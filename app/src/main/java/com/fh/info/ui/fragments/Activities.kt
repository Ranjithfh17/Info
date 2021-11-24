package com.fh.info.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fh.info.R
import com.fh.info.databinding.FragmentActivitiesBinding
import com.fh.info.ui.adapter.AdapterActivities
import com.fh.info.utils.AppResource
import com.fh.info.viewmodel.InfoItemViewModel
import com.fh.info.viewmodelfactory.InfoViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect


class Activities : Fragment(R.layout.fragment_activities) {

    private lateinit var binding: FragmentActivitiesBinding
    private lateinit var adapterActivities: AdapterActivities
    private val args by navArgs<ActivitiesArgs>()
    private lateinit var viewModel: InfoItemViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentActivitiesBinding.bind(view)

        val infoViewModelProvider = InfoViewModelProvider(requireActivity().application, args.applicationInfo.applicationInfo)
        viewModel = ViewModelProvider(this, infoViewModelProvider).get(InfoItemViewModel::class.java)

        setUpRecyclerView()
        getActivityInfo()

        binding.navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun setUpRecyclerView() {
        adapterActivities = AdapterActivities()
        binding.activitiesRecyclerView.apply {
            adapter = adapterActivities
        }
    }

    private fun getActivityInfo() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.activityFlow.collect { result->
                when(result){
                    is AppResource.Success ->{
                        adapterActivities.differ.submitList(result.data)
                    }
                    is AppResource.Error ->{
                        Snackbar.make(binding.root,"Failed to get Activity Info",Snackbar.LENGTH_LONG).show()
                    }
                    else -> {
                        /*NO-OP*/
                    }
                }

            }
        }

    }

}