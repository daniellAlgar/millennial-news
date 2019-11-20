package com.algar.remote.di

import com.algar.remote.helpers.DateTimeSerializer
import com.algar.remote.NewsDataSource
import com.algar.remote.NewsService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import org.koin.dsl.module.module
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

    val headersInterceptor = Interceptor {
        val interceptor = it.request()
            .newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("x-api-key", apiKey)
            .build()

        it.proceed(interceptor)
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get())
            .addInterceptor(headersInterceptor)
            .build()
    }

    factory<Interceptor> {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
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