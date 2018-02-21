package com.nuhkoca.udacitymoviesapp.presenter.splash;

import android.content.Context;

import com.nuhkoca.udacitymoviesapp.utils.BarConcealer;
import com.nuhkoca.udacitymoviesapp.view.splash.SplashScreenActivityView;

/**
 * Created by nuhkoca on 2/20/18.
 */

public class SplashScreenActivityPresenterImpl implements SplashScreenActivityPresenter {

    private SplashScreenActivityView mSplashScreenActivityView;
    private BarConcealer mBarConcealer;

    public SplashScreenActivityPresenterImpl(SplashScreenActivityView mSplashScreenActivityView, Context context) {
        this.mSplashScreenActivityView = mSplashScreenActivityView;
        mBarConcealer = BarConcealer.create(context);
    }

    @Override
    public void openActivity() {
        if (mSplashScreenActivityView!=null) {
            mBarConcealer.makeFullImmersive();
            mSplashScreenActivityView.onActivityOpened();
        }
    }

    @Override
    public void onDestroy() {
        mSplashScreenActivityView = null;
    }
}
