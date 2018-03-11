package com.nuhkoca.udacitymoviesapp.helper;

/**
 * Created by nuhkoca on 2/23/18.
 */

public class Constants {
    public enum TYPES {
        REVIEW,
        TRAILER,
        DETAILS
    }

    public static final String NULL_CHECK = "null";
    public static final String MOVIE_MODEL_TAG = "movie-model";
    public static final String GENRE_TAG = "genre";
    public static final String TRANSITION_TAG = "image-transition";
    public static final int PERCENTAGE_TO_ANIMATE_FAB = 90;
    public static final byte MAX_REVIEW_LINE = 7;
    public static final String YOUTUBE_PREFIX = "http://www.youtube.com/watch?v=";
    public static final String W500_IMAGE_URL_PREFIX = "http://image.tmdb.org/t/p/w500";
    public static final String W300_IMAGE_URL_PREFIX = "http://image.tmdb.org/t/p/w300";
    public static final String THUMBNAIL_URL_PREFIX = "https://img.youtube.com/vi/";
    public static final int CHILD_ACTIVITY_REQUEST_CODE = 123;
    public static final String DATABASE_NAME = "movies.db";
    public static final int DATABASE_VERSION = 1;
    public static final int MOVIE = 100;
    public static final int MOVIE_WITH_ID = 101;
    public static final int MOVIE_LOADER_ID = 1005;
    public static final int VIEW_HOLDER_TAG_1 = 1236784534;
    public static final int VIEW_HOLDER_TAG_2 = 1136783424;
    public static final String REVIEW_CONTENT_EXTRA = "content";
    public static final String REVIEW_AUTHOR_EXTRA = "author";
    public static final String REVIEW_MOVIE_EXTRA = "movie";
    public static final String INTENT_CONDITION = "com.android";
    public static final String NUMBER_FORMAT = "#,###,###";
    public static final String YOUTUBE_VIDEO_ID = "youtube-video-id";
    public static final String TITLE_STATE = "title-state";
    public static final String BUDGET_STATE = "budget-state";
    public static final String OVERVIEW_STATE = "overview-state";
    public static final String ORIGINAL_TITLE_STATE = "original-title-state";
    public static final String RELEASE_DATE_STATE = "release-date-state";
    public static final String VOTE_COUNT_STATE = "vote-count-state";
    public static final String RATING_STATE = "rating-state";
    public static final String RATING_TEXT_STATE = "rating-text-state";
    public static final String POPULARITY_STATE = "popularity-state";
    public static final String POPULARITY_TEXT_STATE = "popularity-text-state";
    public static final String GENRES_STATE = "genres-state";
    public static final String POSTER_STATE = "poster-state";
    public static final String REVENUE_STATE = "revenue-state";
    public static final String HOMEPAGE_STATE = "homepage-state";
    public static final String COMPANIES_STATE = "companies-state";
    public static final String COUNTRIES_STATE = "countries-state";
    public static final String LANGUAGES_STATE = "languages-state";
    public static final String REVIEW_STATE = "review-state";
    public static final String TRAILER_STATE = "trailer-state";
    public static final String RUNTIME_STATE = "runtime-state";
    public static final String REVIEW_COUNT_STATE = "review-count-state";
    public static final String TRAILER_COUNT_STATE = "trailer-count-state";
    public static final int VIEW_PAGER_SIZE = 4;
    public static final int HIDE_THRESHOLD = 20;

    private Constants() {
        throw new AssertionError();
    }
}