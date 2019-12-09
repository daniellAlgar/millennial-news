package com.algar.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algar.model.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(articles: List<Article>)

    @Query("SELECT * FROM Article")
    suspend fun getArticles(): List<Article>
}