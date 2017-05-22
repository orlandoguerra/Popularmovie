package com.savior.notes.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by Orlando on 5/11/2017.
 */

public class Constants {

    public static final String MOVIE_ID = "MOVIE_ID";


    public static class FAV_FIVE implements BaseColumns {
        public static final String TABLE_NAME = "FAV_FIVE";
        public static final String TITLE = "TITLE";
        public static final String POSTER_PATH = "POSTER_PATH";
    }

}