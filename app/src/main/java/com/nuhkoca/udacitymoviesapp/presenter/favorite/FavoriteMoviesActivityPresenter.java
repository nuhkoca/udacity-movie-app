package com.nuhkoca.udacitymoviesapp.presenter.favorite;

/**
 * Created by nuhkoca on 2/27/18.
 */

public interface FavoriteMoviesActivityPresenter {
    void fetchMoviesFromDatabase();

    void onDestroy();
}
