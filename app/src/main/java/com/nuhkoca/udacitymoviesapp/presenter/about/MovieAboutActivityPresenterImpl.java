package com.nuhkoca.udacitymoviesapp.presenter.about;

import com.nuhkoca.udacitymoviesapp.view.about.MovieAboutActivityView;

/**
 * Created by nuhkoca on 2/27/18.
 */

public class MovieAboutActivityPresenterImpl implements MovieAboutActivityPresenter {

    private MovieAboutActivityView mMovieAboutActivityView;

    public MovieAboutActivityPresenterImpl(MovieAboutActivityView mMovieAboutActivityView) {
        this.mMovieAboutActivityView = mMovieAboutActivityView;
    }

    @Override
    public void loadAboutPage() {
        mMovieAboutActivityView.onAboutPageLoaded();
    }

    @Override
    public void onDestroy() {
        if (mMovieAboutActivityView != null) {
            mMovieAboutActivityView = null;
        }
    }
}