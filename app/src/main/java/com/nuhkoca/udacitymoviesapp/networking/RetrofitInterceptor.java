package com.nuhkoca.udacitymoviesapp.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nuhkoca.udacitymoviesapp.BuildConfig;

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

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
