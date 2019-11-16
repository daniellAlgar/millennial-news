package com.algar.model

import java.util.ArrayList

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: ArrayList<Article>
)
