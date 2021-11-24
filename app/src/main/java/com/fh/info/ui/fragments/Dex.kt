package com.fh.info.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fh.info.R
import com.fh.info.databinding.FragmentDexBinding
import com.fh.info.ui.adapter.AdapterCertificate
import com.fh.info.ui.adapter.AdapterDex
import com.fh.info.utils.AppResource
import com.fh.info.viewmodel.InfoItemViewModel
import com.fh.info.viewmodelfactory.InfoViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect


class Dex : Fragment(R.layout.fragment_dex) {
    private lateinit var binding:FragmentDexBinding
    private lateinit var adapterDex: AdapterDex
    private lateinit var viewModel:InfoItemViewModel
    private val args by navArgs<DexArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentDexBinding.bind(view)

        val infoViewModelProvider=InfoViewModelProvider(requireActivity().application,args.applicationinfo.applicationInfo)
        viewModel=ViewModelProvider(this,infoViewModelProvider).get(InfoItemViewModel::class.java)

        setUpRecyclerview()
        setDex()
        binding.navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun setUpRecyclerview(){
        adapterDex= AdapterDex()
        binding.dexRecyclerView.apply {
            adapter=adapterDex
        }
    }

    private fun setDex(){
        lifecycleScope.launchWhenStarted {
            viewModel.dexFlow.collect { dex ->
                when(dex){
                    is AppResource.Success ->{
                        adapterDex.differ.submitList(dex.data)
                    }
                    is AppResource.Error ->{
                        Snackbar.make(binding.root,"Failed to get Dex classes",Snackbar.LENGTH_LONG).show()
                    }
                    else->{
                        /*NO-OP*/
                    }
                }

            }
        }
    }


}