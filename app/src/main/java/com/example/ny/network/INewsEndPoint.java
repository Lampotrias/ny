package com.example.ny.network;

import android.database.Observable;

import com.example.ny.data.NewsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface INewsEndPoint {
	@GET ("home.json")
	Call<NewsResponse> getNews();

	@GET ("{section}.json")
	Observable<NewsResponse> getNewsByCategory(@Path("section") String category);
}
