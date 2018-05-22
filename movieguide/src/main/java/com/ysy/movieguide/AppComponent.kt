package com.ysy.movieguide

import com.ysy.movieguide.movies.api.ApiModule
import com.ysy.movieguide.movies.listing.ListingComponent
import com.ysy.movieguide.movies.listing.ListingModule
import com.ysy.movieguide.movies.network.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (NetworkModule::class), (ApiModule::class)])
interface AppComponent {
    fun plus(listingModule: ListingModule): ListingComponent
}
