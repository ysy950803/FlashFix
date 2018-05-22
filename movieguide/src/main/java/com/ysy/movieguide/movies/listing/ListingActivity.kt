package com.ysy.movieguide.movies.listing

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ysy.movieguide.R

class ListingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)
        setToolbar()
        loadListingFragment(savedInstanceState)
    }

    private fun setToolbar() {
        supportActionBar?.title = getString(R.string.movie_guide)
    }

    private fun loadListingFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.listingContainer, ListingFragment(), ListingFragment::class.simpleName)
                    .commit()
        }
    }
}
