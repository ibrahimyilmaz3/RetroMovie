package com.example.retrofitmovie.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitmovie.R
import com.example.retrofitmovie.remote.Repository
import com.example.retrofitmovie.ui.adapter.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prLoad.visibility = View.VISIBLE
        setup()

    }

    private fun setup() {
        rvMovie.layoutManager = LinearLayoutManager(this)

        prLoad.visibility = View.VISIBLE
        with(Repository(this)) {
            popularMovies()
            popularMovies.observe(this@MainActivity) {
                prLoad.visibility = View.GONE
                if (it.results.isNotEmpty()) {
                    rvMovie.adapter = MovieAdapter(it.results)
                }
            }
        }
    }
}