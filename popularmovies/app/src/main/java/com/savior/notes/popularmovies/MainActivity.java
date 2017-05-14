package com.savior.notes.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.savior.notes.popularmovies.data.Constants;
import com.savior.notes.popularmovies.data.JsonUtils;
import com.savior.notes.popularmovies.data.MovieBean;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private MovieRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String SAVED_STATE_JSON = "SavedJson";
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<MovieBean> listMovies = null;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingIndicator = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mLoadingIndicator.setVisibility(View.VISIBLE);

        SharedPreferences sPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String orderString = sPreferences.getString(getString(R.string.order),getString(R.string.rated_value));
        sPreferences.registerOnSharedPreferenceChangeListener(this);
        new MovieTask().execute(NetworkUtils.buildUrl(orderString));
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MovieRecyclerAdapter(this, new ClickMovie());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.order))){
            String order = sharedPreferences.getString(getString(R.string.order),getString(R.string.rated_value));
            new MovieTask().execute(NetworkUtils.buildUrl(order));
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
            String searchResults = null;
            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String searchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (searchResults == null && searchResults.equals("")) {
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
}
