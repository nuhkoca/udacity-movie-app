package com.nuhkoca.udacitymoviesapp.callback;

import android.widget.ImageView;

import com.nuhkoca.udacitymoviesapp.model.movie.Results;

/**
 * Created by nuhkoca on 2/19/18.
 */

public interface IMovieItemTouchListener {
    void onMovieItemTouched(Results results, ImageView imageView, String genre);
}