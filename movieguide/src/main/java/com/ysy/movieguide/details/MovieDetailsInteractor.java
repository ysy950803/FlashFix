package com.ysy.movieguide.details;

import com.ysy.movieguide.Review;
import com.ysy.movieguide.Video;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author arun
 */
public interface MovieDetailsInteractor
{
    Observable<List<Video>> getTrailers(String id);
    Observable<List<Review>> getReviews(String id);
}
