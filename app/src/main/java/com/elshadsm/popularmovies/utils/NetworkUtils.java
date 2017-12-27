package com.elshadsm.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

import com.elshadsm.popularmovies.models.MovieDBConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Elshad Seyidmammadov on 21.12.2017.
 */

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIE_DB_API_SCHEME = "http";
    private static final String MOVIE_DB_API_AUTHORITY = "api.themoviedb.org";
    private static final String MOVIE_DB_API_FIRST_PATH = "3";
    private static final String MOVIE_DB_API_SECOND_PATH = "movie";
    private static final String MOVIE_DB_API_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String MOVIE_DB_API_POSTER_SIZE = "w500";
    private static final String MOVIE_DB_API_QUERY_PARAMETER_API_KEY_NAME = "api_key";
    // TODO: Use your own key instead of 'MovieDBConfig.API_KEY' in order to use movie DB API.
    private static final String MOVIE_DB_API_QUERY_PARAMETER_API_KEY_VALUE = MovieDBConfig.API_KEY;

    public static URL buildUrl(String filterType) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(MOVIE_DB_API_SCHEME)
                .authority(MOVIE_DB_API_AUTHORITY)
                .appendPath(MOVIE_DB_API_FIRST_PATH)
                .appendPath(MOVIE_DB_API_SECOND_PATH)
                .appendPath(filterType)
                .appendQueryParameter(MOVIE_DB_API_QUERY_PARAMETER_API_KEY_NAME, MOVIE_DB_API_QUERY_PARAMETER_API_KEY_VALUE);
        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "filterType: " + filterType, e);
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL moviesRequestUrl) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) moviesRequestUrl.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String buildPosterUrl(String posterPath) {
        return MOVIE_DB_API_POSTER_BASE_URL + MOVIE_DB_API_POSTER_SIZE + "/" + posterPath;
    }
}
