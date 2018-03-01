package com.nuhkoca.udacitymoviesapp.view.favorite;

/**
 * Created by nuhkoca on 2/27/18.
 */

public interface FavoriteMoviesActivityView {
    void onMoviesFetchedFromDatabase();

    void showWarningText(boolean visible);
}