package com.aenima.android.popularmovies.core;

import com.aenima.android.popularmovies.BuildConfig;
import com.aenima.android.popularmovies.core.model.Movie;
import com.aenima.android.popularmovies.core.model.MovieList;
import com.aenima.android.popularmovies.core.model.ReviewList;
import com.aenima.android.popularmovies.core.model.VideoList;
import com.aenima.android.popularmovies.core.network.NetworkUtils;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by marina on 20/02/2018.
 */

public class MovieDBAPI {


    private static final String API_KEY = BuildConfig.API_KEY;

    //https://api.themoviedb.org/3/movie/popular?api_key=
    private static String MOVIE_DB_API_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static String MOVIE_DB_API_API_KEY_QUERY_PARAM = "api_key";


    public static String MOVIE_DB_API_POPULAR_PARTIAL_URL = "popular";
    public static String MOVIE_DB_API_TOP_RATED_PARTIAL_URL = "top_rated";
    public static final String FAVOURITE_KEY = "favourite_key";


    public static URL getMovieListUrl(String sortBy){
        LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
        queryParams.put(MOVIE_DB_API_API_KEY_QUERY_PARAM, API_KEY);
        return NetworkUtils.buildUri(MOVIE_DB_API_BASE_URL, sortBy, queryParams);
    }
/*
    public static List<Movie> getMovies(String sortBy){
        LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
        queryParams.put(MOVIE_DB_API_API_KEY_QUERY_PARAM, API_KEY);
        return NetworkUtils.getMovieList(MOVIE_DB_API_BASE_URL, sortBy, queryParams);
    }
*/
    public static List<Movie> getMovieList(String jsonString){
        return Movie.getMovieList(jsonString);
    }

    public static Call<MovieList> getMovies(String sortBy){
        return NetworkUtils.getMovies(MOVIE_DB_API_BASE_URL , sortBy, API_KEY);
    }

    public static Call<ReviewList> getMovieReviews(String movieId){
        return NetworkUtils.getMovieReviews(MOVIE_DB_API_BASE_URL , movieId, API_KEY);
    }

    public static Call<VideoList> getMovieVideos(String movieId){
        return NetworkUtils.getMovieVideos(MOVIE_DB_API_BASE_URL , movieId, API_KEY);
    }
}
