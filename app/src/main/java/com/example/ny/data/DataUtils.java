package com.example.ny.data;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.example.ny.BuildConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.reactivex.Single;

import static com.example.ny.utils.Utils.isDebug;

public class DataUtils {
    private static final String TAG = DataUtils.class.getSimpleName();

    public static List<NewsItem> generateNews() throws NetworkErrorException {
        // we are adding this delay to imitate long loading process
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            if (isDebug()) Log.e(TAG, e.getMessage(), e);
        }
        final Category darwinAwards = new Category(1, "Darwin Awards");
        final Category criminal = new Category(2, "Criminal");
        final Category animals = new Category(3, "Animals");
        final Category music = new Category(4, "Music");

        final List<NewsItem> news = new ArrayList<>();

        return news;
    }

    public static Single<List<NewsItem>> observeNews() {
        return Single.create(emitter -> {
            try {
                List<NewsItem> news = generateNews();
                emitter.onSuccess(news);
            } catch (NetworkErrorException ex) {
                if (!emitter.tryOnError(ex) && BuildConfig.DEBUG) {
                    // this is most transparent way to handle exceptions that may happen after disposing
                    // see this discussion: https://github.com/ReactiveX/RxJava/issues/4880
                    // alternatively, you can set RxJavaPlugins.setErrorHandler in you app class
                    Log.e("DataUtils", "observeNews error handler caught an error", ex);
                }
            }
        });
    }

    private static Date createDate(int year, int month, int date, int hrs, int min) {
        return new GregorianCalendar(year, month - 1, date, hrs, min).getTime();
    }

    private DataUtils() {
        throw new AssertionError("No instances");
    }

}
