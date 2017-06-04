package com.savior.notes.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.savior.notes.popularmovies.data.ConstantsContract;
import com.savior.notes.popularmovies.data.PopularMoviesDatabaseHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Created by Orlando on 5/31/2017.
 */

@RunWith(AndroidJUnit4.class)
public class TestFavoriteContentProvider {

    private final Context mContext = InstrumentationRegistry.getTargetContext();
    SQLiteOpenHelper sqlHelper = new PopularMoviesDatabaseHelper(mContext);

    @Before
    public void setUp() {
        SQLiteDatabase database = sqlHelper.getWritableDatabase();
        database.delete(ConstantsContract.FavoriteEntry.TABLE_NAME, null, null);
    }

    @Test
    public void testInsert() {
        ContentValues testValues = new ContentValues();
        testValues.put(ConstantsContract.FavoriteEntry.title,"Test");
        testValues.put(ConstantsContract.FavoriteEntry.poster,"Test");
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri uri = contentResolver.insert(ConstantsContract.FavoriteEntry.CONTENT_URI, testValues);
        Cursor cursor = mContext.getContentResolver().query(
                ConstantsContract.FavoriteEntry.CONTENT_URI, null, null, null, null);
        assertTrue("Cursor count:"+cursor.getCount(),cursor.getCount() == 1);
    }

    @Test
    public void testSelect() {
        Cursor cursor = mContext.getContentResolver().query(
                ConstantsContract.FavoriteEntry.CONTENT_URI, null, null, null, null);
        assertTrue("Select Cursor count:"+cursor.getCount(),cursor.getCount() == 0);
    }

}
