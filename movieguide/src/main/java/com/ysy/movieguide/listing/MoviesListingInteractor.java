package com.ysy.movieguide.listing;

import com.ysy.movieguide.Movie;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author arun
 */
public interface MoviesListingInteractor
{
    boolean isPaginationSupported();
    Observable<List<Movie>> fetchMovies(int page);
}
