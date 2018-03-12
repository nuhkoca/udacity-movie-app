package com.nuhkoca.udacitymoviesapp.presenter.browser;

import com.nuhkoca.udacitymoviesapp.view.browser.WebBrowserActivityView;

/**
 * Created by nuhkoca on 3/12/18.
 */

public class WebBrowserActivityPresenterImpl implements WebBrowserActivityPresenter {

    private WebBrowserActivityView mWebBrowserActivityView;

    public WebBrowserActivityPresenterImpl(WebBrowserActivityView mWebBrowserActivityView) {
        this.mWebBrowserActivityView = mWebBrowserActivityView;
    }

    @Override
    public void openReview() {
        mWebBrowserActivityView.onBrowserOpened();
    }

    @Override
    public void prepareUI() {
        mWebBrowserActivityView.onUIPrepared();
    }

    @Override
    public void onDestroy() {
        if (mWebBrowserActivityView != null) {
            mWebBrowserActivityView = null;
        }
    }
}
