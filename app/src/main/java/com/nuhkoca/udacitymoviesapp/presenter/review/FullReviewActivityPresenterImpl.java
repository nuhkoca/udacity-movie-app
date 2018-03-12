package com.nuhkoca.udacitymoviesapp.presenter.review;

import com.nuhkoca.udacitymoviesapp.view.review.FullReviewActivityView;

/**
 * Created by nuhkoca on 3/1/18.
 */

public class FullReviewActivityPresenterImpl implements FullReviewActivityPresenter {

    private FullReviewActivityView mFullReviewActivityView;

    public FullReviewActivityPresenterImpl(FullReviewActivityView mFullReviewActivityView) {
        this.mFullReviewActivityView = mFullReviewActivityView;
    }

    @Override
    public void loadReview() {
        mFullReviewActivityView.onReviewLoaded();
    }

    @Override
    public void initBottomSheet() {
        mFullReviewActivityView.onBottomSheetCreated();
    }

    @Override
    public void openReviewOnBrowser() {
        mFullReviewActivityView.onReviewOpenedOnBrowser();
    }

    @Override
    public void onDestroy() {
        if (mFullReviewActivityView != null) {
            mFullReviewActivityView = null;
        }
    }
}