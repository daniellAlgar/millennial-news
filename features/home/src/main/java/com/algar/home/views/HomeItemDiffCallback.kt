package com.algar.home.views

import androidx.recyclerview.widget.DiffUtil
import com.algar.model.Article

class HomeItemDiffCallback(
    private val oldList: List<Article>,
    private val newList: List<Article>?
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if (newList == null) {
            false
        } else {
            oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if (newList == null) {
            false
        } else {
            oldList[oldItemPosition].title == newList[newItemPosition].title
                    && oldList[oldItemPosition].publishedAt == newList[newItemPosition].publishedAt
        }
    }
}