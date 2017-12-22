package com.elshadsm.popularmovies.services;

import android.os.AsyncTask;
import android.util.Log;

import com.elshadsm.popularmovies.adapters.MoviePostersAdapter;
import com.elshadsm.popularmovies.models.Movie;
import com.elshadsm.popularmovies.models.MovieDBConfig;
import com.elshadsm.popularmovies.utils.JSONUtils;
import com.elshadsm.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

/**
 * Created by Elshad Seyidmammadov on 22.12.2017.
 */

public class MovieQueryTask extends AsyncTask<String, Void, List<Movie>> {

    private static final String LOG_TAG = MovieQueryTask.class.getSimpleName();

    private MoviePostersAdapter moviePostersAdapter;

    public MovieQueryTask(MoviePostersAdapter moviePostersAdapter) {
        this.moviePostersAdapter = moviePostersAdapter;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        URL moviesRequestUrl = NetworkUtils.buildUrl(MovieDBConfig.API_KEY, params[0]);
        try {
            String jsonMoviesResponse = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);
            return JSONUtils.parseJson(jsonMoviesResponse);
        } catch (Exception e) {
            Log.e(LOG_TAG, "filter type: " + params[0], e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movieList) {
        if (movieList != null) {
            moviePostersAdapter.setMovieList(movieList);
        }
    }
}
