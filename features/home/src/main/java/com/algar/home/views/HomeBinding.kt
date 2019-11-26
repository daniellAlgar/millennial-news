package com.algar.home.views

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.algar.model.Article
import com.algar.repository.utils.Resource

object HomeBinding {

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, resource: Resource<List<Article>>?) {
        with(recyclerView.adapter as HomeAdapter) {
            updateData(items = resource)
        }
    }

    @BindingAdapter("app:showWhenLoading")
    @JvmStatic
    fun <T>showWhenLoading(view: SwipeRefreshLayout, resource: Resource<T>?) {
        resource?.let {
            view.isRefreshing = resource.status == Resource.Status.LOADING
        }
    }
}