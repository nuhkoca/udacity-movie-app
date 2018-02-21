package com.nuhkoca.udacitymoviesapp.presenter.detail;

import android.content.Context;

import com.nuhkoca.udacitymoviesapp.utils.BarConcealer;
import com.nuhkoca.udacitymoviesapp.view.detail.MovieDetailActivityView;

/**
 * Created by nuhkoca on 2/19/18.
 */

public class MovieDetailActivityPresenterImpl implements MovieDetailActivityPresenter {

    private MovieDetailActivityView mMovieDetailActivityView;
    private BarConcealer mBarConcealer;

    public MovieDetailActivityPresenterImpl(MovieDetailActivityView mMovieDetailActivityView, Context context) {
        this.mMovieDetailActivityView = mMovieDetailActivityView;
        mBarConcealer = BarConcealer.create(context);
    }

    @Override
    public void populateDetails() {
        if (mMovieDetailActivityView != null) {
            mBarConcealer.hideStatusBarOnly();
            mMovieDetailActivityView.onDetailsLoaded();
        }
    }

    @Override
    public void onDestroy() {
        if (mMovieDetailActivityView != null) {
            mMovieDetailActivityView = null;
        }
    }
}