package com.savior.notes.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.savior.notes.popularmovies.data.AsyncTaskCompleteListener;
import com.savior.notes.popularmovies.data.Constants;
import com.savior.notes.popularmovies.data.FavFavoriteDAO;
import com.savior.notes.popularmovies.data.JsonUtils;
import com.savior.notes.popularmovies.data.MovieBean;
import com.savior.notes.popularmovies.data.PopularMoviesDatabaseHelper;
import com.savior.notes.popularmovies.data.ReviewBean;
import com.savior.notes.popularmovies.data.TrailerMovieBean;
import com.savior.notes.popularmovies.recycler.ReviewRecyclerAdapter;
import com.savior.notes.popularmovies.recycler.TrailerRecyclerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity{

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    private FavFavoriteDAO favFavoriteDAO;
    private static final String SAVED_STATE_JSON = "SAVED_STATE_JSON";
    private static final int DETAILS_SEARCH_LOADER = 2434332;
    private String searchResultsSaved;
    private static final String TRAILER_STATE_JSON = "TRAILER_STATE_JSON";
    private String trailerResultsSaved;
    private static final int TRAILER_SEARCH_LOADER = 435435435;
    private static final String REVIEWS_STATE_JSON = "REVIEWS_STATE_JSON";
    private String reviewsResultsSaved;
    private static final int REVIEWS_SEARCH_LOADER = 543543;
    private TrailerRecyclerAdapter mAdapter;
    private ReviewRecyclerAdapter rAdapter;
    private List<TrailerMovieBean> trailers;
    private boolean isfavorite;
    private MovieBean movie;

    @BindView(R.id.image_details_movie) ImageView mUrlDisplayTextView;
    @BindView(R.id.tv_movie_title_display) TextView mTitleTextView;
    @BindView(R.id.tv_movie_date_display) TextView mDateTextView;
    @BindView(R.id.tv_movie_vote_display) TextView mVoteTextView;
    @BindView(R.id.tv_movie_plot_display) TextView mPlotTextView;
    @BindView(R.id.iv_image_status) ImageView mImageStatus;
    @BindView(R.id.rv_trailers) RecyclerView mRecyclerTrilerView;
    @BindView(R.id.rv_review) RecyclerView mRecyclerReviewView;
    @BindView(R.id.iv_image_like) ImageView mImageLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            searchResultsSaved = savedInstanceState.getString(SAVED_STATE_JSON);
            trailerResultsSaved = savedInstanceState.getString(TRAILER_STATE_JSON);
            reviewsResultsSaved = savedInstanceState.getString(REVIEWS_STATE_JSON);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerTrilerView.setLayoutManager(layoutManager);
        mRecyclerTrilerView.setHasFixedSize(true);
        mAdapter = new TrailerRecyclerAdapter(this, new ClickTrailer());
        mRecyclerTrilerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(this);
        mRecyclerReviewView.setLayoutManager(layoutManagerReview);
        mRecyclerReviewView.setHasFixedSize(true);
        rAdapter = new ReviewRecyclerAdapter(this);
        mRecyclerReviewView.setAdapter(rAdapter);

        String movieId = getIntent().getStringExtra(Constants.MOVIE_ID);

        //Load Details
        runLoader(searchResultsSaved, DETAILS_SEARCH_LOADER,NetworkUtils.buildUrlDetailsMovie(movieId), new DetailsMovieListener());
        //Load trailers
        runLoader(trailerResultsSaved, TRAILER_SEARCH_LOADER,NetworkUtils.getTrailers(movieId), new TrailerMovieListener());
        //Load Reviews
        runLoader(reviewsResultsSaved, REVIEWS_SEARCH_LOADER,NetworkUtils.getReviews(movieId), new ReviewMovieListener());

        favFavoriteDAO = new FavFavoriteDAO(new PopularMoviesDatabaseHelper(this));


    }

    private void runLoader(String resultSaved, int idLoader, URL url, AsyncTaskCompleteListener listener){
        if(resultSaved == null){
            getSupportLoaderManager().initLoader(idLoader, null, new DetailsLoader(this, listener));
            Bundle queryBundle = new Bundle();
            queryBundle.putString(DetailsLoader.SEARCH_QUERY, url.toString());
            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<String> searchLoader = loaderManager.getLoader(idLoader);
            if (searchLoader == null) {
                loaderManager.initLoader(idLoader, queryBundle, new DetailsLoader(this, listener));
            } else {
                loaderManager.restartLoader(idLoader, queryBundle, new DetailsLoader(this, listener));
            }
        }else{
            listener.onTaskComplete(resultSaved);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(SAVED_STATE_JSON, searchResultsSaved);
    }

    public void changeFavorite(View v) {
        if(isfavorite){
            favFavoriteDAO.delete(movie.getId());
        }else{
            ContentValues favFavoriteContent = new ContentValues();
            favFavoriteContent.put(Constants.FAV_FIVE._ID,movie.getId());
            favFavoriteContent.put(Constants.FAV_FIVE.POSTER_PATH,movie.getPosterPath());
            favFavoriteContent.put(Constants.FAV_FIVE.TITLE,movie.getTitle());
            favFavoriteDAO.insert(favFavoriteContent);
        }

        isfavorite = !isfavorite;
        mImageLike.setBackgroundResource(isfavorite?R.drawable.ic_star_black:R.drawable.ic_star_border_black);

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
        isfavorite = favFavoriteDAO.isFavorite(movie.getId());
        if(isfavorite){
            mImageLike.setBackgroundResource(R.drawable.ic_star_black);
        }else{
            mImageLike.setBackgroundResource(R.drawable.ic_star_border_black);
        }

        try{
            double d = Double.parseDouble(movie.getVote());
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

    class ClickTrailer implements ListItemClickListener{
        @Override
        public void onListItemClick(int clickedItem) {
            String ytVideo = NetworkUtils.getYouTubeVideo(trailers.get(clickedItem).getKey());
            Uri addressUri = Uri.parse(ytVideo);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(addressUri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    class DetailsMovieListener  implements AsyncTaskCompleteListener<String>{
        @Override
        public void onTaskComplete(String result) {
            if (result == null || result.equals("")) {
                return;
            }
            searchResultsSaved = result;
            try {
                movie = JsonUtils.getMovieFromJson(result);
                fillInformation(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class TrailerMovieListener  implements AsyncTaskCompleteListener<String>{
        @Override
        public void onTaskComplete(String result) {
            if (result == null || result.equals("")) {
                return;
            }

            trailerResultsSaved = result;
            Log.i(this.getClass().getName(),result);
            try {
                trailers = JsonUtils.parseTrailersFormJson(result);
                mAdapter.swapCursor(trailers);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReviewMovieListener  implements AsyncTaskCompleteListener<String>{
        @Override
        public void onTaskComplete(String result) {
            if (result == null || result.equals("")) {
                return;
            }

            reviewsResultsSaved = result;
            Log.i(this.getClass().getName(),result);
            try {
                List<ReviewBean> reviews= JsonUtils.parseReviewsFormJson(result);
                rAdapter.swapCursor(reviews);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
