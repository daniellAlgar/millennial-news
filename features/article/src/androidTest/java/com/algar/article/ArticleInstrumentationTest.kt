package com.algar.article

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.algar.article.di.featureArticleModule
import com.algar.common_test.datasets.NewsDataSet
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class ArticleInstrumentationTest: KoinTest {

    private val argsMock = mockk<ArticleFragmentArgs>()

    private val modules = listOf(featureArticleModule)

    @Before
    fun setUp() {
        loadKoinModules(modules)
    }

    @After
    fun tearDown() {
        unloadKoinModules(modules)
    }

    @Test
    fun contentFromArticleIsDisplayed() {
        val fakeArticle = NewsDataSet.fakeArticles(count = 1)[0]
        val fragmentArgs = Bundle().apply {
            putParcelable("article", fakeArticle)
            putString("title", fakeArticle.title)
        }
        every { argsMock.article } returns fakeArticle

        launchFragment(fragmentArgs = fragmentArgs)

        onView(withId(R.id.article_content)).check(matches(withText(containsString(fakeArticle.content))))
    }

    /**
     * Helper methods
     */
    private fun launchFragment(fragmentArgs: Bundle? = null): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val articleScenario = launchFragmentInContainer<ArticleFragment>(
            fragmentArgs = fragmentArgs,
            themeResId = R.style.AppTheme
        )

        articleScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        return mockNavController
    }
}