package com.algar.article

import com.algar.common.base.BaseFragment
import com.algar.common.base.BaseViewModel
import org.koin.android.ext.android.inject

class ArticleFragment : BaseFragment() {

    private val viewModel: ArticleViewModel by inject()

    override fun getViewModel(): BaseViewModel = viewModel
}