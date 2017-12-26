package com.elshadsm.popularmovies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.elshadsm.popularmovies.R;
import com.elshadsm.popularmovies.models.Constants;
import com.elshadsm.popularmovies.models.Movie;
import com.elshadsm.popularmovies.utils.DataUtil;
import com.elshadsm.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView title, releaseDate, voteAverage, overview;
    private ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initWidgets();
        applyWidgetsValue();
    }

    private void initWidgets() {
        title = findViewById(R.id.movie_details_title);
        releaseDate = findViewById(R.id.movie_details_release_date);
        voteAverage = findViewById(R.id.movie_details_vote_average);
        overview = findViewById(R.id.movie_details_overview);
        poster = findViewById(R.id.movie_details_poster);
    }

    private void applyWidgetsValue() {
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.INTENT_EXTRA_NAME_MOVIE_DETAILS)) {
            Bundle data = intent.getExtras();
            assert data != null;
            final Movie movie = data.getParcelable(Constants.INTENT_EXTRA_NAME_MOVIE_DETAILS);
            setMovieDetails(movie);
        }
    }

    private void setMovieDetails(Movie movie) {
        setMoviePoster(movie);
        title.setText(movie.getTitle());
        releaseDate.setText(DataUtil.extractYearFromDate(movie.getReleaseDate()));
        voteAverage.setText(DataUtil.formatMovieDetailsVoteAverage(movie.getVoteAverage()));
        overview.setText(movie.getOverview());
    }

    private void setMoviePoster(Movie movie) {
        Picasso.with(poster.getContext())
                .load(NetworkUtils.buildPosterUrl(movie.getPosterPath()))
                .placeholder(R.drawable.movie_poster_placeholder)
                .error(R.drawable.movie_poster_error)
                .into(poster);
    }

}
