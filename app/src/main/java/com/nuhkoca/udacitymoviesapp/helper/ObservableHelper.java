package com.nuhkoca.udacitymoviesapp.helper;

import com.nuhkoca.udacitymoviesapp.model.movie.MovieResponse;
import com.nuhkoca.udacitymoviesapp.network.IMovieAPI;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by nuhkoca on 2/16/18.
 */

public class ObservableHelper {
    private IMovieAPI iMovieAPI;
    private String apiKey;

    public ObservableHelper(Retrofit retrofit, String apiKey) {
        iMovieAPI = getIMovieAPI(retrofit);
        this.apiKey = apiKey;
    }

    public Observable<MovieResponse> getPopularMovies(int pageId) {
        return iMovieAPI.getPopularMovies(apiKey, pageId);
    }

    public Observable<MovieResponse> getTopRatedMovies(int pageId) {
        return iMovieAPI.getTopRatedMovies(apiKey, pageId);
    }

    public Observable<MovieResponse> getTrailers(int movieId) {
        return iMovieAPI.getTrailers(movieId, apiKey);
    }

    private IMovieAPI getIMovieAPI(Retrofit retrofit) {
        return retrofit.create(IMovieAPI.class);
    }
}