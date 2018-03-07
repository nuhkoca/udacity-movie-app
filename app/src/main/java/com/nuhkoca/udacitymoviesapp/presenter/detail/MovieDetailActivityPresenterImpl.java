package com.nuhkoca.udacitymoviesapp.presenter.detail;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.nuhkoca.udacitymoviesapp.App;
import com.nuhkoca.udacitymoviesapp.BuildConfig;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.helper.Constants;
import com.nuhkoca.udacitymoviesapp.helper.ObservableHelper;
import com.nuhkoca.udacitymoviesapp.helper.RetrofitInterceptor;
import com.nuhkoca.udacitymoviesapp.model.details.DetailsResponse;
import com.nuhkoca.udacitymoviesapp.model.review.ReviewResponse;
import com.nuhkoca.udacitymoviesapp.model.video.VideoResponse;
import com.nuhkoca.udacitymoviesapp.view.detail.MovieDetailActivityView;

import java.util.ArrayList;

import retrofit2.HttpException;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nuhkoca on 2/19/18.
 */

public class MovieDetailActivityPresenterImpl implements MovieDetailActivityPresenter {

    private MovieDetailActivityView mMovieDetailActivityView;

    public MovieDetailActivityPresenterImpl(MovieDetailActivityView mMovieDetailActivityView, Context context) {
        this.mMovieDetailActivityView = mMovieDetailActivityView;
    }

    @Override
    public void populateDetails() {
        if (mMovieDetailActivityView != null) {
            mMovieDetailActivityView.onDetailsLoaded();
        }
    }

    @Override
    public void onDestroy() {
        if (mMovieDetailActivityView != null) {
            mMovieDetailActivityView = null;
        }
    }

    @Override
    public void onCreateBottomSheet() {
        mMovieDetailActivityView.onBottomSheetCreated();
    }

    @Override
    public void onChangeViewWidth() {
        mMovieDetailActivityView.onViewWidthChanged();
    }

    @Override
    public void onScheduleStartPostponedTransition() {
        mMovieDetailActivityView.onScheduledStartPostponedTransition();
    }

    @Override
    public void loadReviews(int movieId) {
        final Retrofit retrofit = RetrofitInterceptor.build();
        ObservableHelper observableHelper = new ObservableHelper(retrofit, BuildConfig.APIKEY);
        Observable<ReviewResponse> getMovies;

        if (movieId == 0) {
            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.movie_id_key_null), Constants.TYPES.REVIEW);
            return;
        }

        getMovies = observableHelper.getReviews(movieId);

        getMovies.subscribeOn(Schedulers.io())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ReviewResponse>>() {
                    @Override
                    public Observable<? extends ReviewResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<ReviewResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e.getMessage());
                        if (e instanceof NetworkErrorException) {
                            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.no_internet_connection), Constants.TYPES.REVIEW);
                        } else if (e instanceof NullPointerException) {
                            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.no_data_error), Constants.TYPES.REVIEW);
                        } else if (e instanceof HttpException) {
                            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.no_internet_connection), Constants.TYPES.REVIEW);
                        } else {
                            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.no_data_error), Constants.TYPES.REVIEW);
                        }
                    }

                    @Override
                    public void onNext(ReviewResponse reviewResponse) {
                        if (reviewResponse.getReviewResults().size() > 0) {
                            mMovieDetailActivityView.onReviewsLoaded(reviewResponse.getReviewResults());
                        } else {
                            mMovieDetailActivityView.onPartLoadingFailed(Constants.TYPES.REVIEW);
                        }
                    }
                });
    }

    @Override
    public void loadTrailers(int movieId) {
        final Retrofit retrofit = RetrofitInterceptor.build();
        ObservableHelper observableHelper = new ObservableHelper(retrofit, BuildConfig.APIKEY);
        Observable<VideoResponse> getTrailers;

        if (movieId == 0) {
            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.movie_id_key_null), Constants.TYPES.TRAILER);
            return;
        }

        getTrailers = observableHelper.getTrailers(movieId);

        getTrailers.subscribeOn(Schedulers.io())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends VideoResponse>>() {
                    @Override
                    public Observable<? extends VideoResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<VideoResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e.getMessage());
                        if (e instanceof NetworkErrorException) {
                            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.no_internet_connection), Constants.TYPES.TRAILER);
                        } else if (e instanceof NullPointerException) {
                            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.no_data_error), Constants.TYPES.TRAILER);
                        } else if (e instanceof HttpException) {
                            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.no_internet_connection), Constants.TYPES.TRAILER);
                        } else {
                            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.no_data_error), Constants.TYPES.TRAILER);
                        }
                    }

                    @Override
                    public void onNext(VideoResponse videoResponse) {
                        if (videoResponse.getVideoResults().size() > 0) {
                            mMovieDetailActivityView.onTrailersLoaded(videoResponse.getVideoResults());
                        } else {
                            mMovieDetailActivityView.onPartLoadingFailed(Constants.TYPES.TRAILER);
                        }
                    }
                });
    }

    @Override
    public void loadOtherDetails(int movieId) {
        final Retrofit retrofit = RetrofitInterceptor.build();
        ObservableHelper observableHelper = new ObservableHelper(retrofit, BuildConfig.APIKEY);
        Observable<DetailsResponse> getOtherDetails;

        if (movieId == 0) {
            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.movie_id_key_null), Constants.TYPES.DETAILS);
            return;
        }

        getOtherDetails = observableHelper.getOtherDetails(movieId);

        getOtherDetails.subscribeOn(Schedulers.io())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DetailsResponse>>() {
                    @Override
                    public Observable<? extends DetailsResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<DetailsResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e.getMessage());
                        if (e instanceof NetworkErrorException) {
                            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.no_internet_connection), Constants.TYPES.DETAILS);
                        } else if (e instanceof NullPointerException) {
                            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.no_data_error), Constants.TYPES.DETAILS);
                        } else if (e instanceof HttpException) {
                            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.no_internet_connection), Constants.TYPES.DETAILS);
                        } else {
                            mMovieDetailActivityView.onAnyLoadingFailed(App.getInstance().getString(R.string.no_data_error), Constants.TYPES.DETAILS);
                        }
                    }

                    @Override
                    public void onNext(DetailsResponse detailsResponse) {
                        if (detailsResponse.toString().length() > 0) {
                            ArrayList<String> companies = new ArrayList<>();
                            ArrayList<String> countries = new ArrayList<>();
                            ArrayList<String> languages = new ArrayList<>();

                            for (int i = 0; i < detailsResponse.getProductionCompaniesResponses().size(); i++) {
                                companies.add(detailsResponse.getProductionCompaniesResponses().get(i).getName());
                            }

                            for (int i = 0; i < detailsResponse.getProductionCountriesResponses().size(); i++) {
                                countries.add(detailsResponse.getProductionCountriesResponses().get(i).getName());
                            }

                            for (int i = 0; i < detailsResponse.getSpokenLanguagesResponses().size(); i++) {
                                languages.add(detailsResponse.getSpokenLanguagesResponses().get(i).getName());
                            }

                            mMovieDetailActivityView.onOtherDetailsLoaded(detailsResponse, companies, countries, languages);
                        } else {
                            mMovieDetailActivityView.onPartLoadingFailed(Constants.TYPES.DETAILS);
                        }
                    }
                });
    }

    @Override
    public void addMovieToDatabase() {
        mMovieDetailActivityView.onMovieAddedToDatabase();
    }

    @Override
    public void expandOrCollapseOtherDetails() {
        mMovieDetailActivityView.onOtherDetailsExpandedOrCollapsed();
    }
}