package com.nuhkoca.udacitymoviesapp.view.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityMovieBinding;
import com.nuhkoca.udacitymoviesapp.presenter.main.MovieActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.main.MovieActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.FragmentReplacer;
import com.nuhkoca.udacitymoviesapp.view.movie.PopularMovieFragment;

import timber.log.Timber;

public class MovieActivity extends AppCompatActivity implements MovieActivityView {

    private ActivityMovieBinding mActivityMovieBinding;
    private long backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        Timber.plant(new Timber.DebugTree());

        mActivityMovieBinding.lMovieToolbar.tvToolbarHeader.setText(getString(R.string.app_name));

        MovieActivityPresenter mMovieActivityPresenter = new MovieActivityPresenterImpl(this, this);
        mMovieActivityPresenter.prepareFirstRun();
        mMovieActivityPresenter.loadNext();

        changeTitle(getString(R.string.popular_header));
    }

    @Override
    public void onFragmentLoadingCompleted(FragmentReplacer fragmentReplacer) {

    }

    @Override
    public void onFragmentLoadingFailed(String message) {
        Timber.d(message);
    }

    @Override
    public void onNextLoaded(final FragmentReplacer fragmentReplacer) {
        mActivityMovieBinding.lMovieToolbar.ibSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int backStackCount = fragmentReplacer.getBackStackEntryCount();

                if (backStackCount > 0) {
                    fragmentReplacer.removeFragmentsFromBackstack();
                    fragmentReplacer.replaceFragment(R.id.flFragmentHolder, new PopularMovieFragment());
                } else {
                    fragmentReplacer.replaceFragment(R.id.flFragmentHolder, new PopularMovieFragment());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        int timeDelay = getResources().getInteger(R.integer.delay_in_seconds_to_close);
        FragmentReplacer fragmentReplacer = new FragmentReplacer();

        if (backPressed + timeDelay > System.currentTimeMillis()) {

            int backStackCount = fragmentReplacer.getBackStackEntryCount();

            if (backStackCount > 0) {
                fragmentReplacer.removeFragmentsFromBackstack();
                super.onBackPressed();
            } else {
                super.onBackPressed();
            }

        } else {
            Toast.makeText(getBaseContext(), getString(R.string.twice_press_to_exit),
                    Toast.LENGTH_SHORT).show();
        }

        backPressed = System.currentTimeMillis();
    }

    private void changeTitle(final String title) {
        int delayInSeconds = getResources().getInteger(R.integer.delay_in_seconds_to_title);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mActivityMovieBinding.lMovieToolbar.tvToolbarHeader.setText(title);
            }
        }, delayInSeconds);
    }
}