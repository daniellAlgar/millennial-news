package com.algar.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime

@Entity
@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: DateTime,
    val content: String?,

    /** Local variable to keep track of when an [Article] was updated */
    var lastRefreshed: DateTime?
): Parcelable

/**
 * Updates all [Article.lastRefreshed] to current date.
 */
fun List<Article>.setLastRefreshedToNow(): List<Article> {
    return apply { forEach { it.lastRefreshed = DateTime.now() }}
}