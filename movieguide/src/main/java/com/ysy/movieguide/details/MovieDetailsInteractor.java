package com.ysy.movieguide.details;

import com.ysy.movieguide.model.Review;
import com.ysy.movieguide.model.Video;

import java.util.List;

import io.reactivex.Observable;

public interface MovieDetailsInteractor {
    Observable<List<Video>> getTrailers(String id);
    Observable<List<Review>> getReviews(String id);
}
