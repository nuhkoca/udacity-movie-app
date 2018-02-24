package com.nuhkoca.udacitymoviesapp.view.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;
import com.nuhkoca.udacitymoviesapp.BuildConfig;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityMovieDetailBinding;
import com.nuhkoca.udacitymoviesapp.helper.Constants;
import com.nuhkoca.udacitymoviesapp.model.movie.Results;
import com.nuhkoca.udacitymoviesapp.model.review.ReviewResults;
import com.nuhkoca.udacitymoviesapp.module.GlideApp;
import com.nuhkoca.udacitymoviesapp.presenter.detail.MovieDetailActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.detail.MovieDetailActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.BarConcealer;
import com.nuhkoca.udacitymoviesapp.utils.SnackbarPopper;
import com.nuhkoca.udacitymoviesapp.view.detail.adapter.ReviewAdapter;

import java.util.Comparator;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailActivityView, View.OnClickListener, AppBarLayout.OnOffsetChangedListener {

    private ActivityMovieDetailBinding mActivityMovieDetailBinding;
    private MovieDetailActivityPresenter mMovieDetailActivityPresenter;
    private BarConcealer barConcealer;
    private Results results;

    private boolean mIsFabShown = true;
    private int mMaxScrollSize;

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

        results = getIntent().getParcelableExtra(Constants.MOVIE_MODEL_TAG);

        mMovieDetailActivityPresenter = new MovieDetailActivityPresenterImpl(this, this);
        mMovieDetailActivityPresenter.populateDetails();
        mMovieDetailActivityPresenter.loadReviews(BuildConfig.APIKEY, results.getId());

        mActivityMovieDetailBinding.fabMovieDetail.setOnClickListener(this);
        mActivityMovieDetailBinding.aplMovieDetail.addOnOffsetChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClicked = item.getItemId();

        switch (itemThatWasClicked) {
            case android.R.id.home:
                supportFinishAfterTransition();
                onBackPressed();
                return true;

            case R.id.menu_share:
                mMovieDetailActivityPresenter.onCreateBottomSheet();
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
        barConcealer = BarConcealer.create(this);
        barConcealer.hideStatusBarOnly();


        if (results != null) {
            results = getIntent().getParcelableExtra(Constants.MOVIE_MODEL_TAG);

            GlideApp.with(this)
                    .load(BuildConfig.W500IMAGEURLPREFIX + results.getPosterPath())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            supportStartPostponedEnterTransition();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            mMovieDetailActivityPresenter.onScheduleStartPostponedTransition();
                            return false;
                        }
                    })
                    .into(mActivityMovieDetailBinding.ivMovieDetailPoster);

            mActivityMovieDetailBinding.ctlMovieDetail.setTitle(results.getOriginalTitle());
            mActivityMovieDetailBinding.lMovieDetailHeaderPart.tvMovieDetailHeaderPartReleaseDate.
                    setText(results.getReleaseDate());
            mActivityMovieDetailBinding.lMovieDetailHeaderPart.tvMovieDetailHeaderPartVoteCount
                    .setText(String.valueOf(results.getVoteCount()));
            mActivityMovieDetailBinding.lMovieDetailHeaderPart.tvMovieDetailHeaderPartVoteAverage
                    .setText(String.valueOf(results.getVoteAverage()));
            mActivityMovieDetailBinding.lMovieDetailHeaderPart.tvMovieDetailHeaderPartOverview
                    .setText(results.getOverview());

            mMovieDetailActivityPresenter.onChangeViewWidth();
        }
    }

    @Override
    public void onBottomSheetCreated() {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.share_extra_text), mActivityMovieDetailBinding.ctlMovieDetail.getTitle()));
        shareIntent.setType("text/plain");

        final IntentPickerSheetView intentPickerSheet = new IntentPickerSheetView(MovieDetailActivity.this, shareIntent, String.format(getString(R.string.share_to), mActivityMovieDetailBinding.ctlMovieDetail.getTitle()), new IntentPickerSheetView.OnIntentPickedListener() {
            @Override
            public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
                mActivityMovieDetailBinding.bslBottomSheetItemHolder.dismissSheet();
                startActivity(activityInfo.getConcreteIntent(shareIntent));
            }
        });

        intentPickerSheet.setFilter(new IntentPickerSheetView.Filter() {
            @Override
            public boolean include(IntentPickerSheetView.ActivityInfo info) {
                return !info.componentName.getPackageName().startsWith("com.android");
            }
        });

        intentPickerSheet.setSortMethod(new Comparator<IntentPickerSheetView.ActivityInfo>() {
            @Override
            public int compare(IntentPickerSheetView.ActivityInfo lhs, IntentPickerSheetView.ActivityInfo rhs) {
                return rhs.label.compareTo(lhs.label);
            }
        });

        mActivityMovieDetailBinding.bslBottomSheetItemHolder.showWithSheetView(intentPickerSheet);
    }

    @Override
    public void onViewWidthChanged() {
        mActivityMovieDetailBinding.lMovieDetailHeaderPart.tvMovieDetailHeaderPartHeader.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                mActivityMovieDetailBinding.lMovieDetailHeaderPart.tvMovieDetailHeaderPartHeader.getViewTreeObserver().removeOnPreDrawListener(this);

                ViewGroup.LayoutParams layoutParams = mActivityMovieDetailBinding.lMovieDetailHeaderPart.vMovieDetailHeaderPart.getLayoutParams();

                layoutParams.width = mActivityMovieDetailBinding.lMovieDetailHeaderPart.tvMovieDetailHeaderPartHeader.getWidth() + 100;

                mActivityMovieDetailBinding.lMovieDetailHeaderPart.vMovieDetailHeaderPart.setLayoutParams(layoutParams);

                return false;
            }
        });
    }

    @Override
    public void onScheduledStartPostponedTransition() {
        mActivityMovieDetailBinding.ivMovieDetailPoster.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        mActivityMovieDetailBinding.ivMovieDetailPoster.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                });
    }

    @Override
    public void onReviewsLoaded(List<ReviewResults> reviewResults) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mActivityMovieDetailBinding.rvReviews.setLayoutManager(linearLayoutManager);

        mActivityMovieDetailBinding.rvReviews.setHasFixedSize(true);
        mActivityMovieDetailBinding.rvReviews.setNestedScrollingEnabled(false);

        ReviewAdapter mReviewAdapter = new ReviewAdapter();
        mActivityMovieDetailBinding.rvReviews.setAdapter(mReviewAdapter);

        mReviewAdapter.swapData(reviewResults);
    }

    @Override
    public void onAnyLoadingFailed(String message) {}

    @Override
    protected void onDestroy() {
        mMovieDetailActivityPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        barConcealer.hideStatusBarOnly();
    }

    @Override
    public void onClick(View v) {
        SnackbarPopper.pop(mActivityMovieDetailBinding.clMovieDetail, getString(R.string.soon));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;

        if (percentage >= Constants.PERCENTAGE_TO_ANIMATE_FAB && mIsFabShown) {
            mIsFabShown = false;

            mActivityMovieDetailBinding.fabMovieDetail.animate()
                    .scaleY(0).scaleX(0)
                    .setDuration(200)
                    .start();
        }

        if (percentage <= Constants.PERCENTAGE_TO_ANIMATE_FAB && !mIsFabShown) {
            mIsFabShown = true;

            mActivityMovieDetailBinding.fabMovieDetail.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }
}