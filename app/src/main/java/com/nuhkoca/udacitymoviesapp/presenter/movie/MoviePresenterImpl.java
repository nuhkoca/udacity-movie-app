package com.nuhkoca.udacitymoviesapp.presenter.movie;

import android.accounts.NetworkErrorException;

import com.nuhkoca.udacitymoviesapp.App;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.model.movie.MovieResponse;
import com.nuhkoca.udacitymoviesapp.helper.ObservableHelper;
import com.nuhkoca.udacitymoviesapp.helper.RetrofitInterceptor;
import com.nuhkoca.udacitymoviesapp.view.movie.MovieView;

import java.util.Objects;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by nuhkoca on 2/16/18.
 */

public class MoviePresenterImpl implements MoviePresenter {

    private MovieView mMovieView;

    public MoviePresenterImpl(MovieView mMovieView) {
        this.mMovieView = mMovieView;
    }

    @Override
    public void loadMovies(String apiKey, final String movieTag) {
        final Retrofit retrofit = RetrofitInterceptor.build();
        ObservableHelper observableHelper = new ObservableHelper(retrofit, apiKey);
        Observable<MovieResponse> getMovies;

        if (apiKey == null) {
            mMovieView.onLoadingFailed(App.getInstance().getString(R.string.api_key_null));
            return;
        }

        if (movieTag == null) {
            mMovieView.onLoadingFailed(App.getInstance().getString(R.string.movie_tag_key_null));
            return;
        }

        mMovieView.showProgress(true);

        if (Objects.equals(movieTag, App.getInstance().getString(R.string.popular_tag))) {
            getMovies = observableHelper.getPopularMovies(1);
        } else {
            getMovies = observableHelper.getTopRatedMovies( 1);
        }

        getMovies.subscribeOn(Schedulers.io())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MovieResponse>>() {
                    @Override
                    public Observable<? extends MovieResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<MovieResponse>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e.getMessage());
                        if (e instanceof NetworkErrorException) {
                            mMovieView.onLoadingFailed(App.getInstance().getString(R.string.no_internet_connection));
                        } else if (e instanceof NullPointerException) {
                            mMovieView.onLoadingFailed(App.getInstance().getString(R.string.no_data_error));
                        }

                        mMovieView.showProgress(true);
                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        mMovieView.onLoadingCompleted(movieResponse.getResults());
                        mMovieView.showProgress(false);
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (mMovieView != null) {
            mMovieView = null;
        }
    }
}