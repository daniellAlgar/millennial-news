package com.algar.home.domain

import androidx.lifecycle.LiveData
import com.algar.model.NewsResponse
import com.algar.repository.NewsRepository
import com.algar.remote.model.ApiResponse

class GetTopHeadlinesUseCase(private val repository: NewsRepository) {

    suspend operator fun invoke(): LiveData<ApiResponse<NewsResponse>> = repository.getTopHeadlines()
}