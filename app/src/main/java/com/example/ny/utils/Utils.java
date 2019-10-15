package com.example.ny.utils;

import android.view.View;

import androidx.annotation.Nullable;

import com.example.ny.BuildConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.disposables.Disposable;

public class Utils {

    private static final String[] arr = {"All", "arts", "automobiles", "books", "business", "fashion", "food", "health", "home", "insider", "magazine",
            "movies", "national", "nyregion", "obituaries", "opinion", "politics", "realestate", "science", "sports", "sundayreview", "technology",
            "theater", "tmagazine", "travel", "upshot"};

    public static String ConvertToPubFormat(String string){
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        try {
            Date date = oldFormat.parse(string);
            if (date != null){
                return newFormat.format(date);
            }else {
                throw new ParseException("date is null", 0);
            }
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
