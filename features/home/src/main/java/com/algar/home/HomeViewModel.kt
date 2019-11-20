package com.algar.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.algar.common.base.BaseViewModel
import com.algar.home.domain.GetTopHeadlinesUseCase
import com.algar.model.NewsResponse
import com.algar.remote.model.ApiResponse
import com.algar.repository.AppDispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val dispatchers: AppDispatchers
) : BaseViewModel() {

    private val _topHeadlines = MediatorLiveData<ApiResponse<NewsResponse>>()
    val topHeadlines: LiveData<ApiResponse<NewsResponse>> = _topHeadlines
    private var topHeadlinesSource: LiveData<ApiResponse<NewsResponse>> = MutableLiveData()

    init {
        getTopHeadlines()
    }

    private fun getTopHeadlines() = viewModelScope.launch(dispatchers.main) {
        _topHeadlines.removeSource(topHeadlinesSource)
        withContext(dispatchers.io) {
            topHeadlinesSource = getTopHeadlinesUseCase()
        }
        _topHeadlines.addSource(topHeadlinesSource) {
            _topHeadlines.value = it
        }
    }
}