package com.elshadsm.popularmovies.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.elshadsm.popularmovies.R;
import com.elshadsm.popularmovies.activities.MovieDetailsActivity;
import com.elshadsm.popularmovies.adapters.FavoriteMoviePostersAdapter;
import com.elshadsm.popularmovies.adapters.MoviePostersAdapter;
import com.elshadsm.popularmovies.models.Movie;
import com.elshadsm.popularmovies.services.FavoriteMoviesCursorLoader;
import com.elshadsm.popularmovies.services.MoviesQueryLoader;

import java.util.List;

import static com.elshadsm.popularmovies.models.Constants.INTENT_EXTRA_NAME_MOVIE_DETAILS;
import static com.elshadsm.popularmovies.models.Constants.MOVIES_QUERY_LOADER_FILTER_TYPE_EXTRA;

/**
 * A fragment containing the list view of Android versions.
 */
public class MoviePostersFragment extends Fragment {

    private MoviePostersAdapter moviePostersAdapter;
    private FavoriteMoviePostersAdapter favoriteMoviePostersAdapter;
    private GridView gridView;

    public static final int MOVIES_QUERY_LOADER_ID = 11121990;
    public static final int FAVORITE_MOVIES_LOADER_ID = 12121994;
    private MoviesQueryLoader moviesQueryLoader;
    private FavoriteMoviesCursorLoader favoriteMoviesCursorLoader;

    private static final String FILTER_TYPE_POPULAR = "popular";
    private static final String FILTER_TYPE_TOP_RATED = "top_rated";

    private static final String SELECTED_ACTION_ID_KEY = "selected_action_id";
    private static int selectedActionId = R.id.action_popular;

    public MoviePostersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SELECTED_ACTION_ID_KEY, selectedActionId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_posters, container, false);
        applyConfiguration(rootView);
        registerEventHandlers();
        if (savedInstanceState == null) {
            applyPopularConfiguration();
        } else {
            applyNewConfiguration(savedInstanceState.getInt(SELECTED_ACTION_ID_KEY, R.id.action_popular));
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return applyNewConfiguration(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    private boolean applyNewConfiguration(int id) {
        switch (id) {
            case R.id.action_popular:
                applyPopularConfiguration();
                return true;
            case R.id.action_top_rated:
                applyTopRatedConfiguration();
                return true;
            case R.id.action_favorites:
                applyFavoritesConfiguration();
                return true;
        }
        return false;
    }

    private void applyConfiguration(View rootView) {
        Context context = getContext();
        FragmentActivity activity = getActivity();
        if (activity == null || context == null) {
            return;
        }
        gridView = rootView.findViewById(R.id.movie_posters_grid);
        TextView emptyMessageView = rootView.findViewById(R.id.empty_message_view);
        gridView.setEmptyView(emptyMessageView);
        moviePostersAdapter = new MoviePostersAdapter(activity);
        moviesQueryLoader = new MoviesQueryLoader(context, moviePostersAdapter);
        favoriteMoviePostersAdapter = new FavoriteMoviePostersAdapter(context, null, false);
        favoriteMoviesCursorLoader = new FavoriteMoviesCursorLoader(context, favoriteMoviePostersAdapter);
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
        Movie selectedMovie = (Movie) gridView.getAdapter().getItem(index);
        intent.putExtra(INTENT_EXTRA_NAME_MOVIE_DETAILS, selectedMovie);
        context.startActivity(intent);
    }

    private void applyTopRatedConfiguration() {
        gridView.setAdapter(moviePostersAdapter);
        selectedActionId = R.id.action_top_rated;
        setMoviePosters(FILTER_TYPE_TOP_RATED);
    }

    private void applyPopularConfiguration() {
        gridView.setAdapter(moviePostersAdapter);
        selectedActionId = R.id.action_popular;
        setMoviePosters(FILTER_TYPE_POPULAR);
    }

    private void applyFavoritesConfiguration() {
        gridView.setAdapter(favoriteMoviePostersAdapter);
        selectedActionId = R.id.action_favorites;
        assert getActivity() != null;
        getActivity().getSupportLoaderManager()
                .initLoader(FAVORITE_MOVIES_LOADER_ID, null, favoriteMoviesCursorLoader);
    }

    private void setMoviePosters(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(MOVIES_QUERY_LOADER_FILTER_TYPE_EXTRA, type);
        assert getActivity() != null;
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<List<Movie>> loader = loaderManager.getLoader(MOVIES_QUERY_LOADER_ID);
        if (loader == null) {
            loaderManager.initLoader(MOVIES_QUERY_LOADER_ID, bundle, moviesQueryLoader);
        } else {
            loaderManager.restartLoader(MOVIES_QUERY_LOADER_ID, bundle, moviesQueryLoader);
        }
    }

}
