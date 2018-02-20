package com.nuhkoca.udacitymoviesapp.view.detail;

/**
 * Created by nuhkoca on 2/19/18.
 */

public interface MovieDetailActivityView {
    void onDetailsLoaded();

    void onDetailsLoadingFailed(String message);
}