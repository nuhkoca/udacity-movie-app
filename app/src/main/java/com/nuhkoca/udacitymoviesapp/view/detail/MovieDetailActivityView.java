package com.nuhkoca.udacitymoviesapp.view.detail;

import com.nuhkoca.udacitymoviesapp.model.review.ReviewResults;

import java.util.List;

/**
 * Created by nuhkoca on 2/19/18.
 */

public interface MovieDetailActivityView {
    void onDetailsLoaded();

    void onBottomSheetCreated();

    void onViewWidthChanged();

    void onScheduledStartPostponedTransition();

    void onReviewsLoaded(List<ReviewResults> reviewResults);

    void onAnyLoadingFailed(String message);
}