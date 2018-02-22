package com.nuhkoca.udacitymoviesapp.view.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.nuhkoca.udacitymoviesapp.model.Result;
import com.nuhkoca.udacitymoviesapp.module.GlideApp;
import com.nuhkoca.udacitymoviesapp.presenter.detail.MovieDetailActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.detail.MovieDetailActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.BarConcealer;
import com.nuhkoca.udacitymoviesapp.utils.SnackbarPopper;
import com.nuhkoca.udacitymoviesapp.view.movie.MovieFragment;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailActivityView, View.OnClickListener {

    private ActivityMovieDetailBinding mActivityMovieDetailBinding;
    private MovieDetailActivityPresenter mMovieDetailActivityPresenter;
    private BarConcealer barConcealer;

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

        mMovieDetailActivityPresenter = new MovieDetailActivityPresenterImpl(this, this);
        mMovieDetailActivityPresenter.populateDetails();

        barConcealer = BarConcealer.create(MovieDetailActivity.this);
        mActivityMovieDetailBinding.fabMovieDetail.setOnClickListener(this);
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
    public void onDetailsLoaded(Result result) {
        if (result != null) {
            result = getIntent().getParcelableExtra(MovieFragment.MOVIE_MODEL_TAG);

            GlideApp.with(this)
                    .load(BuildConfig.IMAGEURLPREFIX + result.getPosterPath())
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

            mActivityMovieDetailBinding.ctlMovieDetail.setTitle(result.getOriginalTitle());
            mActivityMovieDetailBinding.lMovieDetailPartHolder.lMovieDetailHolderHeader.tvMovieDetailHeaderPartReleaseDate.
                    setText(result.getReleaseDate());
            mActivityMovieDetailBinding.lMovieDetailPartHolder.lMovieDetailHolderHeader.tvMovieDetailHeaderPartVoteCount
                    .setText(String.valueOf(result.getVoteCount()));
            mActivityMovieDetailBinding.lMovieDetailPartHolder.lMovieDetailHolderHeader.tvMovieDetailHeaderPartVoteAverage
                    .setText(String.valueOf(result.getVoteAverage()));
            mActivityMovieDetailBinding.lMovieDetailPartHolder.lMovieDetailHolderHeader.tvMovieDetailHeaderPartOverview
                    .setText(result.getOverview());


            mMovieDetailActivityPresenter.onChangeViewWidth();
        }
    }

    @Override
    public void onBottomSheetCreated() {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.share_to), mActivityMovieDetailBinding.ctlMovieDetail.getTitle()));
        shareIntent.setType("text/plain");

        final IntentPickerSheetView intentPickerSheet = new IntentPickerSheetView(MovieDetailActivity.this, shareIntent, String.format(getString(R.string.share_to), mActivityMovieDetailBinding.ctlMovieDetail.getTitle()), new IntentPickerSheetView.OnIntentPickedListener() {
            @Override
            public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
                mActivityMovieDetailBinding.bslBottomSheetItemHolder.dismissSheet();
                startActivity(activityInfo.getConcreteIntent(shareIntent));
            }
        });

        mActivityMovieDetailBinding.bslBottomSheetItemHolder.showWithSheetView(intentPickerSheet);
    }

    @Override
    public void onViewWidthChanged() {
        mActivityMovieDetailBinding.lMovieDetailPartHolder.lMovieDetailHolderHeader.tvMovieDetailHeaderPartHeader.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                mActivityMovieDetailBinding.lMovieDetailPartHolder.lMovieDetailHolderHeader.tvMovieDetailHeaderPartHeader.getViewTreeObserver().removeOnPreDrawListener(this);

                ViewGroup.LayoutParams layoutParams = mActivityMovieDetailBinding.lMovieDetailPartHolder.lMovieDetailHolderHeader.vMovieDetailHeaderPart.getLayoutParams();

                layoutParams.width = mActivityMovieDetailBinding.lMovieDetailPartHolder.lMovieDetailHolderHeader.tvMovieDetailHeaderPartHeader.getWidth() + 100;

                mActivityMovieDetailBinding.lMovieDetailPartHolder.lMovieDetailHolderHeader.vMovieDetailHeaderPart.setLayoutParams(layoutParams);

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
}