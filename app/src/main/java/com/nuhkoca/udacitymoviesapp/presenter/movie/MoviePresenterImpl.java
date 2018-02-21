package com.nuhkoca.udacitymoviesapp.presenter.movie;

import com.nuhkoca.udacitymoviesapp.App;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.model.MovieResponse;
import com.nuhkoca.udacitymoviesapp.network.ObservableHelper;
import com.nuhkoca.udacitymoviesapp.network.RetrofitInterceptor;
import com.nuhkoca.udacitymoviesapp.view.movie.MovieView;

import java.util.Objects;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by nuhkoca on 2/16/18.
 */

public class MoviePresenterImpl implements MoviePresenter {

    private MovieView mMovieView;

    public MoviePresenterImpl(MovieView mMovieView) {
        this.mMovieView = mMovieView;
    }

    @Override
    public void loadMovies(String apiKey, String movieTag) {
        Retrofit retrofit = RetrofitInterceptor.build();

        if (apiKey == null) {
            mMovieView.onLoadingFailed(App.getInstance().getString(R.string.api_key_null));
            return;
        }

        if (movieTag == null) {
            mMovieView.onLoadingFailed(App.getInstance().getString(R.string.movie_tag_key_null));
            return;
        }

        Observable<MovieResponse> getMovies;

        if (Objects.equals(movieTag, App.getInstance().getString(R.string.popular_tag))) {
            getMovies = ObservableHelper.getPopularMovies(retrofit, apiKey, 1);
        } else {
            getMovies = ObservableHelper.getTopRatedMovies(retrofit, apiKey, 1);
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
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMovieView.onLoadingFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        mMovieView.onLoadingCompleted(movieResponse.getResults());
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (mMovieView!=null) {
            mMovieView = null;
        }
    }
}