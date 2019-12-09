package com.algar.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.algar.local.converter.Converters
import com.algar.local.dao.NewsDao
import com.algar.model.Article

@Database(
    version = 1,
    exportSchema = false,
    entities = [Article::class]
)
@TypeConverters(Converters::class)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, NewsDatabase::class.java, "MillennialNews")
                .build()
    }
}