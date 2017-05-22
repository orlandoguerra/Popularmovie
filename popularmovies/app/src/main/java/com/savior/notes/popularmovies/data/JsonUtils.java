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

    public static List<TrailerMovieBean> parseTrailersFormJson(String jsonStr) throws JSONException {
        JSONObject json = new JSONObject(jsonStr);
        JSONArray trailerArray = json.getJSONArray("results");
        List<TrailerMovieBean> listTrailer = new ArrayList<TrailerMovieBean>();
        for (int i = 0; i < trailerArray.length(); i++) {
            JSONObject trailer = trailerArray.getJSONObject(i);
            TrailerMovieBean tBean = parseTrailerFromJson(trailer);
            if("YouTube".equals(tBean.getSite())&& "Trailer".equals(tBean.getType())){
                listTrailer.add(tBean);
            }
        }
        return listTrailer;
    }

    public static  TrailerMovieBean parseTrailerFromJson(JSONObject trailer) throws JSONException {
        TrailerMovieBean trailerBean = new TrailerMovieBean();
        trailerBean.setId(trailer.getString("id"));
        trailerBean.setKey(trailer.getString("key"));
        trailerBean.setSite(trailer.getString("site"));
        trailerBean.setType(trailer.getString("type"));
        return trailerBean;
    }

    public static List<ReviewBean> parseReviewsFormJson(String jsonStr) throws JSONException {
        JSONObject json = new JSONObject(jsonStr);
        JSONArray reviewArray = json.getJSONArray("results");
        List<ReviewBean> listReview = new ArrayList<ReviewBean>();
        for (int i = 0; i < reviewArray.length(); i++) {
            JSONObject review = reviewArray.getJSONObject(i);
            ReviewBean rBean = parseReviewFromJson(review);
            listReview.add(rBean);

        }
        return listReview;
    }

    public static  ReviewBean parseReviewFromJson(JSONObject trailer) throws JSONException {
        ReviewBean reviewBean = new ReviewBean();
        reviewBean.setId(trailer.getString("id"));
        reviewBean.setAuthor(trailer.getString("author"));
        reviewBean.setContent(trailer.getString("content"));
        reviewBean.setUrl(trailer.getString("url"));
        return reviewBean;
    }



}
