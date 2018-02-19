package com.nuhkoca.udacitymoviesapp.view.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityMovieBinding;
import com.nuhkoca.udacitymoviesapp.presenter.main.MovieActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.main.MovieActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.FragmentReplacer;

import timber.log.Timber;

public class MovieActivity extends AppCompatActivity implements MovieActivityView {

    private ActivityMovieBinding mActivityMovieBinding;
    private static final int DURATION = 2000;

    private static MovieActivity instance;

    public MovieActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        instance = this;

        Timber.plant(new Timber.DebugTree());

        mActivityMovieBinding.lMovieToolbar.tvToolbarHeader.setText(getString(R.string.app_name));

        MovieActivityPresenter mMovieActivityPresenter = new MovieActivityPresenterImpl(this, this);
        mMovieActivityPresenter.prepareFirstRun();
        mMovieActivityPresenter.loadNext();

        changeTitle(getString(R.string.popular_header));
    }

    @Override
    public void onFragmentLoadingCompleted(FragmentReplacer fragmentReplacer) {
    }

    @Override
    public void onFragmentLoadingFailed(String message) {
        Timber.d(message);
    }

    @Override
    public void onNextLoaded(FragmentReplacer fragmentReplacer) {
        mActivityMovieBinding.lMovieToolbar.ibSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void changeTitle(final String title) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mActivityMovieBinding.lMovieToolbar.tvToolbarHeader.setText(title);
            }
        }, DURATION);
    }
}