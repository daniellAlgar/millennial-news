package com.algar.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.algar.article.databinding.FragmentArticleBinding
import com.algar.common.base.BaseFragment
import com.algar.common.base.BaseViewModel
import org.koin.android.ext.android.inject

class ArticleFragment : BaseFragment() {

    private val viewModel: ArticleViewModel by inject()
    private lateinit var binding: FragmentArticleBinding
    private val args: ArticleFragmentArgs by navArgs()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.article = args.article
        return binding.root
    }
}