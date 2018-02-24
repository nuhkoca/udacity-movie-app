package com.nuhkoca.udacitymoviesapp.presenter.detail;

/**
 * Created by nuhkoca on 2/19/18.
 */

public interface MovieDetailActivityPresenter {
    void populateDetails();

    void onDestroy();

    void onCreateBottomSheet();

    void onChangeViewWidth();

    void onScheduleStartPostponedTransition();

    void loadReviews(int movieId);
}