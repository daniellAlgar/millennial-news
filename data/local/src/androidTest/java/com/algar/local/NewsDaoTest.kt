package com.algar.local

import com.algar.common_test.datasets.NewsDataSet.fakeArticles
import com.algar.local.base.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.assertEquals

class NewsDaoTest: BaseTest() {

    private val numberOfArticles = 20

    @Test
    fun getAllArticlesInDb() = runBlocking {
        populateDatabase()

        val articles = newsDao.getArticles()
        val numberOfReturnedArticles = articles.count()

       assertEquals(numberOfArticles, numberOfReturnedArticles)
    }

    /**
     * Helper methods
     */
    private suspend fun populateDatabase() {
        newsDao.save(articles = fakeArticles(count = numberOfArticles))
    }
}