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
import com.fh.info.databinding.FragmentProvidersBinding
import com.fh.info.ui.adapter.AdapterProviders
import com.fh.info.viewmodel.ProviderViewModel
import com.fh.info.viewmodelfactory.ProviderViewModelFactory
import kotlinx.coroutines.flow.collect


class Providers : Fragment(R.layout.fragment_providers) {

    private lateinit var binding:FragmentProvidersBinding
    private lateinit var adapterProviders: AdapterProviders
    private val args by navArgs<ProvidersArgs>()

    private val viewModel by viewModels<ProviderViewModel> {
        ProviderViewModelFactory(args.applicationInfo.applicationInfo)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentProvidersBinding.bind(view)

        setUpRecyclerview()
        setProvidersInfo()
        binding.navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpRecyclerview(){
        adapterProviders= AdapterProviders()
        binding.providersRecyclerView.apply {
            adapter=adapterProviders
        }
    }

    private fun setProvidersInfo(){
        lifecycleScope.launchWhenStarted {
            viewModel.providersFlow.collect {
                adapterProviders.differ.submitList(it)
            }
        }
    }

}