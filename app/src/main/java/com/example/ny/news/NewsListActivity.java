package com.example.ny.news;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ny.R;
import com.example.ny.data.NewsItem;
import com.example.ny.details.NewsDetailsActivity;
import com.example.ny.network.INewsEndPoint;
import com.example.ny.network.RestApi;
import com.example.ny.utils.Utils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import io.reactivex.schedulers.Schedulers;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class NewsListActivity extends AppCompatActivity {

	public static final String TAG = NewsListActivity.class.getSimpleName();

	private CompositeDisposable disposables;
	@Nullable
	private ProgressBar progress;
	@Nullable private RecyclerView recycler;
	@Nullable private NewsAdapter adapter;

	@Nullable private View error;
	@Nullable private Button errorAction;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_news_list);

		progress = findViewById(R.id.progress);
		recycler = findViewById(R.id.recycler);
		error = findViewById(R.id.error_layout);
		errorAction = findViewById(R.id.action_button);

		disposables = new CompositeDisposable();

		adapter = new NewsAdapter(this, newsItem -> NewsDetailsActivity.start(this, newsItem));
		recycler.setAdapter(adapter);
		recycler.addItemDecoration(new NewsItemDecoration(getResources().getDimensionPixelSize(R.dimen.spacing_micro)));

		errorAction.setOnClickListener(view -> loadItems());

		if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
			final int columnsCount = getResources().getInteger(R.integer.landscape_news_columns_count);
			recycler.setLayoutManager(new GridLayoutManager(this, columnsCount));
		} else {
			recycler.setLayoutManager(new LinearLayoutManager(this));
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		loadItems();
	}

	@Override
	protected void onStop() {
		super.onStop();
		showProgress(false);

		Utils.disposeSafe(disposables);
		disposables = null;
	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		getMenuInflater().inflate(R.menu.menu_option, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected ( MenuItem item ) {
		switch ( item.getItemId () ) {
			//case R.id.about_activity:
			// startActivity ( new Intent( this , AboutActivity.class ) );
			default:
				return super.onOptionsItemSelected ( item );
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		adapter = null;
		recycler = null;
		progress = null;
	}

	private void loadItems() {
		showProgress(true);
		INewsEndPoint endPoint = RestApi.getInstance().getEndPoint();

		disposables.add(endPoint.getNews()
				.map(newsResponse -> newsResponse.getResults())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::updateItems, this::handleError)
		);
	}

	private void updateItems(@Nullable List<NewsItem> news) {
		if (adapter != null) adapter.replaceItems(news);

		Utils.setVisible(recycler, true);
		Utils.setVisible(progress, false);
		Utils.setVisible(error, false);
	}

	private void handleError(Throwable th) {
		if (Utils.isDebug()) {
			Log.e(TAG, th.getMessage(), th);
		}
		Utils.setVisible(error, true);
		Utils.setVisible(progress, false);
		Utils.setVisible(recycler, false);
	}

	private void showProgress(boolean shouldShow) {
		Utils.setVisible(progress, shouldShow);
		Utils.setVisible(recycler, !shouldShow);
		Utils.setVisible(error, !shouldShow);
	}
}
