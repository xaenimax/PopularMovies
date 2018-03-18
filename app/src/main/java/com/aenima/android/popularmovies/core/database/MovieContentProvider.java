package com.aenima.android.popularmovies.core.database;

import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by aenim on 17/03/2018.
 */

public class MovieContentProvider extends ContentProvider{
    public static final int MOVIE = 100;
    public static final int MOVIE_WITH_ID = 101;
    public static UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE, MOVIE);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE + "/#", MOVIE_WITH_ID);
        return uriMatcher;
    }
    private MovieDbHelper movieDbHelper;
    @Override
    public boolean onCreate() {
        Context context = getContext();
        movieDbHelper = new MovieDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String selection, @Nullable String[] strings1, @Nullable String s1) {
        final SQLiteDatabase sqLiteDatabase = movieDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor returnCursor = null;
        switch (match) {
            case MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String selectionString = MovieContract.MovieEntry._ID + "=?";
                String[] selecionArgs = new String[]{id};
                returnCursor = sqLiteDatabase.query(MovieContract.MovieEntry.TABLE_NAME,
                        null, selectionString, selecionArgs,
                        null,
                        null,
                        s1);
                break;
            case MOVIE:
                returnCursor = sqLiteDatabase.query(MovieContract.MovieEntry.TABLE_NAME,
                        strings,
                        selection,
                        strings1,
                        null,
                        null,
                        s1);
                break;
            default:
                throw new UnsupportedOperationException("Unknown operation: " + uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase sqLiteDatabase = movieDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri = null;
        switch (match){
            case MOVIE_WITH_ID:
            case MOVIE:
                long id = sqLiteDatabase.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
                if (id > 0){
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                }else {
                    throw new SQLiteException("Error inserting new row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown operation: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase sqLiteDatabase = movieDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int deletedId = 0;
        switch (match) {
            case MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String selectionString = MovieContract.MovieEntry._ID + "=?";
                String[] selecionArgs = new String[]{id};
                deletedId = sqLiteDatabase.delete(MovieContract.MovieEntry.TABLE_NAME,
                        selectionString, selecionArgs);
                break;
            case MOVIE:
                deletedId = sqLiteDatabase.delete(MovieContract.MovieEntry.TABLE_NAME,
                        s,
                        strings);
                break;
            default:
                throw new UnsupportedOperationException("Unknown operation: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        //there is no update operation permitted
        return 0;
    }
}
