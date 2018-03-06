package com.aenima.android.popularmovies.core.model;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

/**
 * Created by aenim on 06/03/2018.
 */

class Review {

    private static final String ID_JSON_HEADER = "id";
    private static final String AUTHOR_JSON_HEADER = "author";
    private static final String CONTENT_JSON_HEADER = "content";
    private static final String URL_JSON_HEADER = "url";

    @SerializedName(ID_JSON_HEADER)
    private long id;

    @SerializedName(AUTHOR_JSON_HEADER)
    public String author;

    @SerializedName(CONTENT_JSON_HEADER)
    public String content;

    @SerializedName(URL_JSON_HEADER)
    public String url;

}
