package com.example.ny.activities;

import android.content.Context;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.ny.R;

import com.example.ny.data.ConverterData;
import com.example.ny.data.NewsItem;
import com.example.ny.database.AppDatabase;
import com.example.ny.utils.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NewsDetailsFragment extends Fragment {
    private static final String EXTRA_NEWS_ITEM = "extra:newsItem";
    private CompositeDisposable disposables;
    private int newsId;
    private Toolbar toolbar;

    private TextView detail_url;
    private TextView titleView;
    private TextView dateView;
    private TextView sectionView;
    private TextView details_text;
    private ImageView imageView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposables = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        imageView = view.findViewById(R.id.details_image);
        titleView = view.findViewById(R.id.details_title);
        dateView = view.findViewById(R.id.details_date);
        sectionView = view.findViewById(R.id.section);
        details_text = view.findViewById(R.id.details_text);
        detail_url = view.findViewById(R.id.details_url);

        if (getArguments() != null){
            newsId = getArguments().getInt(EXTRA_NEWS_ITEM, 0);
        }
        else{
            newsNotFound(new Throwable());
        }

        AppDatabase db = AppDatabase.getInstance(getActivity());
        disposables.add(db.newsDao().getNewsByID(newsId)
                .map(ConverterData::fromDatabase)
                .filter(newsItem -> newsItem != null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showNews, this::newsNotFound)
        );

        return view;
    }

    private void newsNotFound(Throwable throwable) {
        Toast.makeText(getActivity(), "News not found: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.menu_detail_fragment, menu);
    }

    private void showNews(NewsItem newsItem)
    {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        final ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setTitle(newsItem.getTitle());
        }
        titleView.setText(newsItem.getTitle());
        dateView.setText(Utils.ConvertToPubFormat(newsItem.getPublishedDate()));
        sectionView.setText(newsItem.getSection());
        details_text.setText(newsItem.getPreviewText());
        detail_url.setMovementMethod(LinkMovementMethod.getInstance());
        detail_url.setText(newsItem.getShortUrl());
        Glide.with(this)
                .load(newsItem.getImageDetailUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    public static NewsDetailsFragment newInstance(final int _id){
        NewsDetailsFragment newsDetailsFragment = new NewsDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_NEWS_ITEM, _id);
        newsDetailsFragment.setArguments(args);
        return newsDetailsFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.disposeSafe(disposables);
        disposables = null;
    }
}
