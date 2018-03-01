package com.nuhkoca.udacitymoviesapp.helper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nuhkoca.udacitymoviesapp.helper.Constants;
import com.nuhkoca.udacitymoviesapp.model.favorite.MovieContract;

/**
 * Created by nuhkoca on 2/27/18.
 */

public class FavoriteMoviesDbHelper extends SQLiteOpenHelper {

    FavoriteMoviesDbHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORITE_MOVIE_TABLE = "CREATE TABLE " +
                MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_NAME + " TEXT NON NULL UNIQUE, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_GENRE + " TEXT NON NULL, " +
                MovieContract.MovieEntry.COLUMN_IMAGE + " TEXT NON NULL " +
                ")";

        db.execSQL(SQL_CREATE_FAVORITE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}