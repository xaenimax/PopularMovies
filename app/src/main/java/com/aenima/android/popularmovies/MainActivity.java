package com.aenima.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aenima.android.popularmovies.core.MovieDBAPI;
import com.aenima.android.popularmovies.core.database.MovieDbHelper;
import com.aenima.android.popularmovies.core.model.MovieList;
import com.aenima.android.popularmovies.core.model.ReviewList;
import com.aenima.android.popularmovies.core.network.NetworkUtils;
import com.aenima.android.popularmovies.core.model.Movie;
import com.aenima.android.popularmovies.core.movieadapter.MovieAdapter;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {// implements LoaderManager.LoaderCallbacks<String>{
    //private static final int MOVIE_DB_LOADER_ID = 75;
    private SQLiteDatabase mSqLiteDatabase;
    MovieDbHelper movieDbHelper;
    private static final String RECYCLER_VIEW_STATE = "recycler_view_state";
    private Parcelable mRecyclerViewState = null;

    @BindView(R.id.loading_indicator_pb)
    public ProgressBar mLoadingIndicator;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.movies_rv)
    RecyclerView movieRecyclerView;

    RecyclerView.LayoutManager gridLayoutManager;
    private int columnNumber = 2;

    private String selectedSortBy;
    SharedPreferences sharedPreferences;
    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        movieDbHelper = new MovieDbHelper(this);
        mSqLiteDatabase = movieDbHelper.getWritableDatabase();


        gridLayoutManager = new StaggeredGridLayoutManager(columnNumber, 1);
        //gridLayoutManager.onRestoreInstanceState(state);
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        selectedSortBy = sharedPreferences.getString(getString(R.string.preference_file_key), MovieDBAPI.MOVIE_DB_API_POPULAR_PARTIAL_URL);
        //startAsyncLoader();
        loadMovies();

    }

    private void loadSavedMovies() {
        this.showMovieList(movieDbHelper.getFavouriteMovie(mSqLiteDatabase));
    }

    private void loadMovies(){
        if (selectedSortBy.equals(MovieDBAPI.FAVOURITE_KEY)){
            loadSavedMovies();
        } else {
            Call<MovieList> callBack = MovieDBAPI.getMovies(selectedSortBy);
            callBack.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                    MovieList results = response.body();
                    if (results != null) {
                        showMovieList(results.movieList);
                    }
                }

                @Override
                public void onFailure(Call<MovieList> call, Throwable t) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showErrorMessage();

                        }
                    });
                }
            });
        }
    }

    /*
    private  void startAsyncLoader(){
        Bundle bundle = new Bundle();
        //URL movieDbListUrl = MovieDBAPI.getMovieListUrl(selectedSortBy);
        bundle.putString(SORT_BY_EXTRA,selectedSortBy);
        // movieRecyclerView.setHasFixedSize(true);
        if (getSupportLoaderManager().getLoader(MOVIE_DB_LOADER_ID) == null) {
            getSupportLoaderManager().initLoader(MOVIE_DB_LOADER_ID, bundle, this);
        } else {
            getSupportLoaderManager().restartLoader(MOVIE_DB_LOADER_ID, bundle, this);

        }
    }
    */

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRecyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        mRecyclerViewState = gridLayoutManager.onSaveInstanceState();
        savedInstanceState.putParcelable(RECYCLER_VIEW_STATE, mRecyclerViewState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_popular) {
            sharedPreferences.edit().putString(getString(R.string.preference_file_key), MovieDBAPI.MOVIE_DB_API_POPULAR_PARTIAL_URL).apply();
            if (!selectedSortBy.equals(MovieDBAPI.MOVIE_DB_API_POPULAR_PARTIAL_URL)){
                selectedSortBy = MovieDBAPI.MOVIE_DB_API_POPULAR_PARTIAL_URL;
                //startAsyncLoader();
                loadMovies();
            }

            return true;
        }
        else if(id == R.id.action_top_rated){
            sharedPreferences.edit().putString(getString(R.string.preference_file_key), MovieDBAPI.MOVIE_DB_API_TOP_RATED_PARTIAL_URL).apply();
            if (!selectedSortBy.equals(MovieDBAPI.MOVIE_DB_API_TOP_RATED_PARTIAL_URL)){
                selectedSortBy = MovieDBAPI.MOVIE_DB_API_TOP_RATED_PARTIAL_URL;
                //startAsyncLoader();
                loadMovies();
            }
            return true;
        }
        else if(id == R.id.action_favourite){
            sharedPreferences.edit().putString(getString(R.string.preference_file_key), MovieDBAPI.FAVOURITE_KEY).apply();
            selectedSortBy = MovieDBAPI.FAVOURITE_KEY;
            loadSavedMovies();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void showErrorMessage() {
        Toast.makeText(this, R.string.no_connection_error, Toast.LENGTH_LONG).show();
    }

    private void showMovieList(final List<Movie> movieList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(movieAdapter == null) {
                    movieAdapter = new MovieAdapter(movieList, new MovieAdapter.OnMovieClickListener() {
                        @Override
                        public void onMovieClick(Movie movie) {

                            Intent detailActivityIntent = new Intent(MainActivity.this, DetailActivity.class);
                            detailActivityIntent.putExtra(getString(R.string.EXTRA_MOVIE_KEY), movie);
                            startActivity(detailActivityIntent);
                        }
                    });
                    movieRecyclerView.setAdapter(movieAdapter);
                    // use a gridlayout
                    movieRecyclerView.setLayoutManager(gridLayoutManager);
                }
                else {
                    movieAdapter.updateData(movieList);
                    movieAdapter.notifyDataSetChanged();
                }
                if(mRecyclerViewState != null) {
                    gridLayoutManager.onRestoreInstanceState(mRecyclerViewState);
                }
            }
        });

    }

    /*
    public static class MovieListLoader extends AsyncTaskLoader<String>{
        Bundle mBundle;

        MovieListLoader(Context context, Bundle args) {
            super(context);
            mBundle = args;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (mBundle == null)
                return;

            forceLoad();
        }

        @Override
        public String loadInBackground() {
            String movieDBListUrlString = mBundle.getString(SORT_BY_EXTRA);
            if(movieDBListUrlString == null){
                return null;
            }
            try{
                URL movieListURL = new URL(movieDBListUrlString);
                return NetworkUtils.getMovieList(movieListURL);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        return new MovieListLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (data == null) {
            showErrorMessage();
        } else {
            showMovieList(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
    */
}
