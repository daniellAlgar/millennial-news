package com.algar.home.views

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
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
}