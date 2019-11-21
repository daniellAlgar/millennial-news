package com.algar.remote

import com.algar.model.NewsResponse
import retrofit2.http.GET

interface NewsService {

    @GET("top-headlines?country=us")
    suspend fun topHeadlines(): NewsResponse
}