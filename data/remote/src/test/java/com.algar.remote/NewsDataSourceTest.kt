package com.algar.remote

import android.util.Log
import com.algar.remote.model.ApiResponse.Error
import com.algar.remote.model.ApiResponse.Success
import com.algar.remote.base.BaseTest
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Test
import org.junit.Assert.assertEquals
import java.net.HttpURLConnection
import kotlinx.coroutines.runBlocking

class NewsDataSourceTest: BaseTest() {

    @Test
    fun `Fetching top headlines successfully returns Success with data`() {
        mockLog()
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
        mockLog()
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

    private fun mockLog() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }
}