package com.algar.article.di

import com.algar.article.ArticleViewModel
import org.koin.dsl.module

val featureArticleModule = module {
    factory { ArticleViewModel() }
}