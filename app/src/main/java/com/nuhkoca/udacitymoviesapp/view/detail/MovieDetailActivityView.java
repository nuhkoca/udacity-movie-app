package com.nuhkoca.udacitymoviesapp.view.detail;

import com.nuhkoca.udacitymoviesapp.model.details.DetailsResponse;
import com.nuhkoca.udacitymoviesapp.model.review.ReviewResults;
import com.nuhkoca.udacitymoviesapp.model.video.VideoResults;

import java.util.List;

/**
 * Created by nuhkoca on 2/19/18.
 */

public interface MovieDetailActivityView {
    void onDetailsLoaded();

    void onBottomSheetCreated();

    void onScheduledStartPostponedTransition();

    void onReviewsLoaded(List<ReviewResults> reviewResults);

    void onTrailersLoaded(List<VideoResults> videoResults);

    void onOtherDetailsLoaded(DetailsResponse detailsResponse, List<String> prodCompanies, List<String> prodCountries, List<String> spokenLanguages);

    void onAnyLoadingFailed(String message, Enum types);

    void onPartLoadingFailed(Enum types);

    void onMovieAddedToDatabase();

    void onAfterScreenRotated();

    void onInitialStarted();

    void onOtherDetailsExpandedOrCollapsed();
}