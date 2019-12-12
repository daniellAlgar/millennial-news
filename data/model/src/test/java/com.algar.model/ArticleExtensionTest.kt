package com.algar.model

import com.algar.common_test.datasets.NewsDataSet.CURRENT_TIME
import com.algar.common_test.datasets.NewsDataSet.fakeArticles
import org.joda.time.DateTime
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

@RunWith(JUnit4::class)
class ArticleExtensionTest {

    @Test
    fun `setLastRefreshedToNow Update with current time`() {
        val article = fakeArticles(count = 1)
        val newDate = DateTime.now().setSecondsToZero()

        val updatedArticle = article.setLastRefreshedToNow()
        val dateInUpdatedArticle = updatedArticle.first().lastRefreshed?.setSecondsToZero()

        assertEquals(newDate, dateInUpdatedArticle)
    }

    @Test
    fun `shouldRefreshFromNetwork All articles are fresh so should return false`() {
        val articles = fakeArticles(count = 20)
        val minutesLessThenThreshold = dataOutdatedThreshold - 1
        val currentDate = CURRENT_TIME.plusMinutes(minutesLessThenThreshold)

        val shouldRefresh = articles.shouldRefreshFromNetwork(date = currentDate)

        assertFalse(shouldRefresh)
    }

    @Test
    fun `shouldRefreshFromNetwork All articles are old so should return true`() {
        val articles = fakeArticles(count = 20)
        val minutesMoreThenThreshold = dataOutdatedThreshold + 1
        val currentDate = CURRENT_TIME.plusMinutes(minutesMoreThenThreshold)

        val shouldRefresh = articles.shouldRefreshFromNetwork(date = currentDate)

        assertTrue(shouldRefresh)
    }

    @Test
    fun `shouldRefreshFromNetwork Some articles have time null and the rest is less then threshold so should return true`() {
        val articles = fakeArticles(count = 20)
        articles.first().lastRefreshed = null
        val minutesLessThenThreshold = dataOutdatedThreshold - 1
        val currentDate = CURRENT_TIME.plusMinutes(minutesLessThenThreshold)

        val shouldRefresh = articles.shouldRefreshFromNetwork(date = currentDate)

        assertTrue(shouldRefresh)
    }

    @Test
    fun `shouldRefreshFromNetwork Some articles have time null and the rest is more then threshold so should return true`() {
        val articles = fakeArticles(count = 20)
        articles.first().lastRefreshed = null
        val minutesMoreThenThreshold = dataOutdatedThreshold + 1
        val currentDate = CURRENT_TIME.plusMinutes(minutesMoreThenThreshold)

        val shouldRefresh = articles.shouldRefreshFromNetwork(date = currentDate)

        assertTrue(shouldRefresh)
    }

    /**
     * Since we have multiple [DateTime.now] the time will differ in milliseconds. This function
     * takes care of that for test purposes by setting seconds and milliseconds to zero on given
     * [DateTime].
     */
    private fun DateTime.setSecondsToZero(): DateTime {
        val seconds = 0
        val milliseconds = 0
        return DateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, seconds, milliseconds)
    }
}