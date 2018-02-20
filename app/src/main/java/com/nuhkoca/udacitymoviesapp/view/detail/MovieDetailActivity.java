package com.nuhkoca.udacitymoviesapp.view.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityMovieDetailBinding;
import com.nuhkoca.udacitymoviesapp.presenter.detail.MovieDetailActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.detail.MovieDetailActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.SnackbarPopper;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailActivityView {

    private ActivityMovieDetailBinding mActivityMovieDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        setSupportActionBar(mActivityMovieDetailBinding.toolbarDetail);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();

        MovieDetailActivityPresenter mMovieDetailActivityPresenter = new MovieDetailActivityPresenterImpl(this, this);
        mMovieDetailActivityPresenter.prepareUI(extras, mActivityMovieDetailBinding.ivMovieDetail, mActivityMovieDetailBinding.ctlMovieDetail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClicked = item.getItemId();

        switch (itemThatWasClicked) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDetailsLoaded() {

    }

    @Override
    public void onDetailsLoadingFailed(String message) {
        SnackbarPopper.pop(mActivityMovieDetailBinding.clMovieDetail, message);
    }
}