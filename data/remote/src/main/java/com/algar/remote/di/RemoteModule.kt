package com.algar.remote.di

import com.algar.remote.helpers.DateTimeSerializer
import com.algar.remote.NewsDataSource
import com.algar.remote.NewsService
import com.algar.remote.di.NamedInterceptor.CURL_PRINT_INTERCEPTOR
import com.algar.remote.di.NamedInterceptor.ADD_HEADERS_INTERCEPTOR
import com.algar.remote.di.NamedInterceptor.HTTP_LOGGING_INTERCEPTOR
import com.algar.remote.helpers.Logger
import com.algar.remote.helpers.RetrofitCurlPrinterInterceptor
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Provides the modules needed for our network layer to work.
 *
 * @param baseUrl The base api endpoint
 */
fun createRemoteModule(baseUrl: String, apiKey: String) = module {

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }


    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>(namedInterceptor(HTTP_LOGGING_INTERCEPTOR)))
            .addInterceptor(get<Interceptor>(namedInterceptor(ADD_HEADERS_INTERCEPTOR)))
            .addInterceptor(get<Interceptor>(namedInterceptor(CURL_PRINT_INTERCEPTOR)))
            .build()
    }

    factory<Interceptor>(namedInterceptor(type = CURL_PRINT_INTERCEPTOR)) {
        RetrofitCurlPrinterInterceptor(logger = Logger)
    }

    factory<Interceptor>(namedInterceptor(type = HTTP_LOGGING_INTERCEPTOR)) {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }
    }

    factory(namedInterceptor(type = ADD_HEADERS_INTERCEPTOR)) {
        Interceptor {
            val interceptor = it.request()
                .newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", apiKey)
                .build()

            it.proceed(interceptor)
        }
    }

    factory { get<Retrofit>().create(NewsService::class.java) }

    factory { NewsDataSource(newsService = get(), dispatcher = get()) }

    single {
        GsonBuilder()
            .registerTypeAdapter(DateTime::class.java, DateTimeSerializer.INSTANCE)
            .create()
    }

    factory { Dispatchers.IO }
}

private enum class NamedInterceptor {
    HTTP_LOGGING_INTERCEPTOR,
    ADD_HEADERS_INTERCEPTOR,
    CURL_PRINT_INTERCEPTOR
}

/**
 * Turns a [NamedInterceptor] to a [Qualifier].
 */
private fun namedInterceptor(type: NamedInterceptor): Qualifier = named(type.toString())