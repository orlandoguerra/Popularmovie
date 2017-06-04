package com.savior.notes.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orlando on 5/22/2017.
 */

public class FavFavoriteDAO {
    /*

    Context mContext;

    public FavFavoriteDAO(Context mContext){
        this.mContext = mContext;
    }

    public void insert(ContentValues content){
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri uri = contentResolver.insert(ConstantsContract.FavoriteEntry.CONTENT_URI, content);
    }

    public boolean delete(String id) {
        Uri returnUri = ContentUris. (ConstantsContract.FavoriteEntry.CONTENT_URI, id);
        ContentResolver contentResolver = mContext.getContentResolver();
        int deletedRecords = contentResolver.delete(returnUri, null, null);
        boolean result = deletedRecords > 0;
        return result;
    }

    public boolean isFavorite(String id){
        Cursor cursor = mContext.getContentResolver().query(ConstantsContract.FavoriteEntry.CONTENT_URI, null,
                "_id = ?", new String[]{id},  "_id ASC");
        if(cursor.moveToFirst()){
            return true;
        }
        return false;
    }

    public List<MovieBean> listFavorites(){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(Constants.FAV_FIVE.TABLE_NAME, null, null, null, null, null, "_id ASC");
        List<MovieBean> listMovies = new ArrayList<MovieBean>();
        while(cursor.moveToNext()){
            MovieBean movBean = new MovieBean();
            movBean.setTitle(cursor.getString(cursor.getColumnIndex(Constants.FAV_FIVE.TITLE)));
            movBean.setPosterPath(cursor.getString(cursor.getColumnIndex(Constants.FAV_FIVE.POSTER_PATH)));
            movBean.setId(cursor.getString(cursor.getColumnIndex(Constants.FAV_FIVE._ID)));
            listMovies.add(movBean);
        }
        cursor.close();
        return listMovies;
    }
*/
}
