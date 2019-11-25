package com.algar.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.algar.common_test.datasets.NewsDataSet
import com.algar.common_test.espresso.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.algar.home.di.featureHomeModule
import com.algar.model.NewsResponse
import com.algar.remote.model.ApiResponse
import com.algar.repository.AppDispatchers
import com.algar.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class HomeInstrumentationTest: KoinTest {

    private val newsRepositoryMock = mockk<NewsRepository>()
    private val testDispatchers = AppDispatchers(main = Dispatchers.Main, io = Dispatchers.Main)

    @Before
    fun setUp() {
        loadKoinModules(featureHomeModule, module {
            factory { testDispatchers }
            factory { newsRepositoryMock }
        })
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun recyclerViewContainsAllItemsFedToIt() {
        val numberOfArticles = 20
        coEvery { newsRepositoryMock.getTopHeadlines() } returns MutableLiveData<ApiResponse<NewsResponse>>().apply {
            postValue(NewsDataSet.fakeSuccessfulApiResponse(numberOfArticles = numberOfArticles))
        }
        launchFragment()

        onView(withId(R.id.fragment_home_recycler_view)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.fragment_home_recycler_view)).check(withItemCount(expectedCount = numberOfArticles))
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