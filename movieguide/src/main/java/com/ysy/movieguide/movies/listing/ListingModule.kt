package com.ysy.movieguide.movies.listing

import com.ysy.movieguide.movies.api.TheMovieDbApi
import dagger.Module
import dagger.Provides

@Module
class ListingModule {

    @Provides
    fun provideListingPresenter(listingInteractor: ListingInteractor): ListingPresenter {
        return ListingPresenterImpl(listingInteractor, null)
    }

    @Provides
    @ListingScope
    fun provideListingInteractor(api: TheMovieDbApi): ListingInteractor {
        return ListingInteractorImpl(api)
    }
}
