package com.djekagoran.mymovieapp.ui.recently_movie

import android.os.Bundle
import android.view.MenuItem
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.base.BaseActivity
import com.djekagoran.mymovieapp.ui.recently_movie.recentlymovie.RecentlyMovieFragment
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_recently_movie.*

class RecentlyMovieActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recently_movie)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RecentlyMovieFragment.newInstance())
                .commit()
        }

        setActionBar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        app_bar.addOnOffsetChangedListener(object: AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsing_toolbar_layout.title = getString(R.string.title_recently_movie)
                    isShow = true
                } else if(isShow) {
                    collapsing_toolbar_layout.title = " "
                    isShow = false
                }
            }
        })
    }
}
