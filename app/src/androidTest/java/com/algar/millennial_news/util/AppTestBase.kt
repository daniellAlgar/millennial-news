package com.algar.millennial_news.util

import androidx.test.rule.ActivityTestRule
import com.algar.article.ArticleViewModel
import com.algar.home.HomeViewModel
import com.algar.home.domain.GetTopHeadlinesUseCase
import com.algar.millennial_news.MainActivity
import com.algar.repository.AppDispatchers
import com.algar.repository.NewsRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

/**
 * Helper class to set up e.g. [MainActivity] test rule, and Koin stub modules.
 */
open class AppTestBase : KoinTest {

    @Rule
    @JvmField
    val mainActivityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun setUp() {
        loadKoinModules(modules)
    }

    @After
    fun tearDown() {
        /* Obs: stopKoin() would be optimal here but I haven't found a way to get that to work with
         * multiple instrumentation tests. It will kill all remaining tests.
         */
        unloadKoinModules(modules)
    }

    private val appDispatchersModule = module {
        factory { AppDispatchers(main = Dispatchers.Main, io = Dispatchers.Main) }
    }
    val newsRepositoryMock = mockk<NewsRepository>()
    private val repositoryModule = module {
        single { newsRepositoryMock }
    }
    private val featureHomeModule = module {
        factory { GetTopHeadlinesUseCase(repository = get()) }
        factory { HomeViewModel(getTopHeadlinesUseCase = get(), dispatchers = get()) }
    }
    private val featureArticleModule = module {
        factory { ArticleViewModel() }
    }

    private val modules = listOf(
        featureHomeModule,
        featureArticleModule,
        repositoryModule,
        appDispatchersModule
    )
}