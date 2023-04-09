package com.example.retrofitmovie.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.size.Scale
import com.example.retrofitmovie.R
import com.example.retrofitmovie.api.ApiClient
import com.example.retrofitmovie.api.ApiService
import com.example.retrofitmovie.response.DetailResponse
import com.example.retrofitmovie.utils.Constant
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.ivPoster
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {

    private val api: ApiService by lazy {
        ApiClient().getClient().create(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val movieID = intent.getIntExtra("id", 1)
        val callDetail = api.getDetail(movieID)
        callDetail.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                when (response.code()) {
                    in 200..299 -> {
                        response.body().let { itBody ->
                            val imagePoster = Constant.POSTER_BASE_URL + itBody!!.poster_path
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
                            tvName.text = itBody.title
                            tvTag.text = itBody.tagline
                            tvRelease.text = itBody.release_date
                            tvRating.text = itBody.vote_average.toString()
                            tvRun.text = itBody.runtime.toString()
                            tvBudget.text = itBody.budget.toString()
                            tvRevenue.text = itBody.revenue.toString()
                            tvOverview.text = itBody.overview

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

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.e("onFailure", "Err : ${t.message}")
            }

        })
    }
}