package com.algar.local

import com.algar.common_test.datasets.NewsDataSet.fakeArticles
import com.algar.local.base.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals

class NewsDaoTest: BaseTest() {

    private val numberOfArticles = 20

    @Test
    fun getAllArticlesInDb() = runBlocking {
        populateDatabase()

        val articles = newsDao.getArticles()
        val numberOfReturnedArticles = articles.count()

       assertEquals(numberOfArticles, numberOfReturnedArticles)
    }

    @Test
    fun addingArticlesShouldDeleteTheOldOnes() = runBlocking {
        populateDatabase()
        populateDatabase()

        val articles = newsDao.getArticles()
        val numberOfReturnedArticles = articles.count()

        assertEquals(numberOfArticles, numberOfReturnedArticles)
    }

    @Test
    fun savingAnArticleWillUpdateItsLastRefreshedDate() = runBlocking {
        val article = fakeArticles(count = 1)
        val lastRefreshed = article.first().lastRefreshed

        newsDao.save(articles = article)
        val updatedArticle = newsDao.getArticles()
        val updatedArticleLastRefreshed = updatedArticle.first().lastRefreshed

        assertNotEquals(lastRefreshed, updatedArticleLastRefreshed)
    }

    /**
     * Helper methods
     */
    private suspend fun populateDatabase() {
        newsDao.save(articles = fakeArticles(count = numberOfArticles))
    }
}