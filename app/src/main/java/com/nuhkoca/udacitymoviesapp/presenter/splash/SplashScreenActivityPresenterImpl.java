package com.nuhkoca.udacitymoviesapp.presenter.splash;

import android.content.Context;

import com.nuhkoca.udacitymoviesapp.App;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.utils.ConnectionSniffer;
import com.nuhkoca.udacitymoviesapp.view.splash.SplashScreenActivityView;

/**
 * Created by nuhkoca on 2/20/18.
 */

public class SplashScreenActivityPresenterImpl implements SplashScreenActivityPresenter {

    private SplashScreenActivityView mSplashScreenActivityView;

    public SplashScreenActivityPresenterImpl(SplashScreenActivityView mSplashScreenActivityView, Context context) {
        this.mSplashScreenActivityView = mSplashScreenActivityView;
    }

    @Override
    public void openActivity() {
        if (mSplashScreenActivityView != null) {

            final boolean isConnected = ConnectionSniffer.sniff();

            if (isConnected) {
                mSplashScreenActivityView.onActivityOpened();
            } else {
                mSplashScreenActivityView.onConnectivityError(App.getInstance().getString(R.string.favorite_is_opening));
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mSplashScreenActivityView != null) {
            mSplashScreenActivityView = null;
        }
    }
}