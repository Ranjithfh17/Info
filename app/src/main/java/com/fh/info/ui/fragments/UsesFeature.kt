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
import com.fh.info.databinding.FragmentUsesFeatureBinding
import com.fh.info.ui.adapter.AdapterFeatures
import com.fh.info.viewmodel.UsesFeatureViewModel
import com.fh.info.viewmodelfactory.UsesFeatureViewModelProvider
import kotlinx.coroutines.flow.collect


class UsesFeature : Fragment(R.layout.fragment_uses_feature) {
    private lateinit var binding:FragmentUsesFeatureBinding
    private lateinit var adapterFeatures: AdapterFeatures
    private val args by navArgs<UsesFeatureArgs>()
    private val vieModel by viewModels<UsesFeatureViewModel> {
        UsesFeatureViewModelProvider(args.applicationinfo.applicationInfo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentUsesFeatureBinding.bind(view)
        setUpRecyclerview()
        setFeatureInfo()
        binding.navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpRecyclerview(){
        adapterFeatures= AdapterFeatures()
        binding.featureRecyclerView.apply {
            adapter=adapterFeatures
        }
    }

    private fun setFeatureInfo(){
        lifecycleScope.launchWhenStarted {
            vieModel.featureFlow.collect {
                adapterFeatures.differ.submitList(it)
            }
        }
    }



}