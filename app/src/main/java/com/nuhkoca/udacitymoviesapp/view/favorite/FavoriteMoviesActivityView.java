package com.nuhkoca.udacitymoviesapp.view.favorite;

import android.database.Cursor;

/**
 * Created by nuhkoca on 2/27/18.
 */

public interface FavoriteMoviesActivityView {
    void onMoviesFetchedFromDatabase(Cursor movieCursor);

    void onMoviesLoadingFromDatabaseFailed(String message);

    void showProgress(boolean visible);
}
