package com.elshadsm.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.elshadsm.popularmovies.R;
import com.elshadsm.popularmovies.activities.MovieDetailsActivity;
import com.elshadsm.popularmovies.fragments.MoviePostersFragment;
import com.elshadsm.popularmovies.models.Movie;
import com.elshadsm.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.elshadsm.popularmovies.models.Constants.INTENT_EXTRA_NAME_MOVIE_DETAILS;

/**
 * Created by Elshad Seyidmammadov on 20.12.2017.
 */

public class MoviePostersAdapter extends RecyclerView.Adapter<MoviePostersAdapter.MoviePostersAdapterViewHolder> {

    private List<Movie> movieList = new ArrayList<>();

    private MoviePostersFragment moviePostersFragment;

    public MoviePostersAdapter(MoviePostersFragment moviePostersFragment) {
        this.moviePostersFragment = moviePostersFragment;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
        moviePostersFragment.restoreViewState();
    }

    @Override
    public MoviePostersAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_poster_item, parent, false);
        return new MoviePostersAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviePostersAdapterViewHolder holder, int position) {
        Picasso.with(holder.poster.getContext())
                .load(NetworkUtils.buildPosterUrl(movieList.get(position).getPosterPath()))
                .placeholder(R.drawable.movie_poster_placeholder)
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public class MoviePostersAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView poster;

        MoviePostersAdapterViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            poster = view.findViewById(R.id.movie_poster);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            Movie selectedMovie = movieList.get(getAdapterPosition());
            intent.putExtra(INTENT_EXTRA_NAME_MOVIE_DETAILS, selectedMovie);
            context.startActivity(intent);
        }
    }
}
