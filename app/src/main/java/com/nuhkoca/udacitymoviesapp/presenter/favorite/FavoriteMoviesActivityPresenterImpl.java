package com.nuhkoca.udacitymoviesapp.presenter.favorite;

import com.nuhkoca.udacitymoviesapp.view.favorite.FavoriteMoviesActivityView;

/**
 * Created by nuhkoca on 2/27/18.
 */

public class FavoriteMoviesActivityPresenterImpl implements FavoriteMoviesActivityPresenter {

    private FavoriteMoviesActivityView mFavoriteMoviesActivityView;

    public FavoriteMoviesActivityPresenterImpl(FavoriteMoviesActivityView mFavoriteMoviesActivityView) {
        this.mFavoriteMoviesActivityView = mFavoriteMoviesActivityView;
    }

    @Override
    public void fetchMoviesFromDatabase() {
        mFavoriteMoviesActivityView.onMoviesFetchedFromDatabase();
        mFavoriteMoviesActivityView.showWarningText(false);
    }

    @Override
    public void onDestroy() {
        if (mFavoriteMoviesActivityView != null) {
            mFavoriteMoviesActivityView = null;
        }
    }
}