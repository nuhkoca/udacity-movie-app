package com.nuhkoca.udacitymoviesapp.network;

import com.nuhkoca.udacitymoviesapp.model.MovieResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by nuhkoca on 2/16/18.
 */

public interface IMovieAPI {

    @GET("movie/popular")
    Observable<MovieResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int pageId);

    @GET("movie/top_rated")
    Observable<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int pageId);
}
