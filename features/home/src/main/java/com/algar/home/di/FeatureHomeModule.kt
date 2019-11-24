package com.algar.home.di

import com.algar.home.HomeViewModel
import com.algar.home.domain.GetTopHeadlinesUseCase
import org.koin.dsl.module

val featureHomeModule = module {
    factory { GetTopHeadlinesUseCase(repository = get()) }
    factory { HomeViewModel(getTopHeadlinesUseCase = get(), dispatchers = get()) }
}