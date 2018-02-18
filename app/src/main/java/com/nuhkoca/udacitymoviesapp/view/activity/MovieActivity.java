package com.nuhkoca.udacitymoviesapp.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityMovieBinding;
import com.nuhkoca.udacitymoviesapp.presenter.activity.MovieActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.activity.MovieActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.FragmentReplacer;
import com.nuhkoca.udacitymoviesapp.view.movie.PopularMovieFragment;

import timber.log.Timber;

public class MovieActivity extends AppCompatActivity implements MovieActivityView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieBinding mActivityMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        Timber.plant(new Timber.DebugTree());

        mActivityMovieBinding.lActivityMovie.tvToolbarHeader.setText(getString(R.string.app_name));

        MovieActivityPresenter mMovieActivityPresenter = new MovieActivityPresenterImpl(this, this);
        mMovieActivityPresenter.loadFragment();
    }

    @Override
    public void onFragmentLoadingCompleted(FragmentReplacer fragmentReplacer) {
        fragmentReplacer.replaceFragment(this, R.id.fl_fragment_holder, new PopularMovieFragment());
    }

    @Override
    public void onFragmentLoadingFailed(String message) {
        Timber.d(message);
    }
}