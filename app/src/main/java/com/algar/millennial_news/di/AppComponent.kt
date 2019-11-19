package com.algar.millennial_news.di

import com.algar.home.di.featureHomeModule
import com.algar.remote.di.createRemoteModule
import com.algar.repository.di.repositoryModule

val appComponent = listOf(
    createRemoteModule("https://"),
    repositoryModule,
    featureHomeModule
)