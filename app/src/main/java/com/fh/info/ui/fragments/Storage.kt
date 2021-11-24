package com.fh.info.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.fh.info.R
import com.fh.info.databinding.FragmentStorageBinding
import com.fh.info.viewmodel.StorageViewModel
import kotlinx.coroutines.flow.collect

class Storage : Fragment(R.layout.fragment_storage) {
    private lateinit var binding:FragmentStorageBinding
    private val viewModel by viewModels<StorageViewModel>()
    private val args by navArgs<StorageArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentStorageBinding.bind(view)

        setApplicationInfo()
        getInfo()


    }

    private fun setApplicationInfo(){
        viewModel.getStorageInfo(args.applicationInfo.applicationInfo)
    }

    private fun getInfo(){
        lifecycleScope.launchWhenStarted {
            viewModel.storageFlow.collect { storage ->
                binding.apply {
                    apk.text=storage.packageSize
                    splitPackage.text=storage.splitPackageSize
                    cache.text=storage.cache
                    data.text=storage.data
                    usage.text=storage.usage.toString()
                }
            }
        }
    }

}