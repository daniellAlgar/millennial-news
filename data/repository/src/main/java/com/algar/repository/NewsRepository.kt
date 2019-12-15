package com.algar.repository

import androidx.lifecycle.LiveData
import com.algar.local.dao.NewsDao
import com.algar.model.Article
import com.algar.model.NewsResponse
import com.algar.model.shouldRefreshFromNetwork
import com.algar.remote.NewsDataSource
import com.algar.remote.model.ApiResponse
import com.algar.repository.utils.NetworkBoundResourceCoroutines
import com.algar.repository.utils.Resource

interface NewsRepository {
    suspend fun getTopHeadlines(forceRefresh: Boolean = true): LiveData<Resource<List<Article>>>
}
// TODO: Add tests
class NewsRepositoryImp(
    private val newsService: NewsDataSource,
    private val dao: NewsDao
) : NewsRepository {

    override suspend fun getTopHeadlines(forceRefresh: Boolean): LiveData<Resource<List<Article>>> {
        return object : NetworkBoundResourceCoroutines<List<Article>, NewsResponse>() {

            override suspend fun saveCallResult(data: NewsResponse) = dao.save(articles = data.articles)

            override fun shouldFetch(data: List<Article>?) = data.isNullOrEmpty() || forceRefresh || data.shouldRefreshFromNetwork()

            override fun loadFromDb(): LiveData<List<Article>> = dao.getArticles()

            override suspend fun createCall(): ApiResponse<NewsResponse> = newsService.fetchTopHeadlines()
        }.asLiveData()
    }
}