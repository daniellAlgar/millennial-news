package com.algar.millennial_news

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.algar.common_test.datasets.NewsDataSet
import com.algar.home.R
import com.algar.millennial_news.util.AppTestBase
import com.algar.model.Article
import com.algar.repository.utils.Resource
import io.mockk.coEvery
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeToDetailsInstrumentationTest : AppTestBase() {

    @Test
    fun homeToArticleFlow_checkCorrectContentAndToolbarTitle() {
        val itemPosition = 0
        val numberOfArticles = 20
        val stubReturnValue = Resource.success(data = NewsDataSet.fakeArticles(count = numberOfArticles))
        val stubArticle = stubReturnValue.data?.get(itemPosition)
        coEvery { newsRepositoryMock.getTopHeadlines() } returns MutableLiveData<Resource<List<Article>>>().apply {
            postValue(stubReturnValue)
        }

        mainActivityTestRule.launchActivity(null)
        onView(withId(R.id.fragment_home_recycler_view)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(itemPosition, ViewActions.click()))

        onView(withId(com.algar.article.R.id.article_content)).check(matches(withText(CoreMatchers.containsString(stubArticle?.content))))
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.action_bar)))).check(matches(withText(stubArticle?.title)))
    }
}