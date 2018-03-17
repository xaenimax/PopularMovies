package com.aenima.android.popularmovies.core.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by marina on 14/03/2018.
 */

public final class MovieContract {

    public static final String AUTHORITY = "com.aenima.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    private MovieContract(){}

    public static class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String TABLE_NAME                       = "movie";
        public static final String TABLE_COLUMN_TITLE               = "title";
        public static final String TABLE_COLUMN_POSTER_PATH         = "poster_path";
        public static final String TABLE_COLUMN_ORIGINAL_TITLE      = "original_title";
        public static final String TABLE_COLUMN_ORIGINAL_LANGUAGE   = "original_language";
        public static final String TABLE_COLUMN_BACKDROP_PATH       = "backdrop_path";
        public static final String TABLE_COLUMN_OVERVIEW            = "overview";
        public static final String TABLE_COLUMN_RELEASE_DATE        = "release_date";
        public static final String TABLE_COLUMN_VOTE_COUNT          = "vote_count";
        public static final String TABLE_COLUMN_HAS_VIDEO           = "has_video";
        public static final String TABLE_COLUMN_IS_ADULT            = "is_adult";
        public static final String TABLE_COLUMN_VOTE_AVG            = "vote_avg";
        public static final String TABLE_COLUMN_POPULARITY          = "popularity";
        public static final String TABLE_COLUMN_TIMESTAMP           = "timestamp";

        /*
        this.movieTitle = movieTitle;
        this.moviePosterPath = moviePosterPath;
        this.movieOriginalLanguage = movieOriginalLanguage;
        this.movieOriginalTitle = movieOriginalTitle;
        this.movieBackdropPath = movieBackdropPath;
        this.movieOverview = movieOverview;
        this.movieReleaseDate = movieReleaseDate;
        this.movieVoteCount = movieVoteCount;
        this.movieId = movieId;
        this.movieHasVideo = movieHasVideo;
        this.movieIsAdult = movieIsAdult;
        this.movieVoteAvg = movieVoteAvg;
        this.moviePopularity = moviePopularity;
         */
    }
}
