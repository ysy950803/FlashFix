package com.ysy.movieguide.listing;

import com.ysy.movieguide.Movie;

import java.util.List;

interface MoviesListingView {

    void showMovies(List<Movie> movies);

    void loadingStarted();

    void loadingFailed(String errorMessage);

    void onMovieClicked(Movie movie);
}
