package com.ysy.movieguide;

import android.app.Application;
import android.os.StrictMode;

import com.ysy.movieguide.details.DetailsComponent;
import com.ysy.movieguide.details.DetailsModule;
import com.ysy.movieguide.favorites.FavoritesModule;
import com.ysy.movieguide.listing.ListingComponent;
import com.ysy.movieguide.listing.ListingModule;
import com.ysy.movieguide.network.NetworkModule;

public class BaseApplication extends Application {

    private AppComponent appComponent;
    private DetailsComponent detailsComponent;
    private ListingComponent listingComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.enableDefaults();
        appComponent = createAppComponent();
    }

    private AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .favoritesModule(new FavoritesModule())
                .build();
    }

    public DetailsComponent createDetailsComponent() {
        detailsComponent = appComponent.plus(new DetailsModule());
        return detailsComponent;
    }

    public void releaseDetailsComponent() {
        detailsComponent = null;
    }

    public ListingComponent createListingComponent() {
        listingComponent = appComponent.plus(new ListingModule());
        return listingComponent;
    }

    public void releaseListingComponent() {
        listingComponent = null;
    }

    public ListingComponent getListingComponent() {
        return listingComponent;
    }
}
