package com.fh.info.ui.fragments

import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fh.info.R
import com.fh.info.callbacks.OptionsItemListener
import com.fh.info.data.model.Options
import com.fh.info.databinding.FragmentHomeBinding
import com.fh.info.ui.adapter.AdapterOptions
import com.fh.info.ui.adapter.AdapterRecentlyInstalledApp
import com.fh.info.ui.adapter.AdapterRecentlyUpdatedApp
import com.fh.info.viewmodel.OptionsViewModel
import kotlinx.coroutines.flow.collect


class Home : Fragment(R.layout.fragment_home),OptionsItemListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterOptions: AdapterOptions
    private val viewModel by viewModels<OptionsViewModel>()
    private lateinit var adapterRecentlyInstalledApp: AdapterRecentlyInstalledApp
    private lateinit var adapterRecentlyUpdatedApp: AdapterRecentlyUpdatedApp


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentHomeBinding.bind(view)
        setUpRecyclerView()
        setUpRecentlyInstalledRecyclerView()
        setUpRecentlyUpdatedRecyclerView()
        setOptionsList()
        getRecentlyInstalledApps()
        getRecentlyUpdatedApps()
    }



    private fun setUpRecyclerView(){
        adapterOptions= AdapterOptions(this)
        binding.optionsRecyclerView.apply {
            adapter=adapterOptions
            layoutManager=GridLayoutManager(requireContext(),3)
        }

    }

    private fun setUpRecentlyInstalledRecyclerView(){
        adapterRecentlyInstalledApp= AdapterRecentlyInstalledApp()
        binding.recentlyInstalledRecyclerView.apply {
            adapter=adapterRecentlyInstalledApp
        }

    }
    private fun setUpRecentlyUpdatedRecyclerView(){
        adapterRecentlyUpdatedApp= AdapterRecentlyUpdatedApp()
        binding.recentlyUpdatedRecyclerView.apply {
            adapter=adapterRecentlyUpdatedApp
        }

    }


    private fun setOptionsList(){
        lifecycleScope.launchWhenStarted {
            viewModel.optionsFlow.collect {
                adapterOptions.differ.submitList(it)
            }
        }
    }

    private fun getRecentlyInstalledApps(){
        lifecycleScope.launchWhenStarted {
            viewModel.recentlyInstalledFlow.collect {
                Log.i("TAG", "getRecentlyInstalledApps:$it ")
                adapterRecentlyInstalledApp.differ.submitList(it)
            }
        }
    }

    private fun getRecentlyUpdatedApps(){
        lifecycleScope.launchWhenStarted {
            viewModel.recentlyUpdatedFlow.collect {
                adapterRecentlyUpdatedApp.differ.submitList(it)
            }
        }
    }

    override fun onOptionItemClick(options: Options) {
        when(options.optionName){
            "All Apps" ->{
                findNavController().navigate(R.id.apps)

            }
            "Analytics" ->{
                findNavController().navigate(R.id.analytics)
            }
        }
    }
}