package com.nuhkoca.udacitymoviesapp.model.favorite;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by nuhkoca on 2/28/18.
 */

public class MovieContract {
    public static final String AUTHORITY = "com.nuhkoca.udacitymoviesapp";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIE = "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_NAME = "movieName";
        public static final String COLUMN_MOVIE_GENRE = "movieGenre";
        public static final String COLUMN_IMAGE = "movieImage";
    }
}