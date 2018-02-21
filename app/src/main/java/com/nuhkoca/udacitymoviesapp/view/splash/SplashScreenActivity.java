package com.nuhkoca.udacitymoviesapp.view.splash;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.presenter.splash.SplashScreenActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.splash.SplashScreenActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.ConnectionSniffer;
import com.nuhkoca.udacitymoviesapp.view.main.MovieActivity;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenActivityView {

    private SplashScreenActivityPresenter mSplashScreenActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSplashScreenActivityPresenter = new SplashScreenActivityPresenterImpl(this, this);
        mSplashScreenActivityPresenter.openActivity();

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public void onActivityOpened() {
        int delayInSeconds = getResources().getInteger(R.integer.delay_in_seconds_to_activity);
        final boolean isConnected = ConnectionSniffer.sniff(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isConnected) {
                    Intent movieIntent = new Intent(SplashScreenActivity.this, MovieActivity.class);
                    movieIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(movieIntent);
                } else {
                    Toast.makeText(SplashScreenActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        }, delayInSeconds);
    }

    @Override
    protected void onDestroy() {
        mSplashScreenActivityPresenter.onDestroy();
        super.onDestroy();
    }
}
