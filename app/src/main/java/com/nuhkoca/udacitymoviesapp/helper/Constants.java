package com.nuhkoca.udacitymoviesapp.helper;

/**
 * Created by nuhkoca on 2/23/18.
 */

public class Constants {

    public enum TYPES {
        REVIEW,
        TRAILER
    }

    public static final String MOVIE_MODEL_TAG = "movie-model";

    public static final int PERCENTAGE_TO_ANIMATE_FAB = 90;

    public static final int UNDERLINE_WIDTH_TO_VIEW = 50;

    public static final String YOUTUBE_PREFIX = "http://www.youtube.com/watch?v=";

    public static final String W500_IMAGE_URL_PREFIX = "http://image.tmdb.org/t/p/w500/";

    public static final String W300_IMAGE_URL_PREFIX = "http://image.tmdb.org/t/p/w300/";

    public static final String THUMBNAIL_URL_PREFIX = "https://img.youtube.com/vi/";

    private Constants(){
        throw new AssertionError();
    }
}
