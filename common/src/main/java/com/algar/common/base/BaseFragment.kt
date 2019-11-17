package com.algar.common.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.algar.navigation.NavigationCommand
import com.algar.common.utils.Event

abstract class BaseFragment: Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeNavigation(getViewModel())
    }

    abstract fun getViewModel(): BaseViewModel

    /**
     * Observe a [NavigationCommand] [Event].
     * When this [LiveData] is updated, [Fragment] will navigate to its destination.
     */
    private fun observeNavigation(viewModel: BaseViewModel) {
        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { command ->
                when (command) {
                    is NavigationCommand.To -> findNavController().navigate(command.directions)
                    is NavigationCommand.Back -> findNavController().navigateUp()
                }
            }
        })
    }
}