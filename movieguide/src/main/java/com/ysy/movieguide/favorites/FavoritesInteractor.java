package com.ysy.movieguide.favorites;

import com.ysy.movieguide.model.Movie;

import java.util.List;

public interface FavoritesInteractor {
    void setFavorite(Movie movie);
    boolean isFavorite(String id);
    List<Movie> getFavorites();
    void unFavorite(String id);
}
