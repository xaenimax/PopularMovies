package com.aenima.android.popularmovies.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aenim on 06/03/2018.
 */

public class ReviewList{
    private static final String RESULTS_JSON_HEADER = "results";

    @SerializedName(RESULTS_JSON_HEADER)
    public List<Review> reviews;

}
