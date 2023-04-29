package com.example.retrofitmovie.remote

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.retrofitmovie.remote.model.Detail
import com.example.retrofitmovie.remote.model.MovieList
import retrofit2.Response

class Repository(val context: Context) {

    val popularMovies = MutableLiveData<MovieList>()
    val detail = MutableLiveData<Detail>()

    fun popularMovies() {
        val call = ApiClient().apiService().getPopularMovies(1)
        call.enqueue(object : retrofit2.Callback<MovieList> {
            override fun onResponse(
                call: retrofit2.Call<MovieList>,
                response: Response<MovieList>
            ) {
                if (response.code() == 200 && response.body() != null) popularMovies.value =
                    response.body()
                Log.d("TAG", popularMovies.value.toString())
            }

            override fun onFailure(call: retrofit2.Call<MovieList>, t: Throwable) {}
        })
    }

    fun detail(id: Int) {
        val call = ApiClient().apiService().getDetail(id)
        call.enqueue(object : retrofit2.Callback<Detail> {
            override fun onResponse(
                call: retrofit2.Call<Detail>,
                response: Response<Detail>
            ) {
                if (response.code() == 200 && response.body() != null) detail.value =
                    response.body()
                Log.d("TAG", detail.value.toString())
            }

            override fun onFailure(call: retrofit2.Call<Detail>, t: Throwable) {}
        })
    }

}