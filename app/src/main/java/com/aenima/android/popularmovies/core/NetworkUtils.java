package com.aenima.android.popularmovies.core;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Scanner;

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
    public static URL buildUri(String baseUrl, LinkedHashMap<String,String> parameters){
        URL validUrl = null;
        try {
            Uri.Builder buildUri = Uri.parse(baseUrl).buildUpon();
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
}
