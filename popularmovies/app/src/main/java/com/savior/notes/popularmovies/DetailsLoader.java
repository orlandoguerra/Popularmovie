package com.savior.notes.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.savior.notes.popularmovies.data.AsyncTaskCompleteListener;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Orlando on 5/17/2017.
 */

public class DetailsLoader implements LoaderManager.LoaderCallbacks<String>{
    private AsyncTaskCompleteListener<String> listener;
    private Context context;
    public static final String SEARCH_QUERY = "query";

    public  DetailsLoader(Context ctx, AsyncTaskCompleteListener<String> listener){
        this.context = ctx;
        this.listener = listener;
    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(context) {

            @Override
            protected void onStartLoading() {
                if (args == null) {
                    return;
                }
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                String searchQueryUrlString = args.getString(SEARCH_QUERY);
                if (searchQueryUrlString == null || TextUtils.isEmpty(searchQueryUrlString)) {
                    return null;
                }

                try {
                    URL searchUrl = new URL(searchQueryUrlString);
                    return NetworkUtils.getResponseFromHttpUrl(searchUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        listener.onTaskComplete(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
