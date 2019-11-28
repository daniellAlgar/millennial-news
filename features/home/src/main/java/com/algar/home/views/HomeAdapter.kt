package com.algar.home.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.algar.home.HomeViewModel
import com.algar.home.R
import com.algar.model.Article
import com.algar.repository.utils.Resource

class HomeAdapter(private val viewModel: HomeViewModel): RecyclerView.Adapter<HomeViewHolder>() {

    private val articles: MutableList<Article> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false))
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bindTo(article = articles[position], viewModel = viewModel)
    }

    // TODO: This is planned to receive a Resource in the future. Keeping it like this for now.
    fun updateData(items: Resource<List<Article>>?) = when (items?.status) {
        Resource.Status.SUCCESS -> {
            val diffCallback = HomeItemDiffCallback(oldList = articles, newList = items.data)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            articles.clear()
            items.data?.let { articles.addAll(it) }
            diffResult.dispatchUpdatesTo(this)
        }
        else -> {}  // No error handling at the moment
    }
}