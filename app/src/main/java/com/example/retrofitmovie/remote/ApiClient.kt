package com.example.retrofitmovie.remote

import com.example.retrofitmovie.utils.Constant
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient {
    private val basePath = Constant.BASE_URL

    private val requestInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", Constant.API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor chain.proceed(request)
    }

    private fun retrofitInstance(): Retrofit {
        val okhttp = OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(requestInterceptor).build()
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        val gson = gsonBuilder.create()
        return Retrofit.Builder().baseUrl(basePath).client(okhttp)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    fun apiService(): ApiService {
        return retrofitInstance().create(ApiService::class.java)
    }
}