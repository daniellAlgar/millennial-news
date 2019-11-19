package com.algar.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.algar.common.base.BaseFragment
import com.algar.common.base.BaseViewModel
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by inject()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}