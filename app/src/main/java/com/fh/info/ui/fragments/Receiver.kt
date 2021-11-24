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
import com.fh.info.databinding.FragmentReceiverBinding
import com.fh.info.ui.adapter.AdapterReceiver
import com.fh.info.viewmodel.ReceiversViewModel
import com.fh.info.viewmodelfactory.ReceiverViewModelProvider
import kotlinx.coroutines.flow.collect


class Receiver : Fragment(R.layout.fragment_receiver) {
    private lateinit var binding:FragmentReceiverBinding
    private lateinit var adapterReceiver:AdapterReceiver
    private val args by navArgs<ReceiverArgs>()
    private val viewModel by viewModels<ReceiversViewModel> {
        ReceiverViewModelProvider(args.applicationInfo.applicationInfo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentReceiverBinding.bind(view)
        setUpRecyclerView()
        setReceiverInfo()
        binding.navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpRecyclerView(){
        adapterReceiver= AdapterReceiver()
        binding.receiversRecyclerView.apply {
            adapter=adapterReceiver
        }
    }

    private fun setReceiverInfo(){
        lifecycleScope.launchWhenStarted {
            viewModel.receiverFlow.collect {
                adapterReceiver.differ.submitList(it)
            }
        }
    }

}