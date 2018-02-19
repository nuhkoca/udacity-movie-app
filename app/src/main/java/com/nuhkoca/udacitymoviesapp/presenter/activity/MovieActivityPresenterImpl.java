package com.nuhkoca.udacitymoviesapp.presenter.activity;

import android.content.Context;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.utils.ConnectionSniffer;
import com.nuhkoca.udacitymoviesapp.utils.FragmentReplacer;
import com.nuhkoca.udacitymoviesapp.view.activity.MovieActivity;
import com.nuhkoca.udacitymoviesapp.view.activity.MovieActivityView;
import com.nuhkoca.udacitymoviesapp.view.movie.PopularMovieFragment;

/**
 * Created by nuhkoca on 2/17/18.
 */

public class MovieActivityPresenterImpl implements MovieActivityPresenter {

    private MovieActivityView mMovieActivityView;
    private FragmentReplacer mFragmentReplacer;
    private Context context;

    public MovieActivityPresenterImpl(MovieActivityView mMovieActivityView, Context context) {
        this.mMovieActivityView = mMovieActivityView;
        this.context = context;
        this.mFragmentReplacer = FragmentReplacer.create(context);
    }

    @Override
    public void prepareFirstRun() {
        if (mFragmentReplacer != null) {
            loadFirstRunFragment();
            mMovieActivityView.onFragmentLoadingCompleted(mFragmentReplacer);
        } else {
            mMovieActivityView.onFragmentLoadingFailed(context.getString(R.string.fragment_loading_unsuccessful));
        }
    }

    @Override
    public void loadNext() {
        mMovieActivityView.onNextLoaded(mFragmentReplacer);
    }

    private void loadFirstRunFragment(){
        boolean isConnected = ConnectionSniffer.sniff(context);

        if (isConnected) {
            mFragmentReplacer.replaceFragment(new MovieActivity().getInstance(), R.id.flFragmentHolder, new PopularMovieFragment());
        }
    }
}
