package com.nuhkoca.udacitymoviesapp.view.detail;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
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
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

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

import java.text.DecimalFormat;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailActivityView, View.OnClickListener, AppBarLayout.OnOffsetChangedListener, ITrailerItemTouchListener, IReviewItemTouchListener {

    private ActivityMovieDetailBinding mActivityMovieDetailBinding;
    private MovieDetailActivityPresenter mMovieDetailActivityPresenter;
    private BarConcealer barConcealer;
    private Results results;
    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;
    private DecimalFormat mDecimalFormat;

    private boolean mIsFabShown = true;
    private int mMaxScrollSize;
    private String genre;

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
        barConcealer = BarConcealer.create(this);
        barConcealer.hideStatusBarOnly();

        mDecimalFormat = new DecimalFormat(Constants.NUMBER_FORMAT);

        if (results != null && getIntent() != null) {
            results = getIntent().getParcelableExtra(Constants.MOVIE_MODEL_TAG);
            genre = getIntent().getStringExtra(Constants.GENRE_TAG);

            GlideApp.with(this)
                    .load(Constants.W500_IMAGE_URL_PREFIX + results.getPosterPath())
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
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.tvOtherDetailsReleaseDate.
                    setText(results.getReleaseDate());

            String formattedVoteCount = mDecimalFormat.format(results.getVoteCount());
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.tvOtherDetailsVoteCount.setText(String.format(getString(R.string.times_place_holder), formattedVoteCount));

            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.rbOtherDetailsRating.setRating(results.getVoteAverage());
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.tvOtherDetailsVoteAverage.setText(String.format(getString(R.string.rating_place_holder), results.getVoteAverage()));

            mActivityMovieDetailBinding.lMovieDetailHeaderPart.tvMovieDetailHeaderPartOverview
                    .setText(results.getOverview());

            String[] genres = genre.split(",");
            mActivityMovieDetailBinding.lMovieDetailHeaderPart.tgMovieDetailHeaderPartGenre.setTags(genres);

            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.pbOtherDetailsPopularity.setProgress((int) results.getPopularity());
            mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.tvOtherDetailsPopularity.setText(String.format(getString(R.string.popularity_place_holder), results.getPopularity()));

            mMovieDetailActivityPresenter.loadTrailers(results.getId());
            mMovieDetailActivityPresenter.loadOtherDetails(results.getId());
            mMovieDetailActivityPresenter.loadReviews(results.getId());

            mMovieDetailActivityPresenter.onChangeViewWidth();
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
    public void onViewWidthChanged() {
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.tvOtherDetailsReleaseLabel.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.tvOtherDetailsReleaseLabel.getViewTreeObserver().removeOnPreDrawListener(this);

                ViewGroup.LayoutParams layoutParams = mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.vOtherDetails1.getLayoutParams();

                layoutParams.width = Constants.UNDERLINE_WIDTH_TO_VIEW;

                mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.vOtherDetails1.setLayoutParams(layoutParams);
                mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.vOtherDetails2.setLayoutParams(layoutParams);
                mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.vOtherDetails3.setLayoutParams(layoutParams);
                mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.vOtherDetails4.setLayoutParams(layoutParams);
                mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.vOtherDetails5.setLayoutParams(layoutParams);
                mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.vOtherDetails6.setLayoutParams(layoutParams);
                mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.vOtherDetails7.setLayoutParams(layoutParams);
                mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.vOtherDetails8.setLayoutParams(layoutParams);
                mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.vOtherDetails9.setLayoutParams(layoutParams);
                mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.vOtherDetails10.setLayoutParams(layoutParams);
                mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.vOtherDetails11.setLayoutParams(layoutParams);

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mActivityMovieDetailBinding.lMovieDetailReviewPart.rvReviews.setLayoutManager(linearLayoutManager);

        mActivityMovieDetailBinding.lMovieDetailReviewPart.rvReviews.setHasFixedSize(true);
        mActivityMovieDetailBinding.lMovieDetailReviewPart.rvReviews.setNestedScrollingEnabled(false);

        mReviewAdapter = new ReviewAdapter(this);
        mActivityMovieDetailBinding.lMovieDetailReviewPart.rvReviews.setAdapter(mReviewAdapter);

        mReviewAdapter.swapData(reviewResults);

        mActivityMovieDetailBinding.lMovieDetailReviewPart.tvMovieDetailReviewCount.setText(String.format(getString(R.string.total_review_tag), reviewResults.size()));
    }

    @Override
    public void onTrailersLoaded(List<VideoResults> videoResults) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mActivityMovieDetailBinding.lMovieDetailTrailerPart.rvTrailers.setLayoutManager(linearLayoutManager);

        mActivityMovieDetailBinding.lMovieDetailTrailerPart.rvTrailers.setHasFixedSize(true);
        mActivityMovieDetailBinding.lMovieDetailTrailerPart.rvTrailers.setNestedScrollingEnabled(false);

        mTrailerAdapter = new TrailerAdapter(this);
        mActivityMovieDetailBinding.lMovieDetailTrailerPart.rvTrailers.setAdapter(mTrailerAdapter);

        mTrailerAdapter.swapData(videoResults);

        mActivityMovieDetailBinding.lMovieDetailTrailerPart.tvMovieDetailTrailerCount.setText(String.format(getString(R.string.total_trailer_tag), videoResults.size()));
    }

    @Override
    public void onOtherDetailsLoaded(DetailsResponse detailsResponse, List<String> prodCompanies, List<String> prodCountries, List<String> spokenLanguages) {
        String formattedBudget = detailsResponse.getBudget() == 0 ? getString(R.string.no_budget_found) :
                String.format(getString(R.string.dollar_place_holder), mDecimalFormat.format(detailsResponse.getBudget()));

        String formattedRevenue = detailsResponse.getRevenue() == 0 ? getString(R.string.no_revenue_found) :
                String.format(getString(R.string.dollar_place_holder), mDecimalFormat.format(detailsResponse.getRevenue()));

        String formattedRuntime = detailsResponse.getRuntime() == 0 ? getString(R.string.no_runtime_found) :
                String.format(getString(R.string.times_place_holder), mDecimalFormat.format(detailsResponse.getRuntime()));

        String formattedHomepage = detailsResponse.getHomepage().equals("") ? getString(R.string.no_homepage_found) : detailsResponse.getHomepage();

        String formattedTagline = String.format(getString(R.string.tagline_place_holder), detailsResponse.getTagline());
        mActivityMovieDetailBinding.lMovieDetailHeaderPart.tvOtherDetailsTagline.setVisibility(detailsResponse.getTagline().equals("") ? View.GONE : View.VISIBLE);


        String companies = prodCompanies.size() == 0 ? getString(R.string.no_company_found) : TextUtils.join(", ", prodCompanies);

        String countries = prodCountries.size() == 0 ? getString(R.string.no_country_found) : TextUtils.join(", ", prodCountries);

        String languages = spokenLanguages.size() == 0 ? getString(R.string.no_language_found) : TextUtils.join(", ", spokenLanguages);

        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedHomepage, formattedHomepage);
        mActivityMovieDetailBinding.lMovieDetailHeaderPart.setVariable(BR.formattedTagline, formattedTagline);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedBudget, formattedBudget);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedRevenue, formattedRevenue);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.formattedRuntime, formattedRuntime);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.companies, companies);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.countries, countries);
        mActivityMovieDetailBinding.lMovieDetailOtherDetailsPart.lOtherDetails.setVariable(BR.languages, languages);

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

    @Override
    public void onMovieAddedToDatabase() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, results.getOriginalTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_GENRE, genre);

        //byte[] poster = getImageToSaveToDatabase(mActivityMovieDetailBinding.ivMovieDetailPoster);

        contentValues.put(MovieContract.MovieEntry.COLUMN_IMAGE, Constants.W300_IMAGE_URL_PREFIX + results.getPosterPath());

        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);

        if (uri != null) {
            SnackbarPopper.pop(mActivityMovieDetailBinding.clMovieDetail, String.format(getString(R.string.movie_added_to_database), results.getOriginalTitle()));
        }
    }

    @Override
    protected void onDestroy() {
        mMovieDetailActivityPresenter.onDestroy();
        mReviewAdapter = null;
        mTrailerAdapter = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        barConcealer.hideStatusBarOnly();
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
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerKey));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(Constants.YOUTUBE_PREFIX + trailerKey));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    @Override
    public void onReviewItemTouched(String content, String author) {
        Intent reviewIntent = new Intent(MovieDetailActivity.this, FullReviewActivity.class);
        reviewIntent.putExtra(Constants.REVIEW_CONTENT_EXTRA, content);
        reviewIntent.putExtra(Constants.REVIEW_AUTHOR_EXTRA, author);
        reviewIntent.putExtra(Constants.REVIEW_MOVIE_EXTRA, results.getOriginalTitle());

        startActivityForResult(reviewIntent, Constants.CHILD_ACTIVITY_REQUEST_CODE);
    }
}