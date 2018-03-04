package com.aenima.android.popularmovies.core.network;

import com.aenima.android.popularmovies.core.model.Movie;
import com.aenima.android.popularmovies.core.model.MovieList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by aenim on 04/03/2018.
 */

public interface MovieService {

    @GET("{sortBy}")
    Call<MovieList> listMovie(@Path("sortBy") String user, @Query("api_key") String apiKey);
}
