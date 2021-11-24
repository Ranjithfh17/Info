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
import com.fh.info.databinding.FragmentPermissionBinding
import com.fh.info.ui.adapter.AdapterPermission
import com.fh.info.viewmodel.PermissionViewModel
import com.fh.info.viewmodelfactory.InfoViewModelProvider
import kotlinx.coroutines.flow.collect


class Permission : Fragment(R.layout.fragment_permission) {


    private lateinit var binding:FragmentPermissionBinding
    private val args by navArgs<PermissionArgs>()
    private lateinit var adapterPermission:AdapterPermission

    private val viewModel by viewModels<PermissionViewModel> {
        InfoViewModelProvider(requireActivity().application,args.applicationInfo.applicationInfo)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentPermissionBinding.bind(view)

        setUpRecyclerView()
        setPermissionInfo()
        binding.navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpRecyclerView(){
        adapterPermission= AdapterPermission()
        binding.permissionsRecyclerView.apply {
            adapter=adapterPermission
        }
    }

    private fun setPermissionInfo(){
        lifecycleScope.launchWhenStarted {
            viewModel.permissionFlow.collect{
                adapterPermission.differ.submitList(it)
            }
        }
    }

}