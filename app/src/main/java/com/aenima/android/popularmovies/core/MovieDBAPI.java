package com.aenima.android.popularmovies.core;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;

/**
 * Created by marina on 20/02/2018.
 */

public class MovieDBAPI {

    private static String MOVIE_DB_BASE_URL = "http://image.tmdb.org/t/p/";

    //https://api.themoviedb.org/3/movie/popular?api_key=
    private static String MOVIE_DB_API_BASE_URL = "https://api.themoviedb.org/3/movie";
    private static String MOVIE_DB_API_POPULAR_PARTIAL_URL = "/popular?";
    private static String MOVIE_DB_API_TOP_RATED_PARTIAL_URL = "/top_rated?";
    private static String MOVIE_DB_API_API_KEY_QUERY_PARAM = "api_key";
    private static String POSTER_SIZE = "w185";


    public static URL getMovieListUrl(){
        LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
        queryParams.put(MOVIE_DB_API_API_KEY_QUERY_PARAM, "");
        return NetworkUtils.buildUri(MOVIE_DB_BASE_URL + MOVIE_DB_API_POPULAR_PARTIAL_URL, queryParams);
    }

    public static void makeMovieDBListQuery(){
        String apitest = "";
        LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
        queryParams.put(MOVIE_DB_API_API_KEY_QUERY_PARAM, "");
        URL movieListUrl = NetworkUtils.buildUri(MOVIE_DB_BASE_URL + MOVIE_DB_API_POPULAR_PARTIAL_URL, queryParams);
        try {
            apitest = NetworkUtils.getHttpResponse(movieListUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(MovieDBAPI.class.getName(), apitest);
    }
}
