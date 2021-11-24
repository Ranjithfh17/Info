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
import com.fh.info.databinding.FragmentGraphicsBinding
import com.fh.info.ui.adapter.AdapterGraphics
import com.fh.info.viewmodel.GraphicViewModel
import com.fh.info.viewmodelfactory.InfoViewModelProvider
import kotlinx.coroutines.flow.collect


class Graphics : Fragment(R.layout.fragment_graphics) {
    private lateinit var binding: FragmentGraphicsBinding
    private lateinit var adapterGraphics: AdapterGraphics
    private val args by navArgs<GraphicsArgs>()
    private val viewModel by viewModels<GraphicViewModel> {
        InfoViewModelProvider(requireActivity().application,args.applicationInfo.applicationInfo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentGraphicsBinding.bind(view)

        setUpRecyclerView()
        setGraphics()
    }

    private fun setUpRecyclerView(){
        adapterGraphics= AdapterGraphics()
        binding.graphicsRecyclerView.apply {
            adapter=adapterGraphics
        }
    }

    private fun setGraphics(){
        lifecycleScope.launchWhenStarted {
            viewModel.graphicsFlow.collect {
                adapterGraphics.differ.submitList(it)
            }
        }
    }

}