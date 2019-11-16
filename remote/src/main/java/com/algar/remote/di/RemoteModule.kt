package com.algar.remote.di

import com.algar.remote.DateTimeSerializer
import com.algar.remote.NewsDataSource
import com.algar.remote.NewsService
import com.google.gson.GsonBuilder
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
fun createRemoteModule(baseUrl: String) = module {

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get())
            .build()
    }

    factory<Interceptor> {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
    }

    factory { get<Retrofit>().create(NewsService::class.java) }

    factory { NewsDataSource(newsService = get()) }

    single {
        GsonBuilder()
            .registerTypeAdapter(DateTime::class.java, DateTimeSerializer.INSTANCE)
            .create()
    }
}