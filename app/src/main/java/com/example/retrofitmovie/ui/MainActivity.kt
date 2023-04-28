package com.example.retrofitmovie.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitmovie.R
import com.example.retrofitmovie.adapter.MovieAdapter
import com.example.retrofitmovie.api.ApiClient
import com.example.retrofitmovie.api.ApiService
import com.example.retrofitmovie.response.MovieListResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class MainActivity : AppCompatActivity() {

    private val movieAdapter by lazy { MovieAdapter() }
    private val api: ApiService by lazy {
        ApiClient().getClient().create(ApiService::class.java)
    }
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prLoad.visibility = View.VISIBLE
        getCurrentData()

        //val callMovieApi = api.getPopularMovies(1)
        /*callMovieApi.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                prLoad.visibility = View.GONE
                when (response.code()) {
                    in 200..299 -> {
                        Log.d("Response Code", "success messages: ${response.code()}")
                        response.body().let { itBody ->
                            itBody?.results.let { itData ->
                                if (itData!!.isNotEmpty()) {
                                    movieAdapter.differ.submitList(itData)
                                    rvMovie.apply {
                                        layoutManager = LinearLayoutManager(this@MainActivity)
                                        adapter = movieAdapter
                                    }
                                }
                            }
                        }
                    }
                    in 300..399 -> {
                        Log.d("Response Code", "Redirection messages: ${response.code()}")
                    }
                    in 400..499 -> {
                        Log.d("Response Code", "Client error messages: ${response.code()}")
                    }
                    in 500..599 -> {
                        Log.d("Response Code", "Server error messages: ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                prLoad.visibility = View.GONE
                Log.d("Response Code", "Server error messages: ${t.message}")
            }

        })*/
    }

    private fun getCurrentData() {

        CoroutineScope(Dispatchers.IO).launch {
            try {

                val response = api.getPopularMovies(1).awaitResponse()

                if (response.isSuccessful) {
                    val data = response.body()!!
                    Log.d(TAG, data.toString())

                    withContext(Dispatchers.Main) {

                        prLoad.visibility = View.GONE

                        data.let { itBody ->
                            itBody.results.let { itData ->
                                if (itData.isNotEmpty()) {
                                    movieAdapter.differ.submitList(itData)
                                    rvMovie.apply {
                                        layoutManager = LinearLayoutManager(this@MainActivity)
                                        adapter = movieAdapter
                                    }
                                }
                            }
                        }

                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        applicationContext,
                        "Seems like something went wrong...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}