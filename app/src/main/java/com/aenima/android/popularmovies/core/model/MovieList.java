package com.aenima.android.popularmovies.core.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aenim on 04/03/2018.
 */

public class MovieList {

    private static final String RESULTS_JSON_HEADER = "results";

    @SerializedName(RESULTS_JSON_HEADER)
    public List<Movie> movieList;
}
