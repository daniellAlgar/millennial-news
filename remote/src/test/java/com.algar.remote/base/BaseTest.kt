package com.algar.remote.base

import com.algar.remote.NewsService
import com.algar.remote.di.createRemoteModule
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import org.koin.test.KoinTest
import java.io.File

abstract class BaseTest: KoinTest {

    protected val newsService: NewsService by inject()
    protected lateinit var mockServer: MockWebServer

    @Before
    open fun setUp() {
        configureMockServer()
        configureDI()
    }

    @After
    open fun tearDown() {
        stopMockServer()
        StandAloneContext.stopKoin()
    }

    private fun stopMockServer() {
        mockServer.shutdown()
    }

    private fun configureDI() {
        StandAloneContext.startKoin(listOf(createRemoteModule(mockServer.url("/").toString())))
    }

    private fun configureMockServer() {
        mockServer = MockWebServer()
        mockServer.start()
    }

    fun mockHttpResponse(mockServer: MockWebServer, fileName: String, responseCode: Int) {
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(path = fileName))
        )
    }

    private fun getJson(path: String): String {
        val uri = javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}