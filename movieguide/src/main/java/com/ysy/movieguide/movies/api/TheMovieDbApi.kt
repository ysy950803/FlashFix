package com.ysy.movieguide.movies.api

import retrofit2.http.GET
import retrofit2.http.QueryMap
import rx.Observable

interface TheMovieDbApi {

    @GET("/3/discover/movie")
    fun getVenues(@QueryMap map: Map<String, String>): Observable<PopularMoviesResponse>
}
