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
import com.fh.info.databinding.FragmentExtrasBinding
import com.fh.info.ui.adapter.AdapterExtras
import com.fh.info.viewmodel.ExtrasViewModel
import com.fh.info.viewmodelfactory.InfoViewModelProvider
import kotlinx.coroutines.flow.collect


class Extras : Fragment(R.layout.fragment_extras) {

    private lateinit var binding:FragmentExtrasBinding
    private lateinit var adapterExtras: AdapterExtras
    private val args by navArgs<ExtrasArgs>()

    private val viewModel by viewModels<ExtrasViewModel> {
        InfoViewModelProvider(requireActivity().application,args.applicationInfo.applicationInfo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentExtrasBinding.bind(view)

        setUpRecyclerView()
        setExtras()
        binding.navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpRecyclerView(){
        adapterExtras= AdapterExtras()
        binding.extrasRecyclerView.apply {
            adapter=adapterExtras
        }

    }

    private fun setExtras(){
        lifecycleScope.launchWhenStarted {
            viewModel.extraFlow.collect {
                adapterExtras.differ.submitList(it)
            }
        }
    }


}