package com.algar.repository.di

import com.algar.repository.AppDispatchers
import com.algar.repository.NewsRepository
import com.algar.repository.NewsRepositoryImp
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {
    single { NewsRepositoryImp(newsService = get()) as NewsRepository }
    single { AppDispatchers(main = Dispatchers.Main, io = Dispatchers.IO) }
}