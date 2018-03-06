package com.aenima.android.popularmovies.core.network;

import android.net.Uri;
import android.util.Log;

import com.aenima.android.popularmovies.core.model.Movie;
import com.aenima.android.popularmovies.core.model.MovieList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marina on 20/02/2018.
 */

public class NetworkUtils {

    /**
     * Parse the given base url adding parameters
     * @param baseUrl
     * @param parameters additional parameters to create, for instance, a query
     * @return a valid Uri, null otherwise
     */
    public static URL buildUri(String baseUrl, String sortBy, LinkedHashMap<String,String> parameters){
        URL validUrl = null;
        try {
            Uri.Builder buildUri = Uri.parse(baseUrl).buildUpon().appendPath(sortBy);
            for (String queryParameter :
                    parameters.keySet()) {
                buildUri.appendQueryParameter(queryParameter, parameters.get(queryParameter));
            }
            validUrl = new URL(buildUri.build().toString());
        }catch (MalformedURLException ex){
            Log.e(NetworkUtils.class.getName(), ex.getLocalizedMessage());
        }

        return validUrl;
    }

    public static URL buildPosterUri(String baseUrl, String posterSize, String posterPath){
        URL validUrl = null;
        try {
            Uri.Builder buildUri = Uri.parse(baseUrl).buildUpon().appendPath(posterSize).appendPath(posterPath);

            validUrl = new URL(buildUri.build().toString());
        }catch (MalformedURLException ex){
            Log.e(NetworkUtils.class.getName(), ex.getLocalizedMessage());
        }


        return validUrl;
    }

    public static String getHttpResponse(URL url) throws IOException {
        String httpResponse = null;
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            if(scanner.hasNext())
                httpResponse = scanner.next();

        } finally {
            httpURLConnection.disconnect();
        }
        return httpResponse;
    }

    public static Call<MovieList> getMovies(String baseUrl, String sortBy, String apiKey) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();

        MovieService service = retrofit.create(MovieService.class);
        Call<MovieList> callBack = service.listMovie(sortBy, apiKey);
        return callBack;
    }


}
