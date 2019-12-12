package com.algar.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import java.util.concurrent.TimeUnit.MILLISECONDS

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

/**
 * We consider an article to be outdated when its [Article.lastRefreshed] is older than
 * [dataOutdatedThreshold] and/or [Article.lastRefreshed] == null.
 *
 * @param date Should only be used in unit tests.
 */
fun List<Article>.shouldRefreshFromNetwork(date: DateTime = DateTime.now()): Boolean {
    // This block is not necessary but it adds a layer of speed optimisation and future safety.
    if (any { it.lastRefreshed == null }) {
        return true
    }

    val diffGreaterThenThreshold = map {
        val articleTimeInMillis = it.lastRefreshed?.millis ?: 0
        MILLISECONDS.toMinutes(date.millis - articleTimeInMillis) >= dataOutdatedThreshold
    }

    return diffGreaterThenThreshold.contains(true)
}