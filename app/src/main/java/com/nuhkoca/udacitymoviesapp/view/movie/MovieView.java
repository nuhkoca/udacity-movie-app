package com.nuhkoca.udacitymoviesapp.view.movie;

import com.nuhkoca.udacitymoviesapp.model.Result;

import java.util.List;

/**
 * Created by nuhkoca on 2/16/18.
 */

public interface MovieView {
    void onLoadingCompleted(List<Result> result);

    void onLoadingFailed(String message);
}
