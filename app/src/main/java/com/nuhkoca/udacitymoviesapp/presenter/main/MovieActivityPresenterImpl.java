package com.nuhkoca.udacitymoviesapp.presenter.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nuhkoca.udacitymoviesapp.view.main.MovieActivityView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nuhkoca on 2/17/18.
 */

public class MovieActivityPresenterImpl implements MovieActivityPresenter {

    private MovieActivityView mMovieActivityView;

    public MovieActivityPresenterImpl(MovieActivityView mMovieActivityView) {
        this.mMovieActivityView = mMovieActivityView;
    }


    @Override
    public void prepareViewPager() {
        mMovieActivityView.onViewPagerReady();
    }

    @Override
    public void beautifyUI(String title) {
        mMovieActivityView.onUIBeautified(title);
    }

    @Override
    public void onDestroy() {
        if (mMovieActivityView != null) {
            mMovieActivityView = null;
        }
    }
}