package com.example.ny.network;

import com.example.ny.data.NewsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface INewsEndPoint {
	@GET ("home.json")
	Observable<NewsResponse> getNews();

	@GET ("{section}.json")
	Observable<NewsResponse> getNewsByCategory(@Path("section") String category);
}
