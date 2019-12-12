package com.algar.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
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