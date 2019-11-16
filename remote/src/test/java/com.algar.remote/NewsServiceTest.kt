package com.algar.remote

import com.algar.remote.base.BaseTest
import org.junit.Test
import org.junit.Assert.assertEquals
import java.net.HttpURLConnection
import kotlinx.coroutines.runBlocking

class NewsServiceTest: BaseTest() {

    @Test
    fun `Fetching top headlines in the US successfully`() {
        mockHttpResponse(
            mockServer = mockServer,
            fileName = "top-headlines-in-us.json",
            responseCode = HttpURLConnection.HTTP_OK
        )

        runBlocking {
            val headlines = newsService.topHeadlines()
            assertEquals(20, headlines.articles.size)
        }
    }
}