package com.example.ny.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.ny.R;

import com.example.ny.data.ConverterData;
import com.example.ny.data.ImageType;
import com.example.ny.data.NewsItem;
import com.example.ny.database.AppDatabase;
import com.example.ny.database.NewsEntity;
import com.example.ny.utils.Utils;

public class NewsDetailsActivity extends AppCompatActivity {
    private static final String EXTRA_NEWS_ITEM = "extra:newsItem";
    private AppDatabase db;
    private Toolbar toolbar;

    private NewsItem detailNews;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        final int newsId = getIntent().getIntExtra(EXTRA_NEWS_ITEM, 0);
        db = AppDatabase.getInstance(getApplicationContext());

        NewsEntity newsEntity = db.newsDao().getNewsByID(newsId);
        if (newsEntity != null){
            detailNews = ConverterData.fromDatabase(newsEntity);
        }else{
            //error
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setTitle(detailNews.getTitle());
        }

        final ImageView imageView = findViewById(R.id.details_image);
        final TextView titleView = findViewById(R.id.details_title);
        final TextView dateView = findViewById(R.id.details_date);
        final TextView sectionView = findViewById(R.id.section);
        final TextView details_text = findViewById(R.id.details_text);

        Glide.with(this)
                .load(detailNews.getImageByType(ImageType.THUMBLARGE))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

        titleView.setText(detailNews.getTitle());
        dateView.setText(Utils.ConvertToPubFormat(detailNews.getPublishedDate()));
        sectionView.setText(detailNews.getSection());
        details_text.setText(detailNews.getPreviewText());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void start(@NonNull Context context, @NonNull int id) {
        context.startActivity(new Intent(context, NewsDetailsActivity.class).putExtra(EXTRA_NEWS_ITEM, id));
    }
}
