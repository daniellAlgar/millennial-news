package com.algar.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.algar.common_test.datasets.NewsDataSet.fakeArticles
import com.algar.common_test.espresso.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.algar.home.di.featureHomeModule
import com.algar.model.Article
import com.algar.repository.AppDispatchers
import com.algar.repository.NewsRepository
import com.algar.repository.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.junit.Assert.assertFalse

@RunWith(AndroidJUnit4::class)
class HomeInstrumentationTest: KoinTest {

    private val newsRepositoryMock = mockk<NewsRepository>()

    private val modules = listOf(featureHomeModule, module {
        factory { AppDispatchers(main = Dispatchers.Main, io = Dispatchers.Main) }
        factory { newsRepositoryMock }
    })

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

    @Test
    fun recyclerViewContainsAllItemsFedToIt() {
        val numberOfArticles = 20
        val stubReturnValue = Resource.success(data = fakeArticles(count = numberOfArticles))
        coEvery { newsRepositoryMock.getTopHeadlines() } returns MutableLiveData<Resource<List<Article>>>().apply {
            postValue(stubReturnValue)
        }
        launchFragment()

        onView(withId(R.id.fragment_home_recycler_view)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withId(R.id.fragment_home_recycler_view)).check(withItemCount(expectedCount = numberOfArticles))
    }

    @Test
    fun pullToRefreshFetchesNewData() {
        val onCreateReturnValue = Resource.success(data = fakeArticles(count = 10))
        coEvery { newsRepositoryMock.getTopHeadlines() } returns MutableLiveData<Resource<List<Article>>>().apply {
            postValue(onCreateReturnValue)
        }
        launchFragment()

        val onRefreshReturnValue = Resource.success(data = fakeArticles(count = 20))
        coEvery { newsRepositoryMock.getTopHeadlines() } returns MutableLiveData<Resource<List<Article>>>().apply {
            postValue(onRefreshReturnValue)
        }
        onView(withId(R.id.fragment_hope_swipe_to_refresh)).perform(swipeDown())
        onView(withId(R.id.fragment_home_recycler_view)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withId(R.id.fragment_home_recycler_view)).check(withItemCount(expectedCount = 20))
        onView(withId(R.id.fragment_hope_swipe_to_refresh)).check { view, _ ->
            assertFalse("This view shouldn't show a refresh progress at the moment.", (view as SwipeRefreshLayout).isRefreshing)
        }
    }

    /**
     * Helper methods
     */
    private fun launchFragment(): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val homeScenario = launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)

        homeScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        return mockNavController
    }
}