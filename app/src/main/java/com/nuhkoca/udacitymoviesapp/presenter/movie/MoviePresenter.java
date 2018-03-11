package com.nuhkoca.udacitymoviesapp.presenter.movie;

/**
 * Created by nuhkoca on 2/16/18.
 */

public interface MoviePresenter {
    void loadMovies(String apiKey, String movieTag);

    void handleScreenRotation();

    void onDestroy();
}