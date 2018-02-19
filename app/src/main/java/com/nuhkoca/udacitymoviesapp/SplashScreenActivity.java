package com.nuhkoca.udacitymoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.nuhkoca.udacitymoviesapp.view.main.MovieActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startMovieActivity();
    }

    private void startMovieActivity(){
        int delayInSeconds = getResources().getInteger(R.integer.delay_in_seconds_to_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent movieIntent = new Intent(SplashScreenActivity.this, MovieActivity.class);
                movieIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(movieIntent);
            }
        }, delayInSeconds);
    }
}
