package com.example.ny.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.ny.R;

import com.example.ny.data.ConverterData;
import com.example.ny.data.ImageType;
import com.example.ny.data.NewsItem;
import com.example.ny.database.AppDatabase;
import com.example.ny.database.NewsEntity;
import com.example.ny.utils.Utils;

public class NewsDetailsFragment extends Fragment {
    private static final String EXTRA_NEWS_ITEM = "extra:newsItem";
    private int newsId;
    private AppDatabase db;
    private Toolbar toolbar;
    private NewsItem detailNews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);

        newsId = getActivity().getIntent().getIntExtra(EXTRA_NEWS_ITEM, 0);
        db = AppDatabase.getInstance(getActivity().getApplicationContext());

        NewsEntity newsEntity = db.newsDao().getNewsByID(newsId);
        if (newsEntity != null){
            detailNews = ConverterData.fromDatabase(newsEntity);
        }else{
            return view;
        }

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        final ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setTitle(detailNews.getTitle());
        }

        final ImageView imageView = view.findViewById(R.id.details_image);
        final TextView titleView = view.findViewById(R.id.details_title);
        final TextView dateView = view.findViewById(R.id.details_date);
        final TextView sectionView = view.findViewById(R.id.section);
        final TextView details_text = view.findViewById(R.id.details_text);

        Glide.with(this)
                .load(detailNews.getImageByType(ImageType.THUMBLARGE))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

        titleView.setText(detailNews.getTitle());
        dateView.setText(Utils.ConvertToPubFormat(detailNews.getPublishedDate()));
        sectionView.setText(detailNews.getSection());
        details_text.setText(detailNews.getPreviewText());

        return view;
    }

    public static void start(@NonNull Context context, @NonNull int id) {
        context.startActivity(new Intent(context, NewsDetailsFragment.class).putExtra(EXTRA_NEWS_ITEM, id));
    }
}
