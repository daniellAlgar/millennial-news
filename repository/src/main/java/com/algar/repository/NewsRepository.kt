package com.algar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.algar.model.NewsResponse
import com.algar.remote.NewsDataSource
import com.algar.remote.model.ApiResponse

interface NewsRepository {
    suspend fun getTopHeadlines(): LiveData<ApiResponse<NewsResponse>>
}

class NewsRepositoryImp(private val newsService: NewsDataSource) : NewsRepository {

    override suspend fun getTopHeadlines(): LiveData<ApiResponse<NewsResponse>> {
        val response = newsService.fetchTopHeadlines()
        return MutableLiveData<ApiResponse<NewsResponse>>().apply {
            value = response
        }
    }
}