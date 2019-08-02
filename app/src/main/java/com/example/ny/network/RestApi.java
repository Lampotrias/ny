package com.example.ny.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestApi {
    private static final String URL = "https://api.nytimes.com/svc/topstories/v2/";
    private static final String API_KEY = "161HEGGLNZnSQ1TP1tA2W5k9kUla9cnj";

    private static final int TIMEOUT_IN_SECONDS = 2;

    private static RestApi sInstance;

    private final INewsEndPoint newsEndpoint;

    private RestApi(){

        final OkHttpClient httpClient = buildOkHttpClient();
        final Retrofit retrofit = buildRetrofitClient(httpClient);
        
        newsEndpoint = retrofit.create(INewsEndPoint.class);
    }

    private Retrofit buildRetrofitClient(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public INewsEndPoint getEndPoint(){
        return newsEndpoint;
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

    public static synchronized RestApi getInstance() {
        if (sInstance == null) {
            sInstance = new RestApi();
        }
        return sInstance;
    }
}
