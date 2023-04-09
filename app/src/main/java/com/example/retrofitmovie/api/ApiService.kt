package com.example.retrofitmovie.api

import com.example.retrofitmovie.response.DetailResponse
import com.example.retrofitmovie.response.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Call<MovieListResponse>

    @GET("movie/{movie_id}")
    fun getDetail(@Path("movie_id") id: Int): Call<DetailResponse>


}