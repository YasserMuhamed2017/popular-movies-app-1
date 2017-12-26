package com.elshadsm.popularmovies.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.elshadsm.popularmovies.R;
import com.elshadsm.popularmovies.activities.MovieDetailsActivity;
import com.elshadsm.popularmovies.adapters.MoviePostersAdapter;
import com.elshadsm.popularmovies.models.Movie;
import com.elshadsm.popularmovies.services.MovieQueryTask;

import static com.elshadsm.popularmovies.models.Constants.INTENT_EXTRA_NAME_MOVIE_DETAILS;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_posters, container, false);
        applyConfiguration(rootView);
        registerEventHandlers();
        setMoviePosters(FILTER_TYPE_POPULAR); // The default type.
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                setMoviePosters(FILTER_TYPE_POPULAR);
                return true;
            case R.id.action_top_rated:
                setMoviePosters(FILTER_TYPE_TOP_RATED);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void applyConfiguration(View rootView) {
        if (getActivity() == null) {
            return;
        }
        moviePostersAdapter = new MoviePostersAdapter(getActivity());
        gridView = rootView.findViewById(R.id.movie_posters_grid);
        gridView.setAdapter(moviePostersAdapter);
    }

    private void registerEventHandlers() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                startMovieDetailsActivity(view, index);
            }
        });
    }

    private void startMovieDetailsActivity(View view, int index) {
        Context context = view.getContext();
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        Movie selectedMovie = moviePostersAdapter.getItem(index);
        intent.putExtra(INTENT_EXTRA_NAME_MOVIE_DETAILS, selectedMovie);
        context.startActivity(intent);
    }

    private void setMoviePosters(String type) {
        MovieQueryTask movieQueryTask = new MovieQueryTask(moviePostersAdapter);
        movieQueryTask.execute(type);
    }

}
