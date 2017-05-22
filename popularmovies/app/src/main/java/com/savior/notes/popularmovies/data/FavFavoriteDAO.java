package com.savior.notes.popularmovies.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orlando on 5/22/2017.
 */

public class FavFavoriteDAO {

    SQLiteOpenHelper sqlHelper;
    public FavFavoriteDAO(SQLiteOpenHelper sqlHelper){
        this.sqlHelper = sqlHelper;
    }

    public void insert(ContentValues content){
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.insert(Constants.FAV_FIVE.TABLE_NAME, null, content);
        db.close();
    }

    public boolean delete(String id) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        boolean result = db.delete(Constants.FAV_FIVE.TABLE_NAME, " _id  =" + id, null) > 0;
        db.close();
        return result;
    }

    public boolean isFavorite(String id){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(Constants.FAV_FIVE.TABLE_NAME, null, "_id = ?", new String[]{id}, null, null, "_id ASC");
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

}
