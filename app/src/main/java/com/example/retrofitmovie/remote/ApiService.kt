package com.example.retrofitmovie.remote

import com.example.retrofitmovie.remote.model.Detail
import com.example.retrofitmovie.remote.model.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Call<MovieList>

    @GET("movie/{movie_id}")
    fun getDetail(@Path("movie_id") id: Int): Call<Detail>


}