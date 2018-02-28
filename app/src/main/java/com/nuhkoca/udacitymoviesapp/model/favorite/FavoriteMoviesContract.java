package com.nuhkoca.udacitymoviesapp.model.favorite;

import android.provider.BaseColumns;

/**
 * Created by nuhkoca on 2/27/18.
 */

public class FavoriteMoviesContract {

    public static final class FavoriteMoviesEntry implements BaseColumns {

        public static final String TABLE_NAME = "movies";
        public static String ID = "id";
        public static final String COLUMN_MOVIE_NAME = "movieName";
        public static final String COLUMN_MOVIE_GENRE = "movieGenre";
        public static final String COLUMN_IMAGE = "movieImage";
    }
}