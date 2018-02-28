package com.nuhkoca.udacitymoviesapp.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nuhkoca.udacitymoviesapp.model.favorite.FavoriteMoviesContract;

/**
 * Created by nuhkoca on 2/27/18.
 */

public class FavoriteMoviesDbHelper extends SQLiteOpenHelper {

    public FavoriteMoviesDbHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORITE_MOVIE_TABLE = "CREATE TABLE " +
                FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_NAME + " (" +
                FavoriteMoviesContract.FavoriteMoviesEntry.ID + " INTEGER PRIMARY KEY," +
                FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_NAME + " TEXT NON NULL," +
                FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_GENRE + " TEXT NON NULL," +
                FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_IMAGE + " TEXT NON NULL" +
                ");";

        db.execSQL(SQL_CREATE_FAVORITE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}