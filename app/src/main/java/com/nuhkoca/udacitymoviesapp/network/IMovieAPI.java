package com.nuhkoca.udacitymoviesapp.network;

import com.nuhkoca.udacitymoviesapp.model.details.DetailsResponse;
import com.nuhkoca.udacitymoviesapp.model.movie.MovieResponse;
import com.nuhkoca.udacitymoviesapp.model.review.ReviewResponse;
import com.nuhkoca.udacitymoviesapp.model.video.VideoResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
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

    @GET("movie/upcoming")
    Observable<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey, @Query("page") int pageId);

    @GET("movie/now_playing")
    Observable<MovieResponse> getNowPlayingMovies(@Query("api_key") String apiKey, @Query("page") int pageId);

    @GET("movie/{id}/reviews")
    Observable<ReviewResponse> getReviews(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Observable<VideoResponse> getTrailers(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}")
    Observable<DetailsResponse> getOtherDetails(@Path("id") int movieId, @Query("api_key") String apiKey);
}