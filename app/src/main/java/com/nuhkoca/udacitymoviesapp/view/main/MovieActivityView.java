package com.nuhkoca.udacitymoviesapp.view.main;

import com.nuhkoca.udacitymoviesapp.utils.FragmentReplacer;

/**
 * Created by nuhkoca on 2/17/18.
 */

public interface MovieActivityView {
    void onFragmentLoadingCompleted(FragmentReplacer fragmentReplacer);

    void onFragmentLoadingFailed(String message);

    void onNextLoaded(FragmentReplacer fragmentReplacer);
}
