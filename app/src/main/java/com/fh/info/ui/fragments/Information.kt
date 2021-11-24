package com.fh.info.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.fh.info.R
import com.fh.info.databinding.FragmentInformationBinding
import com.fh.info.ui.adapter.AdapterInformation
import com.fh.info.viewmodel.InformationViewModel
import kotlinx.coroutines.flow.collect


class Information : Fragment(R.layout.fragment_information) {
    private lateinit var binding:FragmentInformationBinding
    private lateinit var adapterInformation: AdapterInformation
    private val viewModel by viewModels<InformationViewModel>()
    private val args by navArgs<InformationArgs>()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentInformationBinding.bind(view)

        setApplicationInfo()
        setUpRecyclerView()
        getInformation()
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setApplicationInfo(){
        viewModel.getInformation(args.applicationInfo.applicationInfo)
    }




    private fun setUpRecyclerView(){
        adapterInformation= AdapterInformation()
        binding.informationRecyclerView.apply {
            adapter=adapterInformation
        }

    }


    private fun getInformation(){
        lifecycleScope.launchWhenStarted {
            viewModel.informationFlow.collect {
                adapterInformation.differ.submitList(it)
            }
        }

    }

}