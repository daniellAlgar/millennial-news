package com.algar.millennial_news.di

import android.content.Context
import com.algar.home.di.featureHomeModule
import com.algar.millennial_news.R
import com.algar.remote.di.createRemoteModule
import com.algar.repository.di.repositoryModule

fun appComponent(context: Context) = listOf(
    createRemoteModule(
        baseUrl = "https://newsapi.org/v2/",
        apiKey = context.getString(R.string.news_api_key)
    ),
    repositoryModule,
    featureHomeModule
)