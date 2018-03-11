package com.nuhkoca.udacitymoviesapp.view.movie;

import com.nuhkoca.udacitymoviesapp.model.movie.Results;

import java.util.List;

/**
 * Created by nuhkoca on 2/16/18.
 */

public interface MovieView {
    void onLoadingCompleted(List<Results> results);

    void onLoadingFailed(String message);

    void onAfterScreenRotated();

    void showProgress(boolean visible);
}
