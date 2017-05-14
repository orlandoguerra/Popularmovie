package com.savior.notes.popularmovies.data;

import android.content.Context;

import com.savior.notes.popularmovies.data.MovieBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orlando on 5/10/2017.
 */

public class JsonUtils {

    public static List<MovieBean> getMoviesFromJson(String jsonStr) throws JSONException {
        JSONObject json = new JSONObject(jsonStr);
        JSONArray movieArray = json.getJSONArray("results");
        List<MovieBean> listMovies = new ArrayList<MovieBean>();
        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject movie = movieArray.getJSONObject(i);
            listMovies.add(parseMovieFromJson(movie));
        }
        return listMovies;
    }

    public static  MovieBean getMovieFromJson(String jsonStr) throws JSONException {
        JSONObject json = new JSONObject(jsonStr);
        return parseMovieFromJson(json);
    }

    public static  MovieBean parseMovieFromJson(JSONObject movie) throws JSONException {
        MovieBean movBean = new MovieBean();
        movBean.setTitle(movie.getString("title"));
        movBean.setPosterPath(movie.getString("poster_path"));
        movBean.setId(movie.getString("id"));
        movBean.setOverview(movie.getString("overview"));
        movBean.setVote(movie.getString("vote_average"));
        movBean.setDate(movie.getString("release_date"));
        movBean.setPopularity(movie.getString("popularity"));
        return movBean;
    }

}
