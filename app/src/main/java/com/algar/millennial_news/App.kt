package com.algar.millennial_news

import android.app.Application
import org.koin.android.ext.android.startKoin
import com.algar.millennial_news.di.appComponent
import org.koin.dsl.module.Module

open class App: Application() {
    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    // Configuration
    open fun configureDi() = startKoin(this, provideComponents())

    open fun provideComponents(): List<Module> = appComponent(context = this)
}