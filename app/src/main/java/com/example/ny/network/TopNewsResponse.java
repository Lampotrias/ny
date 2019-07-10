package com.example.ny.network;

import android.util.Log;

import com.example.myapplication.DTO.ResultsDTO;
import com.example.ny.network.ApiKeyInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class TopNewsResponse {
    private static final String URL = "https://api.nytimes.com";
    private static final String API_KEY = "161HEGGLNZnSQ1TP1tA2W5k9kUla9cnj";

    private static final int TIMEOUT_IN_SECONDS = 2;

    private static TopNewsResponse response;

    private final NewsEndpoint newsEndpoint;

    private TopNewsResponse(){

        final OkHttpClient httpClient = buildOkHttpClient();
        final Retrofit retrofit = buildRetrofitClient(httpClient);
        
        newsEndpoint = retrofit.create(NewsEndpoint.class);
    }

    private Retrofit buildRetrofitClient(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient buildOkHttpClient() {
        final HttpLoggingInterceptor networkLogInterceptor = new HttpLoggingInterceptor();
        networkLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);


        return new OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor.create(API_KEY))
                .addInterceptor(networkLogInterceptor)
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    public static TopNewsResponse getInstance(){
        if (response == null)
            response = new TopNewsResponse();

        return response;
    }

    public interface NewsEndpoint{
        @GET("/svc/topstories/v2/science.json")
        Call<ResultsDTO> search();
    }
}
