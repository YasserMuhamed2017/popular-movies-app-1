package com.elshadsm.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.elshadsm.popularmovies.R;
import com.elshadsm.popularmovies.adapters.MoviePostersAdapter;
import com.elshadsm.popularmovies.services.MovieQueryTask;

/**
 * A fragment containing the list view of Android versions.
 */
public class MoviePostersFragment extends Fragment {

    private MoviePostersAdapter moviePostersAdapter;
    private GridView gridView;

    private static final String FILTER_TYPE_POPULAR = "popular";
    private static final String FILTER_TYPE_TOP_RATED = "top_rated";

    public MoviePostersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_posters, container, false);
        moviePostersAdapter = new MoviePostersAdapter(getActivity());
        gridView = rootView.findViewById(R.id.movie_posters_grid);
        setMoviePosters(FILTER_TYPE_POPULAR);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedMenuItem = item.getItemId();
        if (selectedMenuItem == R.id.action_popular) {
            setMoviePosters(FILTER_TYPE_POPULAR);
            return true;
        }
        if (selectedMenuItem == R.id.action_top_rated) {
            setMoviePosters(FILTER_TYPE_TOP_RATED);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setMoviePosters(String type) {
        MovieQueryTask movieQueryTask = new MovieQueryTask(moviePostersAdapter);
        gridView.setAdapter(moviePostersAdapter);
        movieQueryTask.execute(type);
    }

}
