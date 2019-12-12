package com.algar.model

import com.algar.common_test.datasets.NewsDataSet
import org.joda.time.DateTime
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.assertEquals

@RunWith(JUnit4::class)
class ArticleExtensionTest {

    @Test
    fun setLastRefreshedToNow() {
        val article = NewsDataSet.fakeArticles(count = 1)
        val newDate = DateTime.now().setSecondsToZero()

        val updatedArticle = article.setLastRefreshedToNow()
        val dateInUpdatedArticle = updatedArticle.first().lastRefreshed?.setSecondsToZero()

        assertEquals(newDate, dateInUpdatedArticle)
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