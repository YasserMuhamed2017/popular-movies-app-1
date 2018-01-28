package com.elshadsm.popularmovies.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.elshadsm.popularmovies.R;
import com.elshadsm.popularmovies.data.MoviesContract;
import com.elshadsm.popularmovies.models.Constants;
import com.elshadsm.popularmovies.models.Movie;
import com.elshadsm.popularmovies.models.Review;
import com.elshadsm.popularmovies.services.ReviewsQueryLoader;
import com.elshadsm.popularmovies.utils.DataUtil;
import com.elshadsm.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.elshadsm.popularmovies.models.Constants.MOVIES_QUERY_LOADER_FILTER_TYPE_EXTRA;
import static com.elshadsm.popularmovies.models.Constants.REVIEWS_QUERY_LOADER_MOVIE_ID_EXTRA;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView title, releaseDate, voteAverage, overview;
    private ImageView poster;
    private ToggleButton toggleButton;
    private Movie movie;

    public static final int REVIEWS_QUERY_LOADER_ID = 511966;
    private ReviewsQueryLoader reviewsQueryLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initWidgets();
        applyWidgetsValue();
        registerEventHandlers();
    }

    private void initWidgets() {
        title = findViewById(R.id.movie_details_title);
        releaseDate = findViewById(R.id.movie_details_release_date);
        voteAverage = findViewById(R.id.movie_details_vote_average);
        overview = findViewById(R.id.movie_details_overview);
        poster = findViewById(R.id.movie_details_poster);
        toggleButton = findViewById(R.id.movie_details_mark_as_favorite);
        reviewsQueryLoader = new ReviewsQueryLoader(MovieDetailsActivity.this, this);
    }

    private void applyWidgetsValue() {
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.INTENT_EXTRA_NAME_MOVIE_DETAILS)) {
            Bundle data = intent.getExtras();
            assert data != null;
            movie = data.getParcelable(Constants.INTENT_EXTRA_NAME_MOVIE_DETAILS);
            setMovieDetails();
            setReviews();
        }
    }

    private void registerEventHandlers() {
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                if (isChecked) {
                    addMovieToFavoriteList();
                    return;
                }
                removeMovieFromFavoriteList();
            }
        });
    }

    private void setMovieDetails() {
        setMoviePoster();
        title.setText(movie.getTitle());
        releaseDate.setText(DataUtil.extractYearFromDate(movie.getReleaseDate()));
        voteAverage.setText(DataUtil.formatMovieDetailsVoteAverage(movie.getVoteAverage()));
        overview.setText(movie.getOverview());
        toggleButton.setChecked(isFavorite());
    }

    private void setMoviePoster() {
        Picasso.with(poster.getContext())
                .load(NetworkUtils.buildPosterUrl(movie.getPosterPath()))
                .placeholder(R.drawable.movie_poster_placeholder)
                .error(R.drawable.movie_poster_error)
                .into(poster);
    }

    private boolean isFavorite() {
        try (Cursor cursor = getContentResolver().query(
                MoviesContract.MoviesEntry.CONTENT_URI, null, null, null, null)) {
            if (cursor == null) { return false; }
            while (cursor.moveToNext()) {
                int movieId = cursor.getInt(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID));
                if (movieId == movie.getId()) { return true; }
            }
        }
        return false;
    }

    private void addMovieToFavoriteList() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID, movie.getId());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_DESCRIPTION, movie.getOverview());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, contentValues);
    }

    private void removeMovieFromFavoriteList() {
        String selection = MoviesContract.MoviesEntry.COLUMN_MOVIE_ID + "=?";
        String[] selectionArgs = {String.valueOf(movie.getId())};
        getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI, selection, selectionArgs);
    }

    private void setReviews() {
        Bundle bundle = new Bundle();
        bundle.putLong(REVIEWS_QUERY_LOADER_MOVIE_ID_EXTRA, movie.getId());
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<List<Review>> loader = loaderManager.getLoader(REVIEWS_QUERY_LOADER_ID);
        if (loader == null) {
            loaderManager.initLoader(REVIEWS_QUERY_LOADER_ID, bundle, reviewsQueryLoader);
        } else {
            loaderManager.restartLoader(REVIEWS_QUERY_LOADER_ID, bundle, reviewsQueryLoader);
        }
    }

}
