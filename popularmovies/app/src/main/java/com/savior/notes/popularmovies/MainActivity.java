package com.savior.notes.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.savior.notes.popularmovies.data.Constants;
import com.savior.notes.popularmovies.data.FavFavoriteDAO;
import com.savior.notes.popularmovies.data.JsonUtils;
import com.savior.notes.popularmovies.data.MovieBean;
import com.savior.notes.popularmovies.data.PopularMoviesDatabaseHelper;
import com.savior.notes.popularmovies.recycler.MovieRecyclerAdapter;

import org.json.JSONException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    @BindView(R.id.rv_movies) RecyclerView mRecyclerView;
    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;

    private MovieRecyclerAdapter mAdapter;
    private List<MovieBean> listMovies = null;
    private FavFavoriteDAO favFavoriteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        SharedPreferences sPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String orderString = sPreferences.getString(getString(R.string.order),getString(R.string.rated_value));
        Log.i(this.getClass().getName(),"xxxxxxxxxxxxxxxxxxxxxxxxx"+orderString);
        sPreferences.registerOnSharedPreferenceChangeListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this,numberOfColumns());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MovieRecyclerAdapter(this, new ClickMovie());
        mRecyclerView.setAdapter(mAdapter);
        favFavoriteDAO = new FavFavoriteDAO(new PopularMoviesDatabaseHelper(this));

        if(getString(R.string.favorite_value).equals(orderString)){
            listMovies = favFavoriteDAO.listFavorites();
            mAdapter.swapCursor(listMovies);
            mLoadingIndicator.setVisibility(View.INVISIBLE);
        }else{
            new MovieTask().execute(NetworkUtils.buildUrl(orderString));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String orderString = sPreferences.getString(getString(R.string.order),getString(R.string.rated_value));
        if(getString(R.string.favorite_value).equals(orderString)){
            listMovies = favFavoriteDAO.listFavorites();
            mAdapter.swapCursor(listMovies);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.order))){
            String order = sharedPreferences.getString(getString(R.string.order),getString(R.string.rated_value));
            if(getString(R.string.favorite_value).equals(order)){
                listMovies = favFavoriteDAO.listFavorites();
                mAdapter.swapCursor(listMovies);
            }else {
                new MovieTask().execute(NetworkUtils.buildUrl(order));
            }
        }
    }


    class ClickMovie implements ListItemClickListener{
        @Override
        public void onListItemClick(int clickedItem) {
            Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
            intent.putExtra(Constants.MOVIE_ID, listMovies.get(clickedItem).getId());
            startActivity(intent);
        }
    }

    public class MovieTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            return NetworkUtils.getResponseFromHttpUrl(searchUrl);
        }

        @Override
        protected void onPostExecute(String searchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (searchResults == null || searchResults.equals("")) {
                return;
            }
            try {
                listMovies = JsonUtils.getMoviesFromJson(searchResults);
                mAdapter.swapCursor(listMovies);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_settings) {
            Intent intent =  new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }
}
