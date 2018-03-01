package com.aenima.android.popularmovies.core.network;

import com.aenima.android.popularmovies.core.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by marina on 01/03/2018.
 */

public interface MovieService {
    @GET("movies/{id}/videos")
    Call<List<Movie>> listRepos(@Path("id") String movieId);
}
