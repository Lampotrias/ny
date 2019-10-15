package com.example.ny.network;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class ApiKeyInterceptor implements Interceptor{

    private static final String PARAM_API_KEY = "api-key";

    private final String apiKey;

    private ApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }


    public static Interceptor create(@NonNull String apiKey) {
        return new ApiKeyInterceptor(apiKey);
    }


    @NotNull
    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        final Request requestWithoutApiKey = chain.request();

        final HttpUrl url = requestWithoutApiKey.url()
                .newBuilder()
                .addQueryParameter(PARAM_API_KEY, apiKey)
                .build();

        final Request requestWithAttachedApiKey = requestWithoutApiKey.newBuilder()
                .url(url)
                .build();

        return chain.proceed(requestWithAttachedApiKey);
    }
}
