package com.nuhkoca.udacitymoviesapp.presenter.movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by nuhkoca on 2/16/18.
 */

public interface MoviePresenter {
    void prepareUI(RecyclerView rvMovie);

    void loadMovies(Context context, String apiKey, int pageId);
}