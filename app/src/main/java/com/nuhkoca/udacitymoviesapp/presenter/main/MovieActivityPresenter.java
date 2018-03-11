package com.nuhkoca.udacitymoviesapp.presenter.main;

/**
 * Created by nuhkoca on 2/17/18.
 */

public interface MovieActivityPresenter {
    void prepareViewPager();

    void beautifyUI(String title);

    void onDestroy();
}