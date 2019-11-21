package com.algar.home.views

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.algar.home.databinding.ItemHomeMiddleBinding
import com.algar.home.databinding.ItemHomeFooterBinding
import com.algar.home.databinding.ItemHomeHeaderBinding
import com.algar.home.views.ItemType.FOOTER
import com.algar.home.views.ItemType.HEADER
import com.algar.model.Article

class HomeViewHolder(private val parent: View): RecyclerView.ViewHolder(parent) {

    fun bindTo(article: Article, viewType: Int) = when (viewType) {
        HEADER.value -> {
            val bindingHeader = ItemHomeHeaderBinding.bind(parent)
            bindingHeader.article = article
        }
        FOOTER.value -> {
            val bindingFooter = ItemHomeFooterBinding.bind(parent)
            bindingFooter.article = article
        }
        else -> {
            val bindingMiddle = ItemHomeMiddleBinding.bind(parent)
            bindingMiddle.article = article
        }
    }
}