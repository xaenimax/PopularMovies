package com.aenima.android.popularmovies.core.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aenim on 06/03/2018.
 */

public class Video {

    //https://www.youtube.com/watch?v=CglpMulzqn4

    private static final String ID_JSON_HEADER = "id";
    private static final String NAME_JSON_HEADER = "name";
    private static final String SITE_JSON_HEADER = "site";
    private static final String SIZE_JSON_HEADER = "size";
    private static final String TYPE_JSON_HEADER = "type";
    private static final String KEY_JSON_HEADER = "key";

    @SerializedName(ID_JSON_HEADER)
    private String id;

    @SerializedName(NAME_JSON_HEADER)
    public String name;

    @SerializedName(SIZE_JSON_HEADER)
    public String size;

    @SerializedName(SITE_JSON_HEADER)
    public String site;

    @SerializedName(TYPE_JSON_HEADER)
    public String type;

    @SerializedName(KEY_JSON_HEADER)
    public String key;

    public String getYoutubeUrl() {
        return String.format("https://img.youtube.com/vi/%s/0.jpg", this.key);
    }

    public String getYoutubeVideoUrl() {
        return String.format("https://www.youtube.com/watch?v=%s", this.key);
    }
}
