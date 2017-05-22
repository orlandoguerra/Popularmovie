package com.savior.notes.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Orlando on 5/22/2017.
 */

public class PopularMoviesDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PopularMovies";
    private static final int DB_VERSION = 1;

    public PopularMoviesDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+Constants.FAV_FIVE.TABLE_NAME+" (_id INTEGER PRIMARY KEY "
                + ","+Constants.FAV_FIVE.TITLE+" TEXT"
                + ","+Constants.FAV_FIVE.POSTER_PATH+" TEXT"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
