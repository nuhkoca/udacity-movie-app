package com.nuhkoca.udacitymoviesapp.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nuhkoca.udacitymoviesapp.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nuhkoca on 2/16/18.
 */

public class RetrofitInterceptor {
    public static Retrofit build() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .serializeNulls()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().add(logging);

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
    }
}