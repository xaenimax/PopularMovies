package com.aenima.android.popularmovies.core.database;

import android.provider.BaseColumns;

/**
 * Created by marina on 14/03/2018.
 */

public final class MovieContract {

    private MovieContract(){}

    public static class MovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "movie";
        public static final String TABLE_COLUMN_NAME = "name";
        public static final String TABLE_COLUMN_MOVIE_ID = "movie_id";

    }
}
