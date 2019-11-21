package com.algar.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.algar.common.base.BaseFragment
import com.algar.common.base.BaseViewModel
import com.algar.home.databinding.FragmentHomeBinding
import com.algar.home.views.HomeAdapter
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by inject()
    private lateinit var viewBinding: FragmentHomeBinding

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(inflater, container,false)
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = viewLifecycleOwner
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureRecyclerView()
    }

    private fun configureRecyclerView() {
        viewBinding.fragmentHomeRecyclerView.adapter = HomeAdapter(viewModel = viewModel)
    }
}