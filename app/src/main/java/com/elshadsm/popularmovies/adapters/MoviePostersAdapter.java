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
import com.elshadsm.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Elshad Seyidmammadov on 20.12.2017.
 */

public class MoviePostersAdapter extends ArrayAdapter<Movie> {

    public MoviePostersAdapter(@NonNull Context context) {
        super(context, 0);
    }

    public void setMovieList(List<Movie> movieList) {
        this.clear();
        this.addAll(movieList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_poster_item, parent, false);
        }
        applyViewConfiguration(position, convertView);
        return convertView;
    }

    private void applyViewConfiguration(int position, View convertView) {
        Movie movie = getItem(position);
        ImageView imageView = convertView.findViewById(R.id.movie_poster);
        assert movie != null;
        Picasso.with(getContext())
                .load(NetworkUtils.buildPosterUrl(movie.getPosterPath()))
                .placeholder(R.drawable.movie_poster_placeholder)
                .error(R.drawable.movie_poster_error)
                .into(imageView);
    }
    
}
