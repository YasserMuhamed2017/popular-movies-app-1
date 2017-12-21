package com.elshadsm.popularmovies.models;

/**
 * Created by Elshad Seyidmammadov on 20.12.2017.
 */

public class Movie {
    private int poster; // drawable reference id

    public Movie(int image) {
        this.poster = image;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }
}
