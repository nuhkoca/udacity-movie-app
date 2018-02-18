package com.nuhkoca.udacitymoviesapp.presenter.movie;

import com.nuhkoca.udacitymoviesapp.view.movie.MovieAdapter;

/**
 * Created by nuhkoca on 2/16/18.
 */

public interface MoviePresenter {
    void loadMovies(MovieAdapter movieAdapter, String apiKey, int pageId);
}