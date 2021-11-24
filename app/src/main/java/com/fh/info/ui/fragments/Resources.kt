package com.fh.info.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fh.info.R
import com.fh.info.databinding.FragmentResourcesBinding
import com.fh.info.ui.adapter.AdapterResources
import com.fh.info.viewmodel.ResourcesViewModel
import com.fh.info.viewmodelfactory.ResourcesViewModelProvider
import kotlinx.coroutines.flow.collect


class Resources : Fragment(R.layout.fragment_resources) {

    private lateinit var bindig:FragmentResourcesBinding
    private lateinit var adapterResources: AdapterResources
    private val args by navArgs<ResourcesArgs>()
    private val viewModel by viewModels<ResourcesViewModel> {
        ResourcesViewModelProvider(args.applicationInfo.applicationInfo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindig= FragmentResourcesBinding.bind(view)

        setUpRecyclerView()
        setResourcesInfo()
        bindig.navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpRecyclerView(){
        adapterResources= AdapterResources()
        bindig.resourcesRecyclerView.apply {
            adapter=adapterResources
        }

    }

    private fun setResourcesInfo(){
        lifecycleScope.launchWhenStarted {
            viewModel.resourcesFlow.collect {
                adapterResources.differ.submitList(it)
            }
        }
    }

}