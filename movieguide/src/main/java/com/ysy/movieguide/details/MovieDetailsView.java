package com.ysy.movieguide.details;

import com.ysy.movieguide.model.Movie;
import com.ysy.movieguide.model.Review;
import com.ysy.movieguide.model.Video;

import java.util.List;

interface MovieDetailsView {
    void showDetails(Movie movie);
    void showTrailers(List<Video> trailers);
    void showReviews(List<Review> reviews);
    void showFavorited();
    void showUnFavorited();
}
