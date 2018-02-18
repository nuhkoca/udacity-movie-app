package com.nuhkoca.udacitymoviesapp.networking;

import com.nuhkoca.udacitymoviesapp.model.MovieResponse;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by nuhkoca on 2/16/18.
 */

public class ObservableHelper {
    public static Observable<MovieResponse> getPopularMovies(Retrofit retrofit, String apiKey, int pageId) {
        IMovieAPI iMovieAPI = retrofit.create(IMovieAPI.class);

        return iMovieAPI.getPopularMovies(apiKey, pageId);
    }

    public static Observable<MovieResponse> getTopRatedMovies(Retrofit retrofit, String apiKey, int pageId) {
        IMovieAPI iMovieAPI = retrofit.create(IMovieAPI.class);

        return iMovieAPI.getTopRatedMovies(apiKey, pageId);
    }
}
