package com.example.ny.utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.ny.BuildConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.disposables.Disposable;

import static android.text.format.DateUtils.DAY_IN_MILLIS;
import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;
import static android.text.format.DateUtils.HOUR_IN_MILLIS;

public class Utils {

    private static final String[] arr = {"All", "arts", "automobiles", "books", "business", "fashion", "food", "health", "home", "insider", "magazine",
            "movies", "national", "nyregion", "obituaries", "opinion", "politics", "realestate", "science", "sports", "sundayreview", "technology",
            "theater", "tmagazine", "travel", "upshot"};

    public static CharSequence formatDateTime(Context context, Date dateTime) {
        return DateUtils.getRelativeDateTimeString(
                context,
                dateTime.getTime(),
                HOUR_IN_MILLIS,
                5 * DAY_IN_MILLIS,
                FORMAT_ABBREV_RELATIVE
        );
    }

    public static String ConvertToPubFormat(String string){
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        SimpleDateFormat newFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            Date date = oldFormat.parse(string);
            return newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void disposeSafe(@Nullable Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static void setVisible(@Nullable View view, boolean show) {
        if (view == null) return;

        int visibility = show ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
    }

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static String[] GetCategoryArray(){
        return arr;
    }

    public static String GetCategoryNameByID(int position){
        try{
            return arr[position];
        }catch (Exception e){
            return "All";
        }
    }

    private Utils() {
        throw new AssertionError("No instances");
    }
}
