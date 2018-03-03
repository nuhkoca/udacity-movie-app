package com.nuhkoca.udacitymoviesapp.presenter.favorite;

import android.database.Cursor;

import com.nuhkoca.udacitymoviesapp.App;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.model.favorite.MovieContract;
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
        if (movieCount().getCount() > 0) {
            mFavoriteMoviesActivityView.onMoviesFetchedFromDatabase();
            mFavoriteMoviesActivityView.showWarningText(false);
        } else {
            mFavoriteMoviesActivityView.onMoviesFetchingFailed(App.getInstance().getString(R.string.no_data_in_database));
            mFavoriteMoviesActivityView.showWarningText(true);
        }
    }

    @Override
    public void deleteAllMovies() {
        mFavoriteMoviesActivityView.onAllMoviesDeleted();
    }

    @Override
    public void onDestroy() {
        if (mFavoriteMoviesActivityView != null) {
            mFavoriteMoviesActivityView = null;
        }
    }

    private Cursor movieCount() {
        Cursor cursor = App.getInstance().getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        if (cursor != null) {
            cursor.close();
        }

        return cursor;
    }
}