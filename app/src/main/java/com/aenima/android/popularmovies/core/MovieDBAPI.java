package com.aenima.android.popularmovies.core;

import android.net.Uri;
import android.util.Log;

import com.aenima.android.popularmovies.BuildConfig;
import com.aenima.android.popularmovies.core.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by marina on 20/02/2018.
 */

public class MovieDBAPI {


    private static final String API_KEY = BuildConfig.API_KEY;

    //https://api.themoviedb.org/3/movie/popular?api_key=
    private static String MOVIE_DB_API_BASE_URL = "https://api.themoviedb.org/3/movie";
    private static String MOVIE_DB_API_API_KEY_QUERY_PARAM = "api_key";


    public static String MOVIE_DB_API_POPULAR_PARTIAL_URL = "popular";
    public static String MOVIE_DB_API_TOP_RATED_PARTIAL_URL = "top_rated";


    public static URL getMovieListUrl(String sortBy){
        LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
        queryParams.put(MOVIE_DB_API_API_KEY_QUERY_PARAM, API_KEY);
        return NetworkUtils.buildUri(MOVIE_DB_API_BASE_URL, sortBy, queryParams);
    }

    public static List<Movie> getMovieList(String jsonString){
        return Movie.getMovieList(jsonString);
    }
}
