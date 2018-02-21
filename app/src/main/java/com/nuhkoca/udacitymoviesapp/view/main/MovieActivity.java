package com.nuhkoca.udacitymoviesapp.view.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityMovieBinding;
import com.nuhkoca.udacitymoviesapp.presenter.main.MovieActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.main.MovieActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.SnackbarPopper;
import com.nuhkoca.udacitymoviesapp.view.movie.MovieFragment;

import java.util.Objects;

public class MovieActivity extends AppCompatActivity implements MovieActivityView, View.OnClickListener {

    private ActivityMovieBinding mActivityMovieBinding;
    private MovieActivityPresenter mMovieActivityPresenter;

    private long backPressed;
    private String fragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        mActivityMovieBinding.lMovieToolbar.tvToolbarHeader.setText(getString(R.string.app_name));

        mMovieActivityPresenter = new MovieActivityPresenterImpl(this);
        mMovieActivityPresenter.loadFragments();

        mActivityMovieBinding.lMovieToolbar.ibSort.setOnClickListener(this);
    }

    @Override
    public void onFragmentLoadingCompleted() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flMovieActivity, MovieFragment.getInstance(getString(R.string.popular_tag)))
                .commit();

        fragmentTag = getString(R.string.popular_tag);
        changeTitle(getString(R.string.popular_header));

        SnackbarPopper.pop(mActivityMovieBinding.flMovieActivity, getString(R.string.popular_movies_successfully_loaded));
    }

    @Override
    public void onBackPressed() {
        int timeDelay = getResources().getInteger(R.integer.delay_in_seconds_to_close);

        if (backPressed + timeDelay > System.currentTimeMillis()) {

            int backStackCount = getSupportFragmentManager().getBackStackEntryCount();

            if (backStackCount > 0) {
                super.onBackPressed();
            }
            super.onBackPressed();

        } else {
            Toast.makeText(getBaseContext(), getString(R.string.twice_press_to_exit),
                    Toast.LENGTH_SHORT).show();
        }

        backPressed = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        mMovieActivityPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (Objects.equals(fragmentTag, getString(R.string.popular_tag))) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flMovieActivity, MovieFragment.getInstance(getString(R.string.top_rated_tag)))
                    .commit();

            fragmentTag = getString(R.string.top_rated_tag);
            changeTitle(getString(R.string.top_rated_header));

            SnackbarPopper.pop(mActivityMovieBinding.flMovieActivity, getString(R.string.top_rated_movies_successfully_loaded));
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flMovieActivity, MovieFragment.getInstance(getString(R.string.popular_tag)))
                    .commit();

            fragmentTag = getString(R.string.popular_tag);
            changeTitle(getString(R.string.popular_header));

            SnackbarPopper.pop(mActivityMovieBinding.flMovieActivity, getString(R.string.popular_movies_successfully_loaded));
        }
    }

    private void changeTitle(final String title) {
        int delayInSeconds = getResources().getInteger(R.integer.delay_in_seconds_to_title);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mActivityMovieBinding.lMovieToolbar.tvToolbarHeader.setText(title);
            }
        }, delayInSeconds);
    }
}