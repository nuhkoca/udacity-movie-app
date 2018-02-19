package com.nuhkoca.udacitymoviesapp.view.movie;

/**
 * Created by nuhkoca on 2/16/18.
 */

public interface MovieView {
    void onLoadingCompleted(String message);

    void onLoadingError(String message);
}
