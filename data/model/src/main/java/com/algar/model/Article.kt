package com.algar.model

import org.joda.time.DateTime

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: DateTime,
    val content: String?
)