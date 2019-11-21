package com.algar.home.views

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.algar.home.databinding.ItemHomeBinding
import com.algar.model.Article

class HomeViewHolder(parent: View): RecyclerView.ViewHolder(parent) {

    private val binding = ItemHomeBinding.bind(parent)

    fun bindTo(article: Article) {
        binding.article = article
    }
}