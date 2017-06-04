package com.savior.notes.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Orlando on 5/31/2017.
 */

public class ConstantsContract {

    public static final String AUTHORITY = "com.savior.notes.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVORITE = "favorite";

    public static class FavoriteEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();
        public static final String TABLE_NAME = "favorite";
        public static final String title = "title";
        public static final String poster = "poster";
    }
}
