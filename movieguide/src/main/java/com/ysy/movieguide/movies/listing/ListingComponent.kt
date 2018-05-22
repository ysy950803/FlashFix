package com.ysy.movieguide.movies.listing

import dagger.Subcomponent

@ListingScope
@Subcomponent(modules = [(ListingModule::class)])
interface ListingComponent {
    fun inject(target: ListingFragment)
}
