package com.fh.info.ui.fragments

import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fh.info.R
import com.fh.info.callbacks.AppInfoListener
import com.fh.info.data.model.ApplicationInfoModel
import com.fh.info.databinding.FragmentAppsBinding
import com.fh.info.ui.adapter.AdapterAllApps
import com.fh.info.utils.StatusBarHeight
import com.fh.info.viewmodel.AppsViewModel
import kotlinx.coroutines.flow.collect


class Apps : Fragment(R.layout.fragment_apps), AppInfoListener {

    private lateinit var binding: FragmentAppsBinding
    private val viewModel by viewModels<AppsViewModel>()
    private lateinit var adapterAllApps: AdapterAllApps

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAppsBinding.bind(view)

        val params = binding.searchLayout.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(
            params.leftMargin,
            StatusBarHeight.getStatusBarHeight(resources) + params.topMargin,
            params.rightMargin,
            params.bottomMargin
        )

        binding.apply {
            allAppRecyclerView.setPadding(
                allAppRecyclerView.paddingTop,
                allAppRecyclerView.paddingTop + params.topMargin + params.height + params.topMargin,
                allAppRecyclerView.paddingRight,
                allAppRecyclerView.paddingBottom
            )
        }

        setUpRecyclerView()
        getAllApps()
        searchApps()
    }

    private fun setUpRecyclerView() {
        adapterAllApps = AdapterAllApps(this)
        binding.allAppRecyclerView.apply {
            adapter = adapterAllApps

        }

    }

    private fun getAllApps() {
        lifecycleScope.launchWhenStarted {
            viewModel.appsFlow.collect {
                Log.i("TAG", "getAllApps:$it ")
//
//                for (i in it.indices) {
//                    if (it[i].isPackageInstalled(requireActivity().packageManager))
//                        viewModel.getAllApps()
//                    return@collect
//                }


                adapterAllApps.differ.submitList(it)
                Log.i("TAG", "getAllApps:$it ")
            }
        }
    }

    private fun searchApps(){
        binding.searchView.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()){
                    filterApps(s.toString())

                }else{
                    getAllApps()
                }

            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterApps(appName: String) {
        viewModel.searchApp(appName)
       lifecycleScope.launchWhenStarted {

           viewModel.searchFlow.collect {
               adapterAllApps.differ.submitList(it)
           }
       }


    }


    override fun onInfoClick(applicationInfo: ApplicationInfo) {
        val applicationInfoModel = ApplicationInfoModel(applicationInfo)
        val action = AppsDirections.actionAppsToAppInfo(applicationInfoModel)
        findNavController().navigate(action)

    }
}


