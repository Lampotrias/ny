package com.example.ny.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ny.R;
import com.example.ny.data.ConverterData;
import com.example.ny.data.NewsItem;
import com.example.ny.data.NewsResponse;
import com.example.ny.database.AppDatabase;
import com.example.ny.database.NewsEntity;
import com.example.ny.network.INewsEndPoint;
import com.example.ny.network.RestApi;
import com.example.ny.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import io.reactivex.schedulers.Schedulers;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class NewListFragments extends Fragment {

	public static final String TAG = NewListFragments.class.getSimpleName();

	private CompositeDisposable disposables;
	@Nullable
	private ProgressBar progress;
	@Nullable private RecyclerView recycler;
	@Nullable private NewsAdapter adapter;

	private AppCompatActivity context;
	@Nullable private View error;
	@Nullable private Button errorAction;

	private Toolbar toolbar;
	private MenuItem mSpinnerItem = null;
	private FloatingActionButton floatingReloadButton;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = ((AppCompatActivity)getActivity());
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_news_list, container, false);

		toolbar = view.findViewById(R.id.toolbar);

		context.setSupportActionBar(toolbar);

		progress = view.findViewById(R.id.progress);
		recycler = view.findViewById(R.id.recycler);
		error = view.findViewById(R.id.error_layout);
		errorAction = view.findViewById(R.id.action_button);

		adapter = new NewsAdapter(context, id  -> NewsDetailsActivity.start());

		if (recycler != null) {
			recycler.setAdapter(adapter);
			recycler.addItemDecoration(new NewsItemDecoration(getResources().getDimensionPixelSize(R.dimen.spacing_micro)));
		}


		if (errorAction != null) {
			errorAction.setOnClickListener(item -> loadItems());
		}

		floatingReloadButton = view.findViewById(R.id.floatReload);
		floatingReloadButton.setOnClickListener(v -> {
			loadItems();
		});

		if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
			final int columnsCount = getResources().getInteger(R.integer.landscape_news_columns_count);
			recycler.setLayoutManager(new GridLayoutManager(getActivity(), columnsCount));
		} else {
			recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
		}

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		Utils.setVisible(error, false);
		disposables = new CompositeDisposable();
	}

	@Override
	public void onStop() {
		super.onStop();
		showProgress(false);

		Utils.disposeSafe(disposables);
		disposables = null;
	}

	@Override
	public void onDestroy() {
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
		AppDatabase db = AppDatabase.getInstance(context);

		db.newsDao().deleteAll();
		db.newsDao().insertAll(newsEntities);

		if (adapter != null) {
			disposables.add(db.newsDao().getAllFromDatabase()
					.map(ConverterData::fromDatabaseToList)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(this::updateItems, this::handleError));
		}
	}

	private void loadItemsByCategory(String categoryName) {
		showProgress(true);

		AppDatabase db = AppDatabase.getInstance(context);

		disposables.add(db.newsDao().getBySectionName(categoryName)
				.map(ConverterData::fromDatabaseToList)
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
