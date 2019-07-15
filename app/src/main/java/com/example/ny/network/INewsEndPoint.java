package com.example.ny.network;

import com.example.ny.data.NewsResponse;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface INewsEndPoint {
	@GET ("home.json")
	Single<NewsResponse> getNews();

	@GET ("{section}.json")
	Observable<NewsResponse> getNewsByCategory(@Path("section") String category);
}
