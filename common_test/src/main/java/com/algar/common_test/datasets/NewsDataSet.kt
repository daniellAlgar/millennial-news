package com.algar.common_test.datasets

import com.algar.model.Article
import com.algar.model.Source
import org.joda.time.DateTime

/**
 * Contains fake data that can be used in tests related to the news api.
 */
object NewsDataSet {

    /**
     * We set a fixed current time since [DateTime.now] doesn't work (properly) in unit tests.
     */
    val CURRENT_TIME = DateTime(2002, 2, 2, 2, 0)

    private val fakeSource = Source(id = null, name = "Fake News")

    private val fakeArticle = Article(
        source = fakeSource,
        author = "Mr. Fox Anchor",
        title = "Wrong facts presented as truth",
        description = "Whatever you do, however it feels or sounds like, remember that this is the utter truth.",
        url = "https://fake-news-daily.com",
        urlToImage = "https://meme-for-stupids.com/wrong-but-right-facts.jpg",
        publishedAt = CURRENT_TIME,
        content = "Charging your phone with the cord pointing downwards makes the charging go faster. True story.",
        lastRefreshed = CURRENT_TIME
    )

    /**
     * Generates a list of articles, all with a unique ID.
     */
    fun fakeArticles(count: Int = 1): ArrayList<Article> {
        val output = ArrayList<Article>()
        (0 until count).mapTo(output) {
            fakeArticle.copy(source = fakeSource.copy(id = it.toString()))
        }

        return output
    }
}