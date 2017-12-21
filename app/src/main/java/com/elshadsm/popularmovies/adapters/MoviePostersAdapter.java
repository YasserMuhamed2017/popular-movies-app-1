package com.elshadsm.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.elshadsm.popularmovies.R;
import com.elshadsm.popularmovies.models.Movie;

import java.util.List;

/**
 * Created by Elshad Seyidmammadov on 20.12.2017.
 */

public class MoviePostersAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = MoviePostersAdapter.class.getSimpleName();

    public MoviePostersAdapter(@NonNull Context context, List<Movie> movieList) {
        super(context, 0, movieList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.movie_poster_item, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.movie_poster);
        imageView.setImageResource(movie.getPoster());
        return convertView;
    }
}
