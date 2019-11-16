package com.algar.remote

import com.algar.model.NewsResponse

/**
 * Implementation of [NewsService] interface.
 */
class NewsDataSource(private val newsService: NewsService) {

    suspend fun fetchTopHeadlines(): NewsResponse {
        return newsService.topHeadlines()
    }
}