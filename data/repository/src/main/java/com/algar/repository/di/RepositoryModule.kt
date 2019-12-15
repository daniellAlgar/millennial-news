package com.algar.repository.di

import com.algar.repository.AppDispatchers
import com.algar.repository.NewsRepository
import com.algar.repository.NewsRepositoryImp
import com.algar.repository.utils.CoroutineLaunch
import com.algar.repository.utils.CoroutineLaunchImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {
    single { AppDispatchers(main = Dispatchers.Main, io = Dispatchers.IO) }
    single { NewsRepositoryImp(
        newsService = get(),
        dao = get(),
        coroutines = get()
    ) as NewsRepository }
    single { CoroutineLaunchImpl() as CoroutineLaunch }
}