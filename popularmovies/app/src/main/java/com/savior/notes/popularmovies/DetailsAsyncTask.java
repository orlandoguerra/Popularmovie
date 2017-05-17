package com.savior.notes.popularmovies;

import android.content.Context;
import android.os.AsyncTask;

import com.savior.notes.popularmovies.data.AsyncTaskCompleteListener;
import com.savior.notes.popularmovies.data.JsonUtils;
import com.savior.notes.popularmovies.data.MovieBean;

import org.json.JSONException;

import java.net.URL;

/**
 * Created by Orlando on 5/17/2017.
 */

public class DetailsAsyncTask extends AsyncTask<URL, Void, String> {

    private Context context;
    private AsyncTaskCompleteListener<String> listener;

    public DetailsAsyncTask(Context ctx, AsyncTaskCompleteListener<String> listener){
        this.context = ctx;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(URL... params) {
        URL searchUrl = params[0];
        System.out.println("-------------------------");
        return NetworkUtils.getResponseFromHttpUrl(searchUrl);

    }

    @Override
    protected void onPostExecute(String searchResults) {
        listener.onTaskComplete(searchResults);
    }
}
