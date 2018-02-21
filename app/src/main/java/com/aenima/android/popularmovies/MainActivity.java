package com.aenima.android.popularmovies;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aenima.android.popularmovies.core.MovieDBAPI;
import com.aenima.android.popularmovies.core.NetworkUtils;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {
    private static final int MOVIE_DB_LOADER_ID = 75;
    private static final String LIST_MOVIE_URL_EXTRA = "list_movie_url_extra";

    @BindView(R.id.loading_indicator_pb)
    ProgressBar mLoadingIndicator;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.movies_rv)
    RecyclerView movieRecyclerView;

    private RecyclerView.Adapter movieAdapter;
    RecyclerView.LayoutManager gridLayoutManager;
    private int columnNumber = 2;

    private String selectedSortBy = MovieDBAPI.MOVIE_DB_API_POPULAR_PARTIAL_URL; //default value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        gridLayoutManager = new GridLayoutManager(this, columnNumber);
        //prepare request for asynctaskloader
        Bundle bundle = new Bundle();
        URL movieDbListUrl = MovieDBAPI.getMovieListUrl(selectedSortBy);
        bundle.putString(LIST_MOVIE_URL_EXTRA, movieDbListUrl.toString());

        getSupportLoaderManager().initLoader(MOVIE_DB_LOADER_ID, bundle, this);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this ) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (args == null)
                    return;

                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                String movieDBListUrlString = args.getString(LIST_MOVIE_URL_EXTRA);
                if(movieDBListUrlString == null){
                    return null;
                }
                try{
                    URL movieListURL = new URL(movieDBListUrlString);
                    String jsonList = NetworkUtils.getHttpResponse(movieListURL);
                    return jsonList;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
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

    private void showErrorMessage() {
        Toast.makeText(this, R.string.no_connection_error, Toast.LENGTH_LONG);
    }

    private void showMovieList(String data) {

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
