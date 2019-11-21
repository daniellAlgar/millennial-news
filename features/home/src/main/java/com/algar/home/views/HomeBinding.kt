package com.algar.home.views

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.algar.model.NewsResponse
import com.algar.remote.model.ApiResponse

object HomeBinding {

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, resource: ApiResponse<NewsResponse>?) {
        with(recyclerView.adapter as HomeAdapter) {
            updateData(items = resource)
        }
    }
}