package com.algar.home.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.algar.home.HomeViewModel
import com.algar.home.R
import com.algar.home.views.ItemType.FOOTER
import com.algar.home.views.ItemType.HEADER
import com.algar.home.views.ItemType.MIDDLE
import com.algar.model.Article
import com.algar.model.NewsResponse
import com.algar.remote.model.ApiResponse

enum class ItemType(val value: Int) {
    HEADER(0),
    MIDDLE(1),
    FOOTER(2)
}

class HomeAdapter(private val viewModel: HomeViewModel): RecyclerView.Adapter<HomeViewHolder>() {

    private val articles: MutableList<Article> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layout = when (viewType) {
            HEADER.value -> R.layout.item_home_header
            FOOTER.value -> R.layout.item_home_footer
            else -> R.layout.item_home_middle
        }
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false))
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bindTo(article = articles[position], viewType = getItemViewType(position))
    }

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> {
            HEADER.value
        }
        articles.size - 1 -> {
            FOOTER.value
        }
        else -> {
            MIDDLE.value
        }
    }

    // TODO: This is planned to receive a Resource in the future. Keeping it like this for now.
    fun updateData(items: ApiResponse<NewsResponse>?) = when (items) {
        is ApiResponse.Success -> {
            val diffCallback = HomeItemDiffCallback(oldList = articles, newList = items.body.articles)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            articles.clear()
            articles.addAll(items.body.articles)
            diffResult.dispatchUpdatesTo(this)
        }
        else -> {}
    }
}