package com.elshadsm.popularmovies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.elshadsm.popularmovies.R;
import com.elshadsm.popularmovies.adapters.MoviePostersAdapter;
import com.elshadsm.popularmovies.models.Movie;

import java.util.Arrays;

/**
 * A fragment containing the list view of Android versions.
 */
public class MoviePostersFragment extends Fragment {

    private final Movie[] movieArray = {
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake),
            new Movie(R.drawable.cupcake)
    };

    public MoviePostersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_posters, container, false);
        MoviePostersAdapter moviePostersAdapter = new MoviePostersAdapter(getActivity(), Arrays.asList(movieArray));
        GridView gridView = rootView.findViewById(R.id.movie_posters_grid);
        gridView.setAdapter(moviePostersAdapter);
        return rootView;
    }

}
