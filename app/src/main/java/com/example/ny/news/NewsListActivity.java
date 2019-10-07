package com.example.ny.news;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ny.R;
import com.example.ny.data.ConverterData;
import com.example.ny.data.NewsItem;
import com.example.ny.data.NewsResponse;
import com.example.ny.database.AppDatabase;
import com.example.ny.database.NewsEntity;
import com.example.ny.details.NewsDetailsActivity;
import com.example.ny.network.INewsEndPoint;
import com.example.ny.network.RestApi;
import com.example.ny.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

	private Toolbar toolbar;
	private MenuItem mSpinnerItem = null;
	private FloatingActionButton floatingReloadButton;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_news_list);

		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		progress = findViewById(R.id.progress);
		recycler = findViewById(R.id.recycler);
		error = findViewById(R.id.error_layout);
		errorAction = findViewById(R.id.action_button);

		adapter = new NewsAdapter(this, id  -> {
			NewsDetailsActivity.start(this, id);
		});

		if (recycler != null) {
			recycler.setAdapter(adapter);
			recycler.addItemDecoration(new NewsItemDecoration(getResources().getDimensionPixelSize(R.dimen.spacing_micro)));
		}


		if (errorAction != null) {
			errorAction.setOnClickListener(view -> loadItems());
		}

		floatingReloadButton = findViewById(R.id.floatReload);
		floatingReloadButton.setOnClickListener(v -> loadItems());

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

		Utils.setVisible(error, false);
		disposables = new CompositeDisposable();
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

		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_option, menu);

		mSpinnerItem = menu.findItem( R.id.action_bar_spinner);
		View view = mSpinnerItem.getActionView();

		if (view instanceof Spinner){
			final Spinner spinner = (Spinner) view;

			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, Utils.GetCategoryArray());
			spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(spinnerAdapter);

			spinner.setSelection(0);

			/*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					if (position > 0){
						//loadItemsByCategory(Utils.GetCategoryNameByID(position));
					}else{
						//loadItems();
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});*/
		}

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
				.map(NewsResponse::getResults)
				.map(ConverterData::fromListToDatabase)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::OnResponseHandler, this::handleError)
		);
	}

	private void OnResponseHandler(List<NewsEntity> newsEntities) {
		AppDatabase db = AppDatabase.getInstance(getApplicationContext());

		db.newsDao().deleteAll();
		db.newsDao().insertAll(newsEntities);

		if (adapter != null) {
			disposables.add(db.newsDao().getAllFromDatabase()
					.map(ConverterData::fromDatabaseToList)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(adapter::replaceItems, this::handleError));
		}

		Utils.setVisible(recycler, true);
		Utils.setVisible(progress, false);
		Utils.setVisible(error, false);
	}

	private void loadItemsByCategory(String categoryName) {
		showProgress(true);
		INewsEndPoint endPoint = RestApi.getInstance().getEndPoint();

		disposables.add(endPoint.getNewsByCategory(categoryName)
				.map(newsResponse -> newsResponse.getResults())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::updateItems, this::handleError)
		);
	}

	private void updateItems(@Nullable List<NewsItem> news) {
		if (adapter != null) {
			adapter.replaceItems(news);
		}

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
