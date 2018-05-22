package com.ysy.movieguide.movies.listing

import android.util.Log
import com.ysy.movieguide.movies.api.PopularMoviesResponse
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ListingPresenterImpl(val interactor: ListingInteractor, private var view: ListingView?) : ListingPresenter {

    override fun setView(listingView: ListingView) {
        view = listingView
        getListOfMovies()
    }

    private fun getListOfMovies() {
        interactor.getListOfMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { popularMoviesResponse -> onGetMoviewsSuccess(popularMoviesResponse) },
                        { e -> onGetMoviesFailure(e) }
                )
    }

    private fun onGetMoviesFailure(e: Throwable?) {
        Log.e(e?.message, e?.stackTrace.toString())
    }

    private fun onGetMoviewsSuccess(popularMoviesResponse: PopularMoviesResponse?) {
        view?.showMovies(popularMoviesResponse?.movies)
    }
}
