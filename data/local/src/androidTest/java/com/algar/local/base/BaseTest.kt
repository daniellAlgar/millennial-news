package com.algar.local.base

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.algar.local.NewsDatabase
import com.algar.local.dao.NewsDao
import com.algar.local.util.DateTimeSerializer
import com.google.gson.GsonBuilder
import org.joda.time.DateTime
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.KoinApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
abstract class BaseTest: KoinTest {

    val newsDao: NewsDao by inject()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    open fun setUp() {
        startKoin { KoinApplication.create() }
        configureDI()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    private fun configureDI() {
        loadKoinModules(configureLocalModules(context = getApplicationContext()))
    }

    private fun configureLocalModules(context: Context) = module {
        single {
            Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        }

        factory { get<NewsDatabase>().newsDao() }

        single {
            GsonBuilder()
                .registerTypeAdapter(DateTime::class.java, DateTimeSerializer.INSTANCE)
                .create()
        }
    }
}