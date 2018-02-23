package com.nuhkoca.udacitymoviesapp.view.detail;

import com.nuhkoca.udacitymoviesapp.model.movie.Results;

/**
 * Created by nuhkoca on 2/19/18.
 */

public interface MovieDetailActivityView {
    void onDetailsLoaded(Results results);

    void onBottomSheetCreated();

    void onViewWidthChanged();

    void onScheduledStartPostponedTransition();
}