package com.ysy.movieguide.movies.listing

import com.ysy.movieguide.movies.model.Movie

interface ListingView {
    fun showMovies(movies: List<Movie>?)
}
