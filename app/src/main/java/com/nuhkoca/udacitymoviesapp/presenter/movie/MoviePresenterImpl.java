package com.nuhkoca.udacitymoviesapp.presenter.movie;

import com.nuhkoca.udacitymoviesapp.model.MovieResponse;
import com.nuhkoca.udacitymoviesapp.networking.ObservableHelper;
import com.nuhkoca.udacitymoviesapp.networking.RetrofitInterceptor;
import com.nuhkoca.udacitymoviesapp.view.movie.MovieAdapter;
import com.nuhkoca.udacitymoviesapp.view.movie.MovieView;

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
    public void loadMovies(final MovieAdapter movieAdapter, String apiKey, int pageId) {
        final Retrofit retrofit = RetrofitInterceptor.build();

        final Observable<MovieResponse> getMovies = ObservableHelper.getPopularMovies(retrofit, apiKey, pageId);

        getMovies.subscribeOn(Schedulers.io())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MovieResponse>>() {
                    @Override
                    public Observable<? extends MovieResponse> call(Throwable throwable) {
                        Timber.e(throwable.getMessage());

                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<MovieResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        mMovieView.onLoadingError();
                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {

                        movieAdapter.swapData(movieResponse.getResults());
                        mMovieView.onLoadingCompleted();
                    }
                });
    }
}