package com.aenima.android.popularmovies.core.model;

import android.app.LoaderManager;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.aenima.android.popularmovies.core.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marina on 22/02/2018.
 */

public class Movie implements Parcelable{

    public static final String RESULTS_JSON_HEADER = "results";
    public static final String VOTE_COUNT_JSON_HEADER = "vote_count";
    public static final String ID_JSON_HEADER = "id";
    public static final String VIDEO_JSON_HEADER = "video";
    public static final String VOTE_AVERAGE_JSON_HEADER = "vote_average";
    public static final String TITLE_JSON_HEADER = "title";
    public static final String POPULARITY_JSON_HEADER = "popularity";
    public static final String POSTER_PATH_JSON_HEADER = "poster_path";
    public static final String ORIGINAL_LANGUAGE_JSON_HEADER = "original_language";
    public static final String ORIGINAL_TITLE_JSON_HEADER = "original_title";
    public static final String BACKDROP_PATH_JSON_HEADER = "backdrop_path";
    public static final String ADULT_JSON_HEADER = "adult";
    public static final String OVERVIEW_JSON_HEADER = "overview";
    public static final String RELEASE_DATE_JSON_HEADER = "release_date";

    private static String MOVIE_DB_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static String POSTER_SIZE = "w185";

    String movieTitle, moviePosterPath, movieOriginalLanguage, movieOriginalTitle, movieBackdropPath, movieOverview, movieReleaseDate;
    int movieVoteCount;
    long movieId;
    boolean movieHasVideo, movieIsAdult;
    double movieVoteAvg, moviePopularity;


    public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    public Movie(String movieTitle, int movieVoteCount, long movieId, boolean movieHasVideo, double movieVoteAvg, double moviePopularity, String moviePosterPath, String movieOriginalLanguage, String movieOriginalTitle, String movieBackdropPath, boolean movieIsAdult, String movieOverview, String movieReleaseDate) {
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
    }


    public Movie(Parcel parcel) {
        this.movieTitle = parcel.readString();
        this.moviePosterPath = parcel.readString();
        this.movieOriginalLanguage = parcel.readString();
        this.movieBackdropPath = parcel.readString();
        this.movieOverview = parcel.readString();
        this.movieReleaseDate = parcel.readString();
        this.movieVoteCount = parcel.readInt();
        this.movieId = parcel.readLong();
        this.movieHasVideo = parcel.readInt() == 1 ? true : false;
        this.movieIsAdult = parcel.readInt() == 1 ? true : false;
        this.movieVoteAvg = parcel.readDouble();
        this.moviePopularity = parcel.readDouble();
    }

    public String getPosterImagePath(){

        try {
            Log.d(this.getClass().getName(),URLDecoder.decode(NetworkUtils.buildPosterUri(MOVIE_DB_POSTER_BASE_URL, POSTER_SIZE, this.moviePosterPath).toString(),"UTF-8"));
            return URLDecoder.decode(NetworkUtils.buildPosterUri(MOVIE_DB_POSTER_BASE_URL, POSTER_SIZE, this.moviePosterPath).toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Movie> getMovieList(String jsonString){
        List<Movie> movieList = new ArrayList<>();
        try {
            JSONObject mainJsonObject = new JSONObject(jsonString);
            JSONArray movieJsonArray = mainJsonObject.optJSONArray(Movie.RESULTS_JSON_HEADER);
            for (int i = 0; i < movieJsonArray.length(); i ++){
                JSONObject movieJsonObject = movieJsonArray.optJSONObject(i);
                if(movieJsonObject != null) {
                    String movieTitle = movieJsonObject.optString(Movie.TITLE_JSON_HEADER);
                    int movieVoteCount = movieJsonObject.optInt(Movie.VOTE_COUNT_JSON_HEADER);
                    long movieId = movieJsonObject.optLong(Movie.ID_JSON_HEADER);
                    boolean movieHasVideo = movieJsonObject.optBoolean(Movie.VIDEO_JSON_HEADER);
                    double movieVoteAvg = movieJsonObject.optDouble(Movie.VOTE_AVERAGE_JSON_HEADER);
                    double moviePopularity = movieJsonObject.optDouble(Movie.POPULARITY_JSON_HEADER);
                    String moviePosterPath = movieJsonObject.optString(Movie.POSTER_PATH_JSON_HEADER);
                    String movieOriginalLanguage = movieJsonObject.optString(Movie.ORIGINAL_LANGUAGE_JSON_HEADER);
                    String movieOriginalTitle = movieJsonObject.optString(Movie.ORIGINAL_TITLE_JSON_HEADER);
                    String movieBackdropPath = movieJsonObject.optString(Movie.BACKDROP_PATH_JSON_HEADER);
                    boolean movieIsAdult = movieJsonObject.optBoolean(Movie.ADULT_JSON_HEADER);
                    String movieOverview = movieJsonObject.optString(Movie.OVERVIEW_JSON_HEADER);
                    String movieReleaseDate = movieJsonObject.optString(Movie.RELEASE_DATE_JSON_HEADER);

                    movieList.add(new Movie(movieTitle, movieVoteCount, movieId, movieHasVideo, movieVoteAvg, moviePopularity, moviePosterPath,
                                            movieOriginalLanguage, movieOriginalTitle, movieBackdropPath, movieIsAdult, movieOverview, movieReleaseDate));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.movieTitle);
        parcel.writeString(this.moviePosterPath);
        parcel.writeString(this.movieOriginalLanguage);
        parcel.writeString(this.movieBackdropPath);
        parcel.writeString(this.movieOverview);
        parcel.writeString(this.movieReleaseDate);
        parcel.writeInt(this.movieVoteCount);
        parcel.writeLong(this.movieId);
        parcel.writeInt(this.movieHasVideo ?  1 : 0);
        parcel.writeInt(this.movieIsAdult ? 1 : 0);
        parcel.writeDouble(this.movieVoteAvg);
        parcel.writeDouble(this.moviePopularity);
    }


    public String getTitle() {
        return movieTitle;
    }

    public String getOriginalTitle() {
        return movieOriginalTitle;
    }

    public String getOverview() {
        return movieOverview;
    }
}
