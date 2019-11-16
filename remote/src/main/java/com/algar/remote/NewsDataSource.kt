package com.algar.remote

import com.algar.model.NewsResponse

/**
 * Implementation of [NewsService] interface.
 */
class NewsDataSource(
    private val newsService: NewsService,
    private val dispatchers: AppDispatchers
) {

    suspend fun fetchTopHeadlines(): ApiResponse<NewsResponse> {
        return safeApiCall(dispatcher = dispatchers.io) {
            newsService.topHeadlines()
        }
    }
}