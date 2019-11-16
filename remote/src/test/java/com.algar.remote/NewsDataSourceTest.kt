package com.algar.remote

import com.algar.remote.ApiResponse.Error
import com.algar.remote.ApiResponse.NetworkError
import com.algar.remote.ApiResponse.Success
import com.algar.remote.base.BaseTest
import org.junit.Test
import org.junit.Assert.assertEquals
import java.net.HttpURLConnection
import kotlinx.coroutines.runBlocking

class NewsDataSourceTest: BaseTest() {

    @Test
    fun `Fetching top headlines successfully returns Success with data`() {
        mockHttpResponse(
            server = mockServer,
            fileName = "top-headlines-in-us.json",
            responseCode = HttpURLConnection.HTTP_OK
        )

        runBlocking {
            val response = newsService.fetchTopHeadlines()
            assert(response is Success)
            val articles = (response as Success).body.articles
            val expectedNumberOfArticles = 20
            assertEquals(expectedNumberOfArticles, articles.size)
        }
    }

    @Test
    fun `Fetching top headlines goes wrong and returns an Error`() {
        mockHttpResponse(
            server = mockServer,
            fileName = "error-request.json",
            responseCode = HttpURLConnection.HTTP_UNAUTHORIZED
        )

        runBlocking {
            val response = newsService.fetchTopHeadlines()
            assert(response is Error)
        }
    }

    @Test
    fun `Fetch from api when there is no network connection returns a NetworkError`() {
        mockNoNetworkConnection(mockServer = mockServer)

        runBlocking {
            val response = newsService.fetchTopHeadlines()
            assert(response is NetworkError)
        }
    }
}