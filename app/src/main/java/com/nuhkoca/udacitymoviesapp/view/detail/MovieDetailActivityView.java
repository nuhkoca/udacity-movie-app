package com.nuhkoca.udacitymoviesapp.view.detail;

import com.nuhkoca.udacitymoviesapp.model.Result;

/**
 * Created by nuhkoca on 2/19/18.
 */

public interface MovieDetailActivityView {
    void onDetailsLoaded(Result result);

    void onBottomSheetCreated();

    void onViewWidthChanged();

    void onScheduledStartPostponedTransition();
}