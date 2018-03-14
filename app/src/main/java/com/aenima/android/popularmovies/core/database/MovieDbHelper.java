package com.aenima.android.popularmovies.core.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                        MovieContract.MovieEntry.TABLE_COLUMN_NAME + " VARCHAR NOT NULL, " +
                        MovieContract.MovieEntry.TABLE_COLUMN_MOVIE_ID + " INTEGER NOT NULL " +
                " ) ;";
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
