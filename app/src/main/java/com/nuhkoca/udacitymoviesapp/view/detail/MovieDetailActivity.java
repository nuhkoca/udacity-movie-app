package com.nuhkoca.udacitymoviesapp.view.detail;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityMovieDetailBinding;
import com.nuhkoca.udacitymoviesapp.module.GlideApp;
import com.nuhkoca.udacitymoviesapp.presenter.detail.MovieDetailActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.detail.MovieDetailActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.SnackbarPopper;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailActivityView, View.OnClickListener {

    private ActivityMovieDetailBinding mActivityMovieDetailBinding;
    private MovieDetailActivityPresenter mMovieDetailActivityPresenter;

    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        supportPostponeEnterTransition();

        setSupportActionBar(mActivityMovieDetailBinding.toolbarDetail);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent() != null) {
            extras = getIntent().getExtras();
        }

        mMovieDetailActivityPresenter = new MovieDetailActivityPresenterImpl(this, this);
        mMovieDetailActivityPresenter.populateDetails();

        mActivityMovieDetailBinding.fabMovieDetail.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClicked = item.getItemId();

        switch (itemThatWasClicked) {
            case android.R.id.home:
                supportFinishAfterTransition();
                onBackPressed();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
        super.onBackPressed();
    }

    @Override
    public void onDetailsLoaded() {
        extras = getIntent().getExtras();

        GlideApp.with(this)
                .load(extras.getString("movie-poster"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        scheduleStartPostponedTransition(mActivityMovieDetailBinding.ivMovieDetailPoster);
                        return false;
                    }
                })
                .into(mActivityMovieDetailBinding.ivMovieDetailPoster);

        mActivityMovieDetailBinding.ctlMovieDetail.setTitle(extras.getString("movie-title"));
        mActivityMovieDetailBinding.lMovieDetail.lMovieDetailHolderHeader.tvMovieDetailHeaderPartReleaseDate.
                setText(extras.getString("release-date"));
        mActivityMovieDetailBinding.lMovieDetail.lMovieDetailHolderHeader.tvMovieDetailHeaderPartVoteCount
                .setText(extras.getString("vote-count"));
        mActivityMovieDetailBinding.lMovieDetail.lMovieDetailHolderHeader.tvMovieDetailHeaderPartVoteAverage
                .setText(extras.getString("vote-average"));
        mActivityMovieDetailBinding.lMovieDetail.lMovieDetailHolderHeader.tvMovieDetailHeaderPartOverview
                .setText(extras.getString("movie-overview"));


        changeViewWidth();
    }

    @Override
    protected void onDestroy() {
        mMovieDetailActivityPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        SnackbarPopper.pop(mActivityMovieDetailBinding.clMovieDetail, getString(R.string.soon));
    }

    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                });
    }

    private void changeViewWidth() {

        mActivityMovieDetailBinding.lMovieDetail.lMovieDetailHolderHeader.tvMovieDetailHeaderPartHeader.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                mActivityMovieDetailBinding.lMovieDetail.lMovieDetailHolderHeader.tvMovieDetailHeaderPartHeader.getViewTreeObserver().removeOnPreDrawListener(this);

                ViewGroup.LayoutParams layoutParams = mActivityMovieDetailBinding.lMovieDetail.lMovieDetailHolderHeader.vMovieDetailHeaderPart.getLayoutParams();

                layoutParams.width = mActivityMovieDetailBinding.lMovieDetail.lMovieDetailHolderHeader.tvMovieDetailHeaderPartHeader.getWidth() + 100;

                mActivityMovieDetailBinding.lMovieDetail.lMovieDetailHolderHeader.vMovieDetailHeaderPart.setLayoutParams(layoutParams);

                return false;
            }
        });
    }
}