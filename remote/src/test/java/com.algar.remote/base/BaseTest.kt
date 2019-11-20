package com.algar.remote.base

import com.algar.remote.NewsDataSource
import com.algar.remote.di.createRemoteModule
import com.squareup.okhttp.mockwebserver.Dispatcher
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import org.koin.test.KoinTest
import java.io.File
import com.squareup.okhttp.mockwebserver.RecordedRequest
import java.io.IOException

abstract class BaseTest: KoinTest {

    protected val newsService: NewsDataSource by inject()
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
        StandAloneContext.startKoin(listOf(createRemoteModule(
            baseUrl = mockServer.url("/").toString(),
            apiKey = "dummy-key"
        )))
    }

    private fun configureMockServer() {
        mockServer = MockWebServer()
        mockServer.start()
    }

    fun mockHttpResponse(server: MockWebServer, fileName: String, responseCode: Int) {
        server.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(path = fileName))
        )
    }

    fun mockNoNetworkConnection(server: MockWebServer) {
        val noNetworkDispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class, IOException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                throw IOException("No network connection")
            }
        }

        server.setDispatcher(noNetworkDispatcher)
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun getJson(path: String): String {
        val uri = javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}