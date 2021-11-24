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
import com.fh.info.databinding.FragmentManifestBinding
import com.fh.info.viewmodel.ManifestViewModel
import com.fh.info.viewmodelfactory.InfoViewModelProvider
import kotlinx.coroutines.flow.collect


class ManifestFrag : Fragment(R.layout.fragment_manifest) {


    private lateinit var binding:FragmentManifestBinding
    private val args by navArgs<ManifestFragArgs>()
    private val viewModel by viewModels<ManifestViewModel> {
        InfoViewModelProvider(requireActivity().application,args.applicationinfo.applicationInfo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentManifestBinding.bind(view)

        lifecycleScope.launchWhenStarted {
            viewModel.manifestFlow.collect {
                binding.manifest.text=it
            }
        }

        binding.navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}