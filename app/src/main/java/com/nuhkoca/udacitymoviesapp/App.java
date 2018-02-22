package com.nuhkoca.udacitymoviesapp;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by nuhkoca on 2/20/18.
 */

public class App extends Application {

    private static App app;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        LeakCanary.install(this);
        app = this;
    }
}