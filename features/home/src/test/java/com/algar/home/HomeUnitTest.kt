package com.algar.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.algar.common_test.datasets.NewsDataSet.fakeSuccessfulApiResponse
import com.algar.home.domain.GetTopHeadlinesUseCase
import com.algar.model.NewsResponse
import com.algar.remote.model.ApiResponse
import com.algar.repository.AppDispatchers
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeUnitTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var getTopHeadlinesUseCase: GetTopHeadlinesUseCase
    private lateinit var homeViewModel: HomeViewModel
    private val stubDispatchers = AppDispatchers(
        main = Dispatchers.Unconfined,
        io = Dispatchers.Unconfined
    )

    @Before
    fun setUp() {
        getTopHeadlinesUseCase = mockk()
    }

    @Test
    fun `Top headlines requested when ViewModel is created`() {
        val observer = mockk<Observer<ApiResponse<NewsResponse>>>(relaxed = true)
        val result = fakeSuccessfulApiResponse(numberOfArticles = 20)
        coEvery { getTopHeadlinesUseCase() } returns MutableLiveData<ApiResponse<NewsResponse>>().apply {
            value = result
        }

        homeViewModel = HomeViewModel(
            getTopHeadlinesUseCase = getTopHeadlinesUseCase,
            dispatchers = stubDispatchers
        )
        homeViewModel.topHeadlines.observeForever(observer)
        verify {
            observer.onChanged(result)
        }

        confirmVerified(observer)
    }
}