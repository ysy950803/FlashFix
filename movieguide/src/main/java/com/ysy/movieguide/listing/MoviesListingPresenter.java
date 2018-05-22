package com.ysy.movieguide.listing;

public interface MoviesListingPresenter {

    void firstPage();

    void nextPage();

    void setView(MoviesListingView view);

    void destroy();
}
