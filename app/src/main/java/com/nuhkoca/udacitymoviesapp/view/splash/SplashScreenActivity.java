package com.nuhkoca.udacitymoviesapp.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.presenter.splash.SplashScreenActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.splash.SplashScreenActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.BarConcealer;
import com.nuhkoca.udacitymoviesapp.view.favorite.FavoriteMoviesActivity;
import com.nuhkoca.udacitymoviesapp.view.main.MovieActivity;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenActivityView {

    private SplashScreenActivityPresenter mSplashScreenActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSplashScreenActivityPresenter = new SplashScreenActivityPresenterImpl(this, this);
        mSplashScreenActivityPresenter.openActivity();
    }

    @Override
    public void onActivityOpened() {
        BarConcealer barConcealer = BarConcealer.create(this);
        barConcealer.makeFullImmersive();

        int delayInSeconds = getResources().getInteger(R.integer.delay_in_seconds_to_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent movieIntent = new Intent(SplashScreenActivity.this, MovieActivity.class);
                movieIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                movieIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(movieIntent);
            }
        }, delayInSeconds);
    }

    @Override
    public void onConnectivityError(String message) {
        BarConcealer barConcealer = BarConcealer.create(this);
        barConcealer.makeFullImmersive();

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        int delayInSeconds = getResources().getInteger(R.integer.delay_in_seconds_to_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent favoriteIntent = new Intent(SplashScreenActivity.this, FavoriteMoviesActivity.class);
                favoriteIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                favoriteIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(favoriteIntent);
            }
        }, delayInSeconds);
    }

    @Override
    protected void onDestroy() {
        mSplashScreenActivityPresenter.onDestroy();
        super.onDestroy();
    }
}