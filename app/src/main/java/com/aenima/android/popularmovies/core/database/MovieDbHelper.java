package com.aenima.android.popularmovies.core.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aenima.android.popularmovies.core.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marina on 14/03/2018.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "movies.db";
    private static final int DB_VERSION = 1;

    public MovieDbHelper (Context context){
        //TODO change swap cursor factory
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE_SQL_STATEMENT = "CREATE TABLE "+
                        MovieContract.MovieEntry.TABLE_NAME + " ( " +
                        MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieContract.MovieEntry.TABLE_COLUMN_TITLE + " VARCHAR NOT NULL, " +
                        MovieContract.MovieEntry.TABLE_COLUMN_POSTER_PATH + " VARCHAR NOT NULL, " +
                        MovieContract.MovieEntry.TABLE_COLUMN_ORIGINAL_TITLE + " VARCHAR, " +
                        MovieContract.MovieEntry.TABLE_COLUMN_ORIGINAL_LANGUAGE + " VARCHAR, " +
                        MovieContract.MovieEntry.TABLE_COLUMN_BACKDROP_PATH + " VARCHAR, " +
                        MovieContract.MovieEntry.TABLE_COLUMN_OVERVIEW + " TEXT, " +
                        MovieContract.MovieEntry.TABLE_COLUMN_RELEASE_DATE + " VARCHAR, " +
                        MovieContract.MovieEntry.TABLE_COLUMN_VOTE_COUNT + " INTEGER, " +
                        MovieContract.MovieEntry.TABLE_COLUMN_HAS_VIDEO + " INTEGER, " +
                        MovieContract.MovieEntry.TABLE_COLUMN_IS_ADULT + " INTEGER, " +
                        MovieContract.MovieEntry.TABLE_COLUMN_VOTE_AVG + " FLOAT, " +
                        MovieContract.MovieEntry.TABLE_COLUMN_POPULARITY + "FLOAT, "+
                        MovieContract.MovieEntry.TABLE_COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP "+

                " ) ;";
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long addFavouriteMovie(Movie movie, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_TITLE, movie.getTitle());
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_VOTE_COUNT, movie.getVoteCount());
        contentValues.put(MovieContract.MovieEntry._ID, movie.getId());
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_HAS_VIDEO, movie.hasVideo()? 1 : 0);
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_VOTE_AVG, movie.getVoteAvg());
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_POPULARITY, movie.getPopularity());
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_POSTER_PATH, movie.getPosterPath());
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_ORIGINAL_LANGUAGE, movie.getOriginalTitle());
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_ORIGINAL_TITLE, movie.getTitle());
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_BACKDROP_PATH, movie.getBackDropPath());
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_IS_ADULT, movie.isAdult() ? 1 : 0);
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(MovieContract.MovieEntry.TABLE_COLUMN_RELEASE_DATE, movie.getReleaseDate());
        return sqLiteDatabase.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
    }

    public void removeFavouriteMovie(Movie movie, SQLiteDatabase sqLiteDatabase){
        final String deleteQuery = "DELETE FROM " + MovieContract.MovieEntry.TABLE_NAME +
                " WHERE " + MovieContract.MovieEntry._ID +
                " = " + movie.getIdString();
        sqLiteDatabase.execSQL(deleteQuery);
    }

    public List<Movie> getFavouriteMovie(SQLiteDatabase sqLiteDatabase){
        List<Movie> favourites = new ArrayList<>();
        Cursor mCursor = sqLiteDatabase.query(MovieContract.MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MovieContract.MovieEntry.TABLE_COLUMN_TIMESTAMP
                );
        mCursor.moveToFirst();
        for(int i = 0; i < mCursor.getCount(); i++){
            mCursor.moveToPosition(i);
            String movieTitle =             mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_TITLE));
            int movieVoteCount =            mCursor.getInt(mCursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_VOTE_COUNT));
            long movieId =                  mCursor.getLong(mCursor.getColumnIndex(MovieContract.MovieEntry._ID));
            boolean movieHasVideo =         mCursor.getInt(mCursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_HAS_VIDEO)) == 1 ? true : false;
            float movieVoteAvg =            mCursor.getFloat(mCursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_VOTE_AVG));
            float moviePopularity =         mCursor.getFloat(mCursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_POPULARITY));
            String moviePosterPath =        mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_POSTER_PATH));
            String movieOriginalLanguage =  mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_ORIGINAL_LANGUAGE));
            String movieOriginalTitle =     mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_ORIGINAL_TITLE));
            String movieBackdropPath =      mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_BACKDROP_PATH));
            boolean movieIsAdult =          mCursor.getInt(mCursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_IS_ADULT)) == 1 ? true : false;
            String movieOverview =          mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_OVERVIEW));
            String movieReleaseDate =       mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.TABLE_COLUMN_RELEASE_DATE));
            Movie movie = new Movie(movieTitle, movieVoteCount, movieId, movieHasVideo, movieVoteAvg, moviePopularity, moviePosterPath, movieOriginalLanguage, movieOriginalTitle, movieBackdropPath, movieIsAdult, movieOverview, movieReleaseDate);
            favourites.add(movie);
        }
        return favourites;
    }
}
