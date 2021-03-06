package com.ysy.movieguide.listing;

import com.ysy.movieguide.model.Movie;
import com.ysy.movieguide.model.MoviesWrapper;
import com.ysy.movieguide.favorites.FavoritesInteractor;
import com.ysy.movieguide.listing.sorting.SortType;
import com.ysy.movieguide.listing.sorting.SortingOptionStore;
import com.ysy.movieguide.network.TmdbWebService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;

class MoviesListingInteractorImpl implements MoviesListingInteractor {

    private FavoritesInteractor favoritesInteractor;
    private TmdbWebService tmdbWebService;
    private SortingOptionStore sortingOptionStore;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final int NEWEST_MIN_VOTE_COUNT = 50;

    MoviesListingInteractorImpl(FavoritesInteractor favoritesInteractor,
                                TmdbWebService tmdbWebService, SortingOptionStore store) {
        this.favoritesInteractor = favoritesInteractor;
        this.tmdbWebService = tmdbWebService;
        sortingOptionStore = store;
    }

    @Override
    public boolean isPaginationSupported() {
        int selectedOption = sortingOptionStore.getSelectedOption();
        return selectedOption != SortType.FAVORITES.getValue();
    }

    @Override
    public Observable<List<Movie>> fetchMovies(int page) {
        int selectedOption = sortingOptionStore.getSelectedOption();
        if (selectedOption == SortType.MOST_POPULAR.getValue()) {
            return tmdbWebService.popularMovies(page).map(MoviesWrapper::getMovieList);
        } else if (selectedOption == SortType.HIGHEST_RATED.getValue()) {
            return tmdbWebService.highestRatedMovies(page).map(MoviesWrapper::getMovieList);
        } else if (selectedOption == SortType.NEWEST.getValue()) {
            Calendar cal = Calendar.getInstance();
            String maxReleaseDate = dateFormat.format(cal.getTime());
            return tmdbWebService.newestMovies(maxReleaseDate, NEWEST_MIN_VOTE_COUNT).map(MoviesWrapper::getMovieList);
        } else {
            return Observable.just(favoritesInteractor.getFavorites());
        }
    }
}
