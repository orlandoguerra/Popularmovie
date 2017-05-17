package com.savior.notes.popularmovies;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.savior.notes.popularmovies.data.AsyncTaskCompleteListener;
import com.savior.notes.popularmovies.data.Constants;
import com.savior.notes.popularmovies.data.JsonUtils;
import com.savior.notes.popularmovies.data.MovieBean;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity implements AsyncTaskCompleteListener<String>{

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    private static final String SAVED_STATE_JSON = "SAVED_STATE_JSON";
    private String searchResultsSaved;

    @BindView(R.id.image_details_movie) ImageView mUrlDisplayTextView;
    @BindView(R.id.tv_movie_title_display) TextView mTitleTextView;
    @BindView(R.id.tv_movie_date_display) TextView mDateTextView;
    @BindView(R.id.tv_movie_vote_display) TextView mVoteTextView;
    @BindView(R.id.tv_movie_plot_display) TextView mPlotTextView;
    @BindView(R.id.iv_image_status) ImageView mImageStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceState != null) {
            searchResultsSaved = savedInstanceState.getString(SAVED_STATE_JSON);
        }

        ButterKnife.bind(this);
        String updateId = getIntent().getStringExtra(Constants.MOVIE_ID);
        URL url = NetworkUtils.buildUrlDetailsMovie(updateId);
        if(searchResultsSaved == null){
            new DetailsAsyncTask(this, this).execute(url);
        }else{
            onTaskComplete(searchResultsSaved);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(SAVED_STATE_JSON, searchResultsSaved);
    }

    private void fillInformation(MovieBean movie){
        Picasso.with(MovieDetailsActivity.this).load(
                NetworkUtils.getPosterImage(movie.getPosterPath()))
                .placeholder(R.drawable.ic_group_black)
                .error(R.drawable.ic_group_black)
                .into(mUrlDisplayTextView);
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

    @Override
    public void onTaskComplete(String result) {
        if (result == null || result.equals("")) {
            return;
        }

        searchResultsSaved = result;
        try {
            MovieBean movie = JsonUtils.getMovieFromJson(result);
            fillInformation(movie);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
