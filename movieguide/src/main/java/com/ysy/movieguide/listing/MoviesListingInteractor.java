package com.ysy.movieguide.listing;

import com.ysy.movieguide.model.Movie;

import java.util.List;

import io.reactivex.Observable;

public interface MoviesListingInteractor {
    boolean isPaginationSupported();
    Observable<List<Movie>> fetchMovies(int page);
}
