package com.example.ny.activities;


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

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import io.reactivex.schedulers.Schedulers;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class NewsListFragment extends Fragment {

	private static final String TAG = NewsListFragment.class.getSimpleName();

	private CompositeDisposable disposables;
	@Nullable
	private ProgressBar progress;
	@Nullable private RecyclerView recycler;
	@Nullable private NewsListAdapter adapter;

	private AppCompatActivity context;
	@Nullable private View error;
	@Nullable private Button errorAction;

	private Toolbar toolbar;
	private MenuItem mSpinnerItem = null;
	private FloatingActionButton floatingReloadButton;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		disposables = new CompositeDisposable();
		context = ((AppCompatActivity)getActivity());
		setHasOptionsMenu(true);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news_list, container, false);

		toolbar = view.findViewById(R.id.toolbar);
		if (toolbar != null){
			toolbar.setTitle("NY Times");
			context.setSupportActionBar(toolbar);
		}

		progress = view.findViewById(R.id.progress);
		recycler = view.findViewById(R.id.recycler);
		error = view.findViewById(R.id.error_layout);
		errorAction = view.findViewById(R.id.action_button);

		adapter = new NewsListAdapter(context, id -> {
			NewsDetailsFragment newsDetailsFragment = NewsDetailsFragment.newInstance(id);

			context.getSupportFragmentManager()
					.beginTransaction()
					.addToBackStack(null)
					.add(R.id.main_frame, newsDetailsFragment)
					.commit();
		});

		if (recycler != null) {
			recycler.setAdapter(adapter);
			recycler.addItemDecoration(new NewsItemDecoration(getResources().getDimensionPixelSize(R.dimen.spacing_micro)));
			//`FirstLoadFromDatabase();
		}

		if (errorAction != null) {
			errorAction.setOnClickListener(item -> LoadNewsFromNetwork());
		}

		floatingReloadButton = view.findViewById(R.id.floatReload);
		floatingReloadButton.setOnClickListener(v -> {
			LoadNewsFromNetwork();
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

	}

	@Override
	public void onStop() {
		super.onStop();
		showProgress(false);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		adapter = null;
		recycler = null;
		progress = null;
		Utils.disposeSafe(disposables);
		disposables = null;
	}

	private void LoadNewsFromNetwork() {
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

	private void FirstLoadFromDatabase() {
		AppDatabase db = AppDatabase.getInstance(context);

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

	private void updateItems(@NotNull List<NewsItem> news) {
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

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

		inflater.inflate(R.menu.menu_option, menu);

		mSpinnerItem = menu.findItem( R.id.action_bar_spinner);
		View view = mSpinnerItem.getActionView();

		if (view instanceof Spinner){
			final Spinner spinner = (Spinner) view;

			ArrayAdapter<String> spinnerAdapter  =  new  ArrayAdapter<>(context, R.layout.spinner_layout, Utils.GetCategoryArray());

			spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(spinnerAdapter);

			spinner.setSelection(0);

			spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					if (position > 0){
						loadItemsByCategory(Utils.GetCategoryNameByID(position));
					}else{
						FirstLoadFromDatabase();
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
		}
	}
}
