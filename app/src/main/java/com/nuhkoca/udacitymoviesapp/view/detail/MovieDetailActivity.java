package com.nuhkoca.udacitymoviesapp.view.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nuhkoca.udacitymoviesapp.BR;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.callback.IReviewItemTouchListener;
import com.nuhkoca.udacitymoviesapp.callback.ITrailerItemTouchListener;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityMovieDetailBinding;
import com.nuhkoca.udacitymoviesapp.helper.Constants;
import com.nuhkoca.udacitymoviesapp.model.details.DetailsResponse;
import com.nuhkoca.udacitymoviesapp.model.favorite.MovieContract;
import com.nuhkoca.udacitymoviesapp.model.movie.Results;
import com.nuhkoca.udacitymoviesapp.model.review.ReviewResults;
import com.nuhkoca.udacitymoviesapp.model.video.VideoResults;
import com.nuhkoca.udacitymoviesapp.module.GlideApp;
import com.nuhkoca.udacitymoviesapp.presenter.detail.MovieDetailActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.detail.MovieDetailActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.BarConcealer;
import com.nuhkoca.udacitymoviesapp.utils.BottomSheetBuilder;
import com.nuhkoca.udacitymoviesapp.utils.SnackbarPopper;
import com.nuhkoca.udacitymoviesapp.view.detail.adapter.ReviewAdapter;
import com.nuhkoca.udacitymoviesapp.view.detail.adapter.TrailerAdapter;
import com.nuhkoca.udacitymoviesapp.view.review.FullReviewActivity;
import com.nuhkoca.udacitymoviesapp.view.youtube.YoutubeActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailActivityView, View.OnClickListener, AppBarLayout.OnOffsetChangedListener, ITrailerItemTouchListener, IReviewItemTouchListener {

    private ActivityMovieDetailBinding mActivityMovieDetailBinding;
    private MovieDetailActivityPresenter mMovieDetailActivityPresenter;
    private BarConcealer mBarConcealer;
    private Results mResults;
    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;
    private DecimalFormat mDecimalFormat;

    private boolean mIsFabShown = true;
    private int mMaxScrollSize;
    private String mGenre;

    private static String mFormattedTagline;
    private static String mFormattedBudget;
    private static String mOverview;
    private static String mOriginalTitle;
    private static String mReleaseDate;
    private static String mFormattedCount;
    private static float mRating;
    private static String mRatingText;
    private static int mPopularity;
    private static String mPopularityText;
    private static String[] mGenres;
    private static String mPosterPath;
    private static String mFormattedRevenue;
    private static String mFormattedRuntime;
    private static String mFormattedHomepage;
    private static String mCompanies;
    private static String mCountries;
    private static String mLanguages;
    private static List<ReviewResults> mReviewResults = new ArrayList<>();
    private static List<VideoResults> mVideoResults = new ArrayList<>();
    private static String mReviewCount;
    private static String mTrailerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        supportPostponeEnterTransition();

        mBarConcealer = BarConcealer.create(this);
        mBarConcealer.hideStatusBarOnly();

        LinearLayoutManager llForReview = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mActivityMovieDetailBinding.lMovieDetailReviewPart.rvReviews.setLayoutManager(llForReview);
        mActivityMovieDetailBinding.lMovieDetailReviewPart.rvReviews.setHasFixedSize(true);
        mActivityMovieDetailBinding.lMovieDetailReviewPart.rvReviews.setNestedScrollingEnabled(false);

        LinearLayoutManager llForTrailer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mActivityMovieDetailBinding.lMovieDetailTrailerPart.rvTrailers.setLayoutManager(llForTrailer);
        mActivityMovieDetailBinding.lMovieDetailTrailerPart.rvTrailers.setHasFixedSize(true);
        mActivityMovieDetailBinding.lMovieDetailTrailerPart.rvTrailers.setNestedScrollingEnabled(false);

        setSupportActionBar(mActivityMovieDetailBinding.toolbarDetail);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mResults = getIntent().getParcelableExtra(Constants.MOVIE_MODEL_TAG);

        mMovieDetailActivityPresenter = new MovieDetailActivityPresenterImpl(this);

        if (savedInstanceState == null) {
            mMovieDetailActivityPresenter.populateDetails();
        } else {
            mMovieDetailActivityPresenter.handleScreenOrientation();
        }

        mActivityMovieDetailBinding.fabMovieDetail.setOnClickListener(this);
        mActivityMovieDetailBinding.aplMovieDetail.addOnOffsetChangedListener(this);

        mMovieDetailActivityPresenter.expandOrCollapseOtherDetails();
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

                setResult(RESULT_OK);
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
        mDecimalFormat = new DecimalFormat(Constants.NUMBER_FORMAT);

        if (mResults != null && getIntent() != null) {
            mResults = getIntent().getParcelableExtra(Constants.MOVIE_MODEL_TAG);
            mGenre = getIntent().getStringExtra(Constants.GENRE_TAG);
            mActivityMovieDetailBinding.ivMovieDetailPoster.setTransitionName(getIntent().getStringExtra(Constants.TRANSITION_TAG));
            mPosterPath = Constants.W500_IMAGE_URL_PREFIX + mResults.getPosterPath();

            GlideApp.with(this)
                    .load(mPosterPath)
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

            // get variables
            mOverview = mResults.getOverview();
            mOriginalTitle = mResults.getOriginalTitle();
            mReleaseDate = mResults.getReleaseDate();
            mFormattedCount = String.format(getString(R.string.times_place_holder), mDecimalFormat.format(mResults.getVoteCount()));
            mRating = mResults.getVoteAverage();
            mRatingText = String.format(getString(R.string.rating_place_holder), mResults.getVoteAverage());
            mPopularity = Math.round(mResults.getPopularity());
            mPopularityText = String.format(getString(R.string.popularity_place_holder), mResults.getPopularity());

            // bind variables
            mActivityMovieDetailBinding.setVariable(BR.headerTitle, mOriginalTitle);
            mActivityMovieDetailBinding.lMovieDetailHeaderPart.setVariable(BR.overview, mOverview);
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.releaseDate, mReleaseDate);
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedCount, mFormattedCount);
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.rating, mRating);
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.ratingText, mRatingText);
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.popularity, mPopularity);
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.popularityText, mPopularityText);

            mGenres = mGenre.split(",");
            mActivityMovieDetailBinding.lMovieDetailHeaderPart.tgMovieDetailHeaderPartGenre.setTags(mGenres);

            mMovieDetailActivityPresenter.loadTrailers(mResults.getId());
            mMovieDetailActivityPresenter.loadOtherDetails(mResults.getId());
            mMovieDetailActivityPresenter.loadReviews(mResults.getId());

            mActivityMovieDetailBinding.executePendingBindings();
        }
    }

    @Override
    public void onBottomSheetCreated() {
        BottomSheetBuilder.create(this,
                mActivityMovieDetailBinding.bslBottomSheetItemHolder,
                String.format(getString(R.string.share_extra_text), mActivityMovieDetailBinding.ctlMovieDetail.getTitle()),
                String.format(getString(R.string.share_to), mActivityMovieDetailBinding.ctlMovieDetail.getTitle()));
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
        mReviewAdapter = new ReviewAdapter(reviewResults, this);
        mActivityMovieDetailBinding.lMovieDetailReviewPart.rvReviews.setAdapter(mReviewAdapter);

        mReviewAdapter.swapData(reviewResults);

        mReviewCount = String.format(getString(R.string.total_review_tag), reviewResults.size());

        mActivityMovieDetailBinding.lMovieDetailReviewPart.setVariable(BR.formattedReviewCount, mReviewCount);

        mReviewResults = reviewResults;

        mActivityMovieDetailBinding.executePendingBindings();
    }

    @Override
    public void onTrailersLoaded(List<VideoResults> videoResults) {
        mTrailerAdapter = new TrailerAdapter(videoResults, this);
        mActivityMovieDetailBinding.lMovieDetailTrailerPart.rvTrailers.setAdapter(mTrailerAdapter);

        mTrailerAdapter.swapData(videoResults);

        mTrailerCount = String.format(getString(R.string.total_trailer_tag), videoResults.size());

        mActivityMovieDetailBinding.lMovieDetailTrailerPart.setVariable(BR.formattedTrailerCount, mTrailerCount);

        mVideoResults = videoResults;

        mActivityMovieDetailBinding.executePendingBindings();
    }

    @Override
    public void onOtherDetailsLoaded(DetailsResponse detailsResponse, List<String> prodCompanies, List<String> prodCountries, List<String> spokenLanguages) {
        mFormattedBudget = detailsResponse.getBudget() == 0 ? getString(R.string.no_budget_found) :
                String.format(getString(R.string.dollar_place_holder), mDecimalFormat.format(detailsResponse.getBudget()));

        mFormattedRevenue = detailsResponse.getRevenue() == 0 ? getString(R.string.no_revenue_found) :
                String.format(getString(R.string.dollar_place_holder), mDecimalFormat.format(detailsResponse.getRevenue()));

        mFormattedRuntime = detailsResponse.getRuntime() == 0 ? getString(R.string.no_runtime_found) :
                String.format(getString(R.string.times_place_holder), mDecimalFormat.format(detailsResponse.getRuntime()));

        mFormattedHomepage = detailsResponse.getHomepage().equals("") ? getString(R.string.no_homepage_found) : detailsResponse.getHomepage();

        mFormattedTagline = String.format(getString(R.string.tagline_place_holder), detailsResponse.getTagline());

        mActivityMovieDetailBinding.lMovieDetailHeaderPart.tvOtherDetailsTagline.setVisibility(detailsResponse.getTagline().equals("") ? View.GONE : View.VISIBLE);

        mCompanies = prodCompanies.size() == 0 ? getString(R.string.no_company_found) : TextUtils.join(", ", prodCompanies);
        mCountries = prodCountries.size() == 0 ? getString(R.string.no_country_found) : TextUtils.join(", ", prodCountries);
        mLanguages = spokenLanguages.size() == 0 ? getString(R.string.no_language_found) : TextUtils.join(", ", spokenLanguages);

        mActivityMovieDetailBinding.lMovieDetailHeaderPart.setVariable(BR.formattedTagline, mFormattedTagline);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedHomepage, mFormattedHomepage);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedBudget, mFormattedBudget);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedRevenue, mFormattedRevenue);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedRuntime, mFormattedRuntime);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.companies, mCompanies);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.countries, mCountries);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.languages, mLanguages);

        mActivityMovieDetailBinding.executePendingBindings();
    }

    @Override
    public void onAnyLoadingFailed(String message, Enum types) {
        SnackbarPopper.pop(mActivityMovieDetailBinding.clMovieDetail, message);

        if (types.equals(Constants.TYPES.REVIEW)) {
            mActivityMovieDetailBinding.lMovieDetailReviewPart.flMovieDetailReviewPart.setVisibility(View.GONE);
            mActivityMovieDetailBinding.lMovieDetailReviewPart.tvMovieDetailReviewCount.setVisibility(View.GONE);
            mActivityMovieDetailBinding.lMovieDetailReviewPart.tvMovieDetailNoReviewError.setVisibility(View.VISIBLE);
        } else if (types.equals(Constants.TYPES.TRAILER)) {
            mActivityMovieDetailBinding.lMovieDetailTrailerPart.flMovieDetailTrailerPart.setVisibility(View.GONE);
            mActivityMovieDetailBinding.lMovieDetailTrailerPart.tvMovieDetailTrailerCount.setVisibility(View.GONE);
            mActivityMovieDetailBinding.lMovieDetailTrailerPart.tvMovieDetailNoTrailerError.setVisibility(View.VISIBLE);
        } else {
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedHomepage,
                    getString(R.string.no_homepage_found));
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedBudget,
                    getString(R.string.no_budget_found));
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedRevenue,
                    getString(R.string.no_revenue_found));
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedRuntime,
                    getString(R.string.no_runtime_found));
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.companies,
                    getString(R.string.no_company_found));
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.countries,
                    getString(R.string.no_country_found));
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.languages,
                    getString(R.string.no_language_found));

            mActivityMovieDetailBinding.lMovieDetailHeaderPart.tvOtherDetailsTagline.setVisibility(View.GONE);

            mActivityMovieDetailBinding.executePendingBindings();
        }
    }

    @Override
    public void onPartLoadingFailed(Enum types) {
        if (types.equals(Constants.TYPES.REVIEW)) {
            mActivityMovieDetailBinding.lMovieDetailReviewPart.flMovieDetailReviewPart.setVisibility(View.GONE);
            mActivityMovieDetailBinding.lMovieDetailReviewPart.tvMovieDetailReviewCount.setVisibility(View.GONE);
            mActivityMovieDetailBinding.lMovieDetailReviewPart.tvMovieDetailNoReviewError.setVisibility(View.VISIBLE);
        } else if (types.equals(Constants.TYPES.TRAILER)) {
            mActivityMovieDetailBinding.lMovieDetailTrailerPart.flMovieDetailTrailerPart.setVisibility(View.GONE);
            mActivityMovieDetailBinding.lMovieDetailTrailerPart.tvMovieDetailTrailerCount.setVisibility(View.GONE);
            mActivityMovieDetailBinding.lMovieDetailTrailerPart.tvMovieDetailNoTrailerError.setVisibility(View.VISIBLE);
        } else {
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.cvOtherDetails.setVisibility(View.GONE);
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.tvOtherDetailsNoDetailError.setVisibility(View.GONE);
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.tvOtherDetailsNoDetailError.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onMovieAddedToDatabase() {
        new AsyncTask<Void, Void, Uri>() {
            @Override
            protected Uri doInBackground(Void... voids) {
                ContentValues contentValues = new ContentValues();

                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, mResults.getOriginalTitle());
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_GENRE, mGenre);
                contentValues.put(MovieContract.MovieEntry.COLUMN_IMAGE, Constants.W300_IMAGE_URL_PREFIX + mResults.getPosterPath());

                return getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
            }

            @Override
            protected void onPostExecute(Uri uri) {
                if (uri != null) {
                    SnackbarPopper.pop(mActivityMovieDetailBinding.clMovieDetail, String.format(getString(R.string.movie_added_to_database), mResults.getOriginalTitle()));
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.movie_already_exists), Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    @Override
    public void onAfterScreenRotated() {
        mActivityMovieDetailBinding.setVariable(BR.headerTitle, mOriginalTitle);

        mActivityMovieDetailBinding.lMovieDetailHeaderPart.setVariable(BR.formattedTagline, mFormattedTagline);
        mActivityMovieDetailBinding.lMovieDetailHeaderPart.setVariable(BR.overview, mOverview);

        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedBudget, mFormattedBudget);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.releaseDate, mReleaseDate);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedCount, mFormattedCount);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.rating, mRating);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.ratingText, mRatingText);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.popularity, mPopularity);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.popularityText, mPopularityText);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedRevenue, mFormattedRevenue);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedRuntime, mFormattedRuntime);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedHomepage, mFormattedHomepage);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.companies, mCompanies);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.countries, mCountries);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.languages, mLanguages);

        mActivityMovieDetailBinding.lMovieDetailReviewPart.setVariable(BR.formattedReviewCount, mReviewCount);
        mActivityMovieDetailBinding.lMovieDetailTrailerPart.setVariable(BR.formattedTrailerCount, mTrailerCount);

        mActivityMovieDetailBinding.lMovieDetailHeaderPart.tgMovieDetailHeaderPartGenre.setTags(mGenres);

        GlideApp.with(this)
                .load(mPosterPath)
                .into(mActivityMovieDetailBinding.ivMovieDetailPoster);

        initAdaptersAfterScreenRotation();

        mActivityMovieDetailBinding.executePendingBindings();
    }

    @Override
    public void onOtherDetailsExpandedOrCollapsed() {
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.tvOtherDetailsMoreLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int duration = getResources().getInteger(android.R.integer.config_longAnimTime);

                if (mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.tvOtherDetailsMoreLess.getText().equals(getString(R.string.more))) {
                    mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.llOtherDetails.animate()
                            .setDuration(duration)
                            .alpha(1)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.
                                            lOtherDetails.llOtherDetails.setVisibility(View.VISIBLE);

                                    mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.
                                            tvOtherDetailsMoreLess.setText(getString(R.string.less));
                                }
                            });
                } else {
                    mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.llOtherDetails.animate()
                            .setDuration(duration)
                            .alpha(0)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.
                                            lOtherDetails.llOtherDetails.setVisibility(View.GONE);

                                    mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.
                                            tvOtherDetailsMoreLess.setText(getString(R.string.more));
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mMovieDetailActivityPresenter != null) {
            mMovieDetailActivityPresenter.onDestroy();
        }
        mReviewAdapter = null;
        mTrailerAdapter = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBarConcealer.hideStatusBarOnly();
    }

    @Override
    public void onClick(View v) {
        int itemThatWasClicked = v.getId();

        switch (itemThatWasClicked) {
            case R.id.fabMovieDetail:
                mMovieDetailActivityPresenter.addMovieToDatabase();
                break;

            default:
                break;
        }
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

    @Override
    public void onTrailerItemTouched(String trailerKey) {
        openYoutubeAppOrWatchInternally(trailerKey);
    }

    @Override
    public void onReviewItemTouched(String content, String author) {
        Intent reviewIntent = new Intent(MovieDetailActivity.this, FullReviewActivity.class);
        reviewIntent.putExtra(Constants.REVIEW_CONTENT_EXTRA, content);
        reviewIntent.putExtra(Constants.REVIEW_AUTHOR_EXTRA, author);
        reviewIntent.putExtra(Constants.REVIEW_MOVIE_EXTRA, mResults.getOriginalTitle());

        startActivityForResult(reviewIntent, Constants.CHILD_ACTIVITY_REQUEST_CODE);
    }

    private void openYoutubeAppOrWatchInternally(final String videoKey) {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.dialog_title_youtube))
                .theme(Theme.LIGHT)
                .content(getString(R.string.dialog_message_youtube))
                .positiveText(getString(R.string.dialog_positive_button_youtube))
                .negativeText(getString(R.string.dialog_negative_button_youtube))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent youtubeIntent = new Intent(MovieDetailActivity.this, YoutubeActivity.class);
                        youtubeIntent.putExtra(Constants.YOUTUBE_VIDEO_ID, videoKey);
                        startActivityForResult(youtubeIntent, Constants.CHILD_ACTIVITY_REQUEST_CODE);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoKey));
                        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(Constants.YOUTUBE_PREFIX + videoKey));
                        try {
                            startActivity(appIntent);
                        } catch (ActivityNotFoundException ex) {
                            startActivity(webIntent);
                        }
                    }
                })
                .show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.TITLE_STATE, mFormattedTagline);
        outState.putString(Constants.BUDGET_STATE, mFormattedBudget);
        outState.putString(Constants.OVERVIEW_STATE, mOverview);
        outState.putString(Constants.ORIGINAL_TITLE_STATE, mOriginalTitle);
        outState.putString(Constants.RELEASE_DATE_STATE, mReleaseDate);
        outState.putString(Constants.VOTE_COUNT_STATE, mFormattedCount);
        outState.putFloat(Constants.RATING_STATE, mRating);
        outState.putString(Constants.RATING_TEXT_STATE, mRatingText);
        outState.putInt(Constants.POPULARITY_STATE, mPopularity);
        outState.putString(Constants.POPULARITY_TEXT_STATE, mPopularityText);
        outState.putStringArray(Constants.GENRES_STATE, mGenres);
        outState.putString(Constants.POSTER_STATE, mPosterPath);
        outState.putString(Constants.REVENUE_STATE, mFormattedRevenue);
        outState.putString(Constants.RUNTIME_STATE, mFormattedRuntime);
        outState.putString(Constants.HOMEPAGE_STATE, mFormattedHomepage);
        outState.putString(Constants.COMPANIES_STATE, mCompanies);
        outState.putString(Constants.COUNTRIES_STATE, mCountries);
        outState.putString(Constants.LANGUAGES_STATE, mLanguages);
        outState.putParcelableArrayList(Constants.REVIEW_STATE, (ArrayList<? extends Parcelable>) mReviewResults);
        outState.putParcelableArrayList(Constants.TRAILER_STATE, (ArrayList<? extends Parcelable>) mVideoResults);
        outState.putString(Constants.REVIEW_COUNT_STATE, mReviewCount);
        outState.putString(Constants.TRAILER_COUNT_STATE, mTrailerCount);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            mFormattedTagline = savedInstanceState.getString(Constants.TITLE_STATE);
            mFormattedBudget = savedInstanceState.getString(Constants.BUDGET_STATE);
            mOverview = savedInstanceState.getString(Constants.OVERVIEW_STATE);
            mOriginalTitle = savedInstanceState.getString(Constants.ORIGINAL_TITLE_STATE);
            mReleaseDate = savedInstanceState.getString(Constants.RELEASE_DATE_STATE);
            mFormattedCount = savedInstanceState.getString(Constants.VOTE_COUNT_STATE);
            mRating = savedInstanceState.getFloat(Constants.RATING_STATE);
            mRatingText = savedInstanceState.getString(Constants.RATING_TEXT_STATE);
            mPopularity = savedInstanceState.getInt(Constants.POPULARITY_STATE);
            mPopularityText = savedInstanceState.getString(Constants.POPULARITY_TEXT_STATE);
            mGenres = savedInstanceState.getStringArray(Constants.GENRES_STATE);
            mPosterPath = savedInstanceState.getString(Constants.POSTER_STATE);
            mFormattedRevenue = savedInstanceState.getString(Constants.REVENUE_STATE);
            mFormattedRuntime = savedInstanceState.getString(Constants.RUNTIME_STATE);
            mFormattedHomepage = savedInstanceState.getString(Constants.HOMEPAGE_STATE);
            mCompanies = savedInstanceState.getString(Constants.COMPANIES_STATE);
            mCountries = savedInstanceState.getString(Constants.COUNTRIES_STATE);
            mLanguages = savedInstanceState.getString(Constants.LANGUAGES_STATE);
            mReviewResults = savedInstanceState.getParcelableArrayList(Constants.REVIEW_STATE);
            mVideoResults = savedInstanceState.getParcelableArrayList(Constants.TRAILER_STATE);
            mReviewCount = savedInstanceState.getString(Constants.REVIEW_COUNT_STATE);
            mTrailerCount = savedInstanceState.getString(Constants.TRAILER_COUNT_STATE);
        }
    }

    private void initAdaptersAfterScreenRotation() {
        mReviewAdapter = new ReviewAdapter(mReviewResults, this);
        mActivityMovieDetailBinding.lMovieDetailReviewPart.rvReviews.setAdapter(mReviewAdapter);
        mReviewAdapter.swapData(mReviewResults);


        mTrailerAdapter = new TrailerAdapter(mVideoResults, this);
        mActivityMovieDetailBinding.lMovieDetailTrailerPart.rvTrailers.setAdapter(mTrailerAdapter);
        mTrailerAdapter.swapData(mVideoResults);
    }
}