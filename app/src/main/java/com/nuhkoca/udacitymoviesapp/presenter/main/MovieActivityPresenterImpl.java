package com.nuhkoca.udacitymoviesapp.presenter.main;

import com.nuhkoca.udacitymoviesapp.view.main.MovieActivityView;

/**
 * Created by nuhkoca on 2/17/18.
 */

public class MovieActivityPresenterImpl implements MovieActivityPresenter {

    private MovieActivityView mMovieActivityView;

    public MovieActivityPresenterImpl(MovieActivityView mMovieActivityView) {
        this.mMovieActivityView = mMovieActivityView;
    }

    @Override
    public void loadFragments() {
        if (mMovieActivityView != null) {
            mMovieActivityView.onFragmentLoadingCompleted();
        }
    }

    @Override
    public void beautifyUI(String title) {
        mMovieActivityView.onUIBeautified(title);
    }

    @Override
    public void onDestroy() {
        if (mMovieActivityView != null) {
            mMovieActivityView = null;
        }
    }
}