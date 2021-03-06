package com.ysy.movieguide.details;

import com.ysy.movieguide.model.Review;
import com.ysy.movieguide.model.ReviewsWrapper;
import com.ysy.movieguide.model.Video;
import com.ysy.movieguide.model.VideoWrapper;
import com.ysy.movieguide.network.TmdbWebService;

import java.util.List;

import io.reactivex.Observable;

class MovieDetailsInteractorImpl implements MovieDetailsInteractor {

    private TmdbWebService tmdbWebService;

    MovieDetailsInteractorImpl(TmdbWebService tmdbWebService) {
        this.tmdbWebService = tmdbWebService;
    }

    @Override
    public Observable<List<Video>> getTrailers(final String id) {
        return tmdbWebService.trailers(id).map(VideoWrapper::getVideos);
    }

    @Override
    public Observable<List<Review>> getReviews(final String id) {
        return tmdbWebService.reviews(id).map(ReviewsWrapper::getReviews);
    }
}
