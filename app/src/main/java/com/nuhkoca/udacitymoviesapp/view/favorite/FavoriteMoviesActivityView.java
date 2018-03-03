package com.nuhkoca.udacitymoviesapp.view.favorite;

/**
 * Created by nuhkoca on 2/27/18.
 */

public interface FavoriteMoviesActivityView {
    void onMoviesFetchedFromDatabase();

    void onMoviesFetchingFailed(String message);

    void onAllMoviesDeleted();

    void showWarningText(boolean visible);
}