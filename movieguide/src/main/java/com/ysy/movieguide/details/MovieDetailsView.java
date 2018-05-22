package com.ysy.movieguide.details;

import com.ysy.movieguide.Movie;
import com.ysy.movieguide.Review;
import com.ysy.movieguide.Video;

import java.util.List;

/**
 * @author arun
 */
interface MovieDetailsView
{
    void showDetails(Movie movie);
    void showTrailers(List<Video> trailers);
    void showReviews(List<Review> reviews);
    void showFavorited();
    void showUnFavorited();
}
