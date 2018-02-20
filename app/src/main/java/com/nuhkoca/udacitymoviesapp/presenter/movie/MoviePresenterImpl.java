package com.nuhkoca.udacitymoviesapp.presenter.movie;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.callback.RecyclerViewItemTouchListener;
import com.nuhkoca.udacitymoviesapp.model.MovieResponse;
import com.nuhkoca.udacitymoviesapp.model.Result;
import com.nuhkoca.udacitymoviesapp.networking.ObservableHelper;
import com.nuhkoca.udacitymoviesapp.networking.RetrofitInterceptor;
import com.nuhkoca.udacitymoviesapp.view.movie.MovieAdapter;
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

public class MoviePresenterImpl implements MoviePresenter, RecyclerViewItemTouchListener {

    private MovieView mMovieView;
    private static final int SPAN_COUNT = 2;
    private MovieAdapter mMovieAdapter;

    public MoviePresenterImpl(MovieView mMovieView) {
        this.mMovieView = mMovieView;
        mMovieAdapter = new MovieAdapter(this);
    }

    @Override
    public void prepareUI(RecyclerView rvMovie) {
        rvMovie.setNestedScrollingEnabled(false);
        rvMovie.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(rvMovie.getContext(),
                SPAN_COUNT,
                1,
                false);
        rvMovie.setLayoutManager(gridLayoutManager);

        rvMovie.setAdapter(mMovieAdapter);
    }

    @Override
    public void loadPopularMovies(final Context context, String apiKey, int pageId, String tag) {
        final Retrofit retrofit = RetrofitInterceptor.build();

        Observable<MovieResponse> getMovies;

        if (Objects.equals(tag, "popular")) {
            getMovies = ObservableHelper.getPopularMovies(retrofit, apiKey, pageId);
        } else {
            getMovies = ObservableHelper.getTopRatedMovies(retrofit, apiKey, pageId);
        }

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
                        mMovieView.onLoadingError(context.getString(R.string.data_not_loaded_successfully));
                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        mMovieAdapter.swapData(movieResponse.getResults());
                        mMovieAdapter.notifyDataSetChanged();

                        mMovieView.onLoadingCompleted(context.getString(R.string.data_loaded_successfully));
                    }
                });
    }

    @Override
    public void onItemTouched(Result result, ImageView imageView) {
        mMovieView.onActivityOpened(result, imageView);
    }
}