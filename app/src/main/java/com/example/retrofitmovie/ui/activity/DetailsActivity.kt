package com.example.retrofitmovie.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.size.Scale
import com.example.retrofitmovie.R
import com.example.retrofitmovie.remote.Repository
import com.example.retrofitmovie.utils.Constant
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val movieID = intent.getIntExtra("id", 1)
        getData(movieID)

    }

    private fun getData(id: Int) {
        with(Repository(this)) {
            detail(id)
            detail.observe(this@DetailsActivity) {

                val imagePoster = Constant.POSTER_BASE_URL + it!!.poster_path
                ivPoster.load(imagePoster) {
                    crossfade(true)
                    placeholder(R.drawable.ss_1)
                    scale(Scale.FILL)
                }
                ivBackground.load(imagePoster) {
                    crossfade(true)
                    placeholder(R.drawable.ss_1)
                    scale(Scale.FILL)
                }

                tvName.text = it.title
                tvTag.text = it.tagline
                tvRelease.text = it.release_date
                tvRating.text = it.vote_average.toString()
                tvRun.text = it.runtime.toString()
                tvBudget.text = it.budget.toString()
                tvRevenue.text = it.revenue.toString()
                tvOverview.text = it.overview

            }
        }
    }

}