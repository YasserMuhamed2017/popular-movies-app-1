package com.elshadsm.popularmovies.utils;

import android.util.Log;

import com.elshadsm.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elshad Seyidmammadov on 21.12.2017.
 */

public class JSONUtils {

    private static final String LOG_TAG = JSONUtils.class.getSimpleName();

    private static final String ERROR_STATUS_CODE = "status_code";
    private static final String ERROR_STATUS_MESSAGE = "status_message";
    private static final String MOVIES = "results";
    private static final String ID_KEY = "id";
    private static final String POSTER_PATH_KEY = "poster_path";
    private static final String OVERVIEW_KEY = "overview";
    private static final String RELEASE_DATE_KEY = "release_date";
    private static final String TITLE_KEY = "title";
    private static final String VOTE_AVERAGE_KEY = "vote_average";

    public static List<Movie> parseJson(String json) throws JSONException {
        JSONObject responseJson = new JSONObject(json);
        if (responseJson.has(ERROR_STATUS_CODE)) {
            int errorCode = responseJson.getInt(ERROR_STATUS_CODE);
            String errorMesage = responseJson.getString(ERROR_STATUS_MESSAGE);
            Log.e(LOG_TAG, errorMesage + " - error code: " + errorCode);
        }
        JSONArray moviesArray = responseJson.getJSONArray(MOVIES);
        return parseMovieList(moviesArray);
    }

    private static List<Movie> parseMovieList(JSONArray moviesArray) throws JSONException {
        List<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movie = moviesArray.getJSONObject(i);
            Movie currentMovie = parseMovie(movie);
            movieList.add(currentMovie);
        }
        return movieList;
    }

    private static Movie parseMovie(JSONObject movie) throws JSONException {
        Movie currentMovie = new Movie();
        currentMovie.setId(movie.getInt(ID_KEY));
        currentMovie.setPosterPath(movie.getString(POSTER_PATH_KEY));
        currentMovie.setOverview(movie.getString(OVERVIEW_KEY));
        currentMovie.setReleaseDate(movie.getString(RELEASE_DATE_KEY));
        currentMovie.setTitle(movie.getString(TITLE_KEY));
        currentMovie.setVoteAverage(movie.getDouble(VOTE_AVERAGE_KEY));
        return currentMovie;
    }
}
