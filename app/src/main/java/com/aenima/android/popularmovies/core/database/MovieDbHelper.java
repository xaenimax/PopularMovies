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

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
