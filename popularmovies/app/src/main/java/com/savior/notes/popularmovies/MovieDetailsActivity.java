package com.savior.notes.popularmovies;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.savior.notes.popularmovies.data.Constants;
import com.savior.notes.popularmovies.data.JsonUtils;
import com.savior.notes.popularmovies.data.MovieBean;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    private static final String SAVED_STATE_JSON = "SAVED_STATE_JSON";
    private ImageView mUrlDisplayTextView;
    private TextView mTitleTextView;
    private TextView mDateTextView;
    private TextView mVoteTextView;
    private TextView mPlotTextView;
    private ImageView mImageStatus;
    private String searchResultsSaved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceState != null) {
            searchResultsSaved = savedInstanceState.getString(SAVED_STATE_JSON);
        }

        String updateId = getIntent().getStringExtra(Constants.MOVIE_ID);
        URL url = NetworkUtils.buildUrlDetailsMovie(updateId);
        new LoadMovieTask().execute(url);
        mUrlDisplayTextView = (ImageView) findViewById(R.id.image_details_movie);
        mTitleTextView = (TextView)findViewById(R.id.tv_movie_title_display);
        mDateTextView = (TextView)findViewById(R.id.tv_movie_date_display);
        mVoteTextView = (TextView)findViewById(R.id.tv_movie_vote_display);
        mPlotTextView = (TextView)findViewById(R.id.tv_movie_plot_display);
        mImageStatus = (ImageView) findViewById(R.id.iv_image_status);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(SAVED_STATE_JSON, searchResultsSaved);
    }

    private void fillInformation(MovieBean movie){
        Picasso.with(MovieDetailsActivity.this).load(
                NetworkUtils.getPosterImage(movie.getPosterPath())).into(mUrlDisplayTextView);
        mTitleTextView.setText(movie.getTitle());
        mDateTextView.setText(movie.getDate());
        mVoteTextView.setText(movie.getVote());
        mPlotTextView.setText(movie.getOverview());
        try{
            double d = Double.parseDouble(movie.getVote());
            Drawable sourceDrawable = null;
            final int sdk = android.os.Build.VERSION.SDK_INT;
            Log.i(TAG,movie.toString());

            if(d>=8.5){
                mImageStatus.setBackgroundResource( R.drawable.ic_sentiment_very_satisfied);
            }else if(d>=7){
                mImageStatus.setBackgroundResource(R.drawable.ic_sentiment_satisfied);
            }else if(d>=6.5){
                mImageStatus.setBackgroundResource(R.drawable.ic_sentiment_neutral);
            }else if(d>=5){
                mImageStatus.setBackgroundResource(R.drawable.ic_sentiment_dissatisfied);
            }else{
                mImageStatus.setBackgroundResource(R.drawable.ic_sentiment_very_dissatisfied);
            }
        }catch(NumberFormatException nfe){
            nfe.printStackTrace();
        }
    }

    public class LoadMovieTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //This the only task made in background
        @Override
        protected String doInBackground(URL... params) {
            if(searchResultsSaved != null){
                return searchResultsSaved;
            }
            URL searchUrl = params[0];
            return NetworkUtils.getResponseFromHttpUrl(searchUrl);
        }

        @Override
        protected void onPostExecute(String searchResults) {
            if (searchResults == null && searchResults.equals("")) {
                return;
            }

            searchResultsSaved = searchResults;
            try {
                MovieBean movie = JsonUtils.getMovieFromJson(searchResults);
                fillInformation(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
