package com.algar.remote

import com.algar.model.NewsResponse
import com.algar.remote.helpers.safeApiCall
import com.algar.remote.model.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Implementation of [NewsService] interface.
 */
class NewsDataSource(
    private val newsService: NewsService,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun fetchTopHeadlines(): ApiResponse<NewsResponse> {
        return safeApiCall(dispatcher = dispatcher) {
            newsService.topHeadlines()
        }
    }
}