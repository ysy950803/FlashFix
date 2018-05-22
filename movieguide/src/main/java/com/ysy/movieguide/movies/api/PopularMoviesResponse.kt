package com.ysy.movieguide.movies.api

import com.google.gson.annotations.SerializedName
import com.ysy.movieguide.movies.model.Movie

class PopularMoviesResponse {

    @SerializedName("results")
    lateinit var movies: List<Movie>
}
