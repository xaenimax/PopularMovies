package com.aenima.android.popularmovies.core.network;

import com.aenima.android.popularmovies.core.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by marina on 01/03/2018.
 */

public interface MovieService {
    @GET("movies/{id}/videos")
    Call<List<Movie>> listRepos(@Path("id") String movieId);

    @GET("{sort}")
    Call<List<Movie>> getMovieList(@Path("sort") String sort, @Query("api_key") String apiKey);
}
