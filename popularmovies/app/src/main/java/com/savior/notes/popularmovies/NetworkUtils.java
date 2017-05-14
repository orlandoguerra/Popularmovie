package com.savior.notes.popularmovies;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Orlando on 5/10/2017.
 */

public class NetworkUtils {

    private final static String BASE_URL = "http://api.themoviedb.org";
    private final static String API_KEY = "api_key";
    private final static String KEY = "";
    private final static String IMG_PATH = "http://image.tmdb.org/t/p/w342";
    private final static String FIRST_PART = "3";
    private final static String SECOND_PART = "movie";

    public static String getPosterImage(String posterPath){
        return IMG_PATH+posterPath;
    }

    public static URL buildUrl(String orderBy) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(FIRST_PART)
                .appendPath(SECOND_PART)
                .appendPath(orderBy)
                .appendQueryParameter(API_KEY, KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildUrlDetailsMovie(String id){
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(FIRST_PART)
                .appendPath(SECOND_PART)
                .appendPath(id)
                .appendQueryParameter(API_KEY, KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
