package com.savior.notes.popularmovies;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.savior.notes.popularmovies.data.ConstantsContract;
import com.savior.notes.popularmovies.data.PopularMoviesDatabaseHelper;

/**
 * Created by Orlando on 5/31/2017.
 */

public class FavoriteContentProvider extends ContentProvider {
    public static final int URL_SIMPLE = 100;
    public static final int URL_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ConstantsContract.AUTHORITY, ConstantsContract.PATH_FAVORITE, URL_SIMPLE);
        uriMatcher.addURI(ConstantsContract.AUTHORITY, ConstantsContract.PATH_FAVORITE + "/#", URL_WITH_ID);
        return uriMatcher;
    }

    SQLiteOpenHelper sqlHelper;
    public boolean onCreate() {
        sqlHelper = new PopularMoviesDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = sqlHelper.getWritableDatabase();
        long id = db.insert(ConstantsContract.FavoriteEntry.TABLE_NAME, null, values);
        if ( id > 0 ) {
            Uri returnUri = ContentUris.withAppendedId(ConstantsContract.FavoriteEntry.CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(uri, null);
            return returnUri;
        } else {
            throw new android.database.SQLException("Failed to insert row into " + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = sqlHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;
        switch (match) {
            case URL_SIMPLE:
                retCursor =  db.query(ConstantsContract.FavoriteEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case URL_WITH_ID:
                String id = uri.getPathSegments().get(1);
                retCursor =  db.query(ConstantsContract.FavoriteEntry.TABLE_NAME,projection,"_id=?",new String[]{id},null,null,sortOrder);
                break;
            default:throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = sqlHelper.getWritableDatabase();
        return db.delete(ConstantsContract.FavoriteEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = sqlHelper.getWritableDatabase();
        int updated;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case URL_WITH_ID:
                String id = uri.getPathSegments().get(1);
                updated = db.update(ConstantsContract.FavoriteEntry.TABLE_NAME,values,"_id=?",new String[]{id});
                break;
            default:throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (updated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
