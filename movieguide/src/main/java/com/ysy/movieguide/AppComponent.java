package com.ysy.movieguide;

import com.ysy.movieguide.details.DetailsComponent;
import com.ysy.movieguide.details.DetailsModule;
import com.ysy.movieguide.favorites.FavoritesModule;
import com.ysy.movieguide.listing.ListingComponent;
import com.ysy.movieguide.listing.ListingModule;
import com.ysy.movieguide.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        FavoritesModule.class})
public interface AppComponent {
    DetailsComponent plus(DetailsModule detailsModule);
    ListingComponent plus(ListingModule listingModule);
}
