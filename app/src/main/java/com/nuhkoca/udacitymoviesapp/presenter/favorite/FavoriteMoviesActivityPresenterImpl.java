package com.nuhkoca.udacitymoviesapp.presenter.favorite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nuhkoca.udacitymoviesapp.App;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.helper.FavoriteMoviesDbHelper;
import com.nuhkoca.udacitymoviesapp.model.favorite.FavoriteMoviesContract;
import com.nuhkoca.udacitymoviesapp.view.favorite.FavoriteMoviesActivityView;

/**
 * Created by nuhkoca on 2/27/18.
 */

public class FavoriteMoviesActivityPresenterImpl implements FavoriteMoviesActivityPresenter {

    private FavoriteMoviesActivityView mFavoriteMoviesActivityView;
    private SQLiteDatabase mSqLiteDatabase;
    private FavoriteMoviesDbHelper mFavoriteMoviesDbHelper;

    public FavoriteMoviesActivityPresenterImpl(Context context, FavoriteMoviesActivityView mFavoriteMoviesActivityView) {
        this.mFavoriteMoviesActivityView = mFavoriteMoviesActivityView;
        mFavoriteMoviesDbHelper = new FavoriteMoviesDbHelper(context);
    }

    @Override
    public void fetchMoviesFromDatabase() {
        mSqLiteDatabase = mFavoriteMoviesDbHelper.getWritableDatabase();

        if (!(getAllMovies().getCount() > 0)) {
            mFavoriteMoviesActivityView.onMoviesLoadingFromDatabaseFailed(App.getInstance().getString(R.string.no_date_in_database));
            mFavoriteMoviesActivityView.showProgress(false);
            return;
        }

        mFavoriteMoviesActivityView.onMoviesFetchedFromDatabase(getAllMovies());
        mFavoriteMoviesActivityView.showProgress(true);
    }

    private Cursor getAllMovies() {
        return mSqLiteDatabase.query(
                FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDestroy() {
        if (mFavoriteMoviesActivityView != null) {
            mFavoriteMoviesActivityView = null;
        }
    }
}