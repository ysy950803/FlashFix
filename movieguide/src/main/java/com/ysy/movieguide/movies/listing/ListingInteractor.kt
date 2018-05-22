package com.ysy.movieguide.movies.listing

import com.ysy.movieguide.movies.api.PopularMoviesResponse
import rx.Observable

interface ListingInteractor {
    fun getListOfMovies() : Observable<PopularMoviesResponse>
}
