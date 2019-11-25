package com.algar.home.domain

import androidx.lifecycle.LiveData
import com.algar.model.Article
import com.algar.repository.NewsRepository
import com.algar.repository.utils.Resource

class GetTopHeadlinesUseCase(private val repository: NewsRepository) {

    suspend operator fun invoke(): LiveData<Resource<List<Article>>> = repository.getTopHeadlines()
}