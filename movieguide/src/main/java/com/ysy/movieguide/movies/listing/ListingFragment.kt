package com.ysy.movieguide.movies.listing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ysy.movieguide.MovieGuideApp
import com.ysy.movieguide.R
import com.ysy.movieguide.movies.model.Movie
import kotlinx.android.synthetic.main.fragment_listing.*
import javax.inject.Inject

class ListingFragment : Fragment(), ListingView {

    @Inject
    lateinit var presenter: ListingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context!!.applicationContext as MovieGuideApp).createListingComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_listing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        presenter.setView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (context!!.applicationContext as MovieGuideApp).releaseListingComponent()
    }

    private fun initLayout() {
        movies_listing.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(context, 2)
        movies_listing.layoutManager = layoutManager
        movies_listing.setHasFixedSize(true)
        movies_listing.adapter = ListingAdapter()
    }

    override fun showMovies(movies: List<Movie>?) {
        (movies_listing.adapter as ListingAdapter).addMovies(movies)
    }
}
