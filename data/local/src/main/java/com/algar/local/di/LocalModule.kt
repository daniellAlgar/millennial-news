package com.algar.local.di

import com.algar.local.NewsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Obs: No Gson definition here since we have that in the :remote module.
 */
val localModule = module {
    single { NewsDatabase.buildDatabase(context = androidContext()) }
    factory { (get<NewsDatabase>()).newsDao() }
}