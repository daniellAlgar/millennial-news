package com.algar.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.algar.model.Article

@Dao
abstract class NewsDao {

    @Transaction
    open suspend fun save(articles: List<Article>) {
        deleteAllArticles()
        insert(articles = articles)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(articles: List<Article>)

    @Query("SELECT * FROM Article")
    abstract suspend fun getArticles(): List<Article>

    @Query("DELETE FROM Article")
    abstract suspend fun deleteAllArticles()
}