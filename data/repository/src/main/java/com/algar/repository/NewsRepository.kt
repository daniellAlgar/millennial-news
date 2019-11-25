package com.algar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.algar.model.Article
import com.algar.model.NewsResponse
import com.algar.remote.NewsDataSource
import com.algar.remote.model.ApiResponse
import com.algar.repository.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface NewsRepository {
    suspend fun getTopHeadlines(): LiveData<Resource<List<Article>>>
}

class NewsRepositoryImp(private val newsService: NewsDataSource) : NewsRepository {

    override suspend fun getTopHeadlines(): LiveData<Resource<List<Article>>> {
        val response = newsService.fetchTopHeadlines()
        val resultValue = MutableLiveData<Resource<List<Article>>>()

        withContext(Dispatchers.Main) {
            resultValue.value = responseToResource(response = response)
        }
        return resultValue
    }

    private fun responseToResource(response: ApiResponse<NewsResponse>): Resource<List<Article>> = when (response) {
        is ApiResponse.Success -> Resource.success(data = response.body.articles)
        is ApiResponse.Error -> Resource.error(error = response.error, data = null)
    }
}