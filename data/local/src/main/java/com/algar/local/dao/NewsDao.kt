package com.algar.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.algar.model.Article
import com.algar.model.setLastRefreshedToNow

@Dao
abstract class NewsDao {

    @Transaction
    open suspend fun save(articles: List<Article>) {
        deleteAllArticles()
        insert(articles = articles.setLastRefreshedToNow())
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insert(articles: List<Article>)

    @Query("SELECT * FROM Article")
    abstract fun getArticles(): LiveData<List<Article>>

    @Query("DELETE FROM Article")
    abstract suspend fun deleteAllArticles()
}