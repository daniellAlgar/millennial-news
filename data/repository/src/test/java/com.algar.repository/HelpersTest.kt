package com.algar.repository

import com.algar.common_test.datasets.NewsDataSet.CURRENT_TIME
import com.algar.common_test.datasets.NewsDataSet.fakeArticles
import com.algar.model.Article
import com.algar.model.dataOutdatedThreshold
import com.algar.repository.utils.Helpers.shouldRefreshFromNetwork
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HelpersTest {

    @Test
    fun `An article older than threshold should be refreshed`() {
        val moreMinutesThanThreshold = dataOutdatedThreshold + 1
        val article = rewindArticleTime(minutes = moreMinutesThanThreshold)

        val shouldRefresh = shouldRefreshFromNetwork(lastRefreshed = article.lastRefreshed, currentTime = CURRENT_TIME)

        assertTrue(shouldRefresh)
    }

    @Test
    fun `An article equal to threshold should be refreshed`() {
        val article = rewindArticleTime(minutes = dataOutdatedThreshold)

        val shouldRefresh = shouldRefreshFromNetwork(lastRefreshed = article.lastRefreshed, currentTime = CURRENT_TIME)

        assertTrue(shouldRefresh)
    }

    @Test
    fun `An article that is less old than threshold should not be refreshed`() {
        val lessMinutesThanThreshold = dataOutdatedThreshold - 1
        val article = rewindArticleTime(minutes = lessMinutesThanThreshold)

        val shouldRefresh = shouldRefreshFromNetwork(lastRefreshed = article.lastRefreshed, currentTime = CURRENT_TIME)

        assertFalse(shouldRefresh)
    }

    /**
     * Sets an [Article.lastRefreshed] time to be [minutes] earlier than [CURRENT_TIME].
     */
    private fun rewindArticleTime(minutes: Int): Article {
        val currentDateMinusMinutes = CURRENT_TIME.minusMinutes(minutes).toDateTime()
        val article = fakeArticles(count = 1).first()
        return article.copy(lastRefreshed = currentDateMinusMinutes)
    }
}