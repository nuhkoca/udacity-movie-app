package com.nuhkoca.udacitymoviesapp.view.movie;

import android.widget.ImageView;

import com.nuhkoca.udacitymoviesapp.model.Result;

/**
 * Created by nuhkoca on 2/16/18.
 */

public interface MovieView {
    void onLoadingCompleted(String message);

    void onLoadingError(String message);

    void onActivityOpened(Result result, ImageView imageView);
}
