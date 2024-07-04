package com.example.newsly.api

import com.example.newsly.models.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


//Top-Headlines
//https://newsapi.org/v2/top-headlines?country=us&apiKey=a1a1989539544fc39cc44bff050d14fc

//Headlines
//https://newsapi.org/v2/everything?q=bitcoin&apiKey=a1a1989539544fc39cc44bff050d14fc

private const val BASE_URL = "https://newsapi.org/"
private const val API_KEY = "a1a1989539544fc39cc44bff050d14fc"

interface NewsAPI {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getTopHeadlines(@Query("country") country: String, @Query("page") page: Int): Call<News>
}