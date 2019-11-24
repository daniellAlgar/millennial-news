package com.algar.millennial_news

import android.app.Application
import org.koin.core.context.startKoin
import com.algar.millennial_news.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module

open class App: Application() {
    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    // Configuration
    open fun configureDi() = startKoin {
        androidContext(this@App)
        modules(provideComponents())
    }

    open fun provideComponents(): List<Module> = appComponent(context = this)
}