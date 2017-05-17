package com.savior.notes.popularmovies.data;

/**
 * Created by Orlando on 5/17/2017.
 */

public interface AsyncTaskCompleteListener <T> {
    public void onTaskComplete(T result);
}
