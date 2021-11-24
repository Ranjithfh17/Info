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
import com.fh.info.databinding.FragmentServicesBinding
import com.fh.info.ui.adapter.AdapterService
import com.fh.info.viewmodel.ServiceViewModel
import com.fh.info.viewmodelfactory.ServiceViewModelProvider
import kotlinx.coroutines.flow.collect

class Services : Fragment(R.layout.fragment_services) {

    private lateinit var binding:FragmentServicesBinding
    private lateinit var adapterService:AdapterService
    private val args by navArgs<ServicesArgs>()
    private val viewModel by viewModels<ServiceViewModel> {
        ServiceViewModelProvider(args.applicationInfo.applicationInfo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentServicesBinding.bind(view)

        setUpRecyclerview()
        setServiceInfo()
        binding.navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpRecyclerview(){
        adapterService= AdapterService()
        binding.serviceRecyclerView.apply {
            adapter=adapterService
        }
    }

    private fun setServiceInfo(){
        lifecycleScope.launchWhenStarted {
            viewModel.serviceFlow.collect{
                adapterService.differ.submitList(it)
            }
        }
    }


}