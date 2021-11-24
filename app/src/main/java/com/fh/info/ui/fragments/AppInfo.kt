package com.fh.info.ui.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fh.info.R
import com.fh.info.callbacks.InfoItemListener
import com.fh.info.callbacks.InfoOptionsListener
import com.fh.info.data.model.ApplicationInfoModel
import com.fh.info.data.model.InfoItems
import com.fh.info.data.model.InfoOptions
import com.fh.info.databinding.FragmentAppInfoBinding
import com.fh.info.ui.adapter.AdapterInfoItems
import com.fh.info.ui.adapter.AdapterInfoOptions
import com.fh.info.utils.PackageUtil
import com.fh.info.utils.PackageUtil.launchApp
import com.fh.info.viewmodel.AppInfoViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

open class AppInfo : Fragment(R.layout.fragment_app_info), InfoOptionsListener, InfoItemListener {

    private lateinit var binding: FragmentAppInfoBinding
    private val viewModel by viewModels<AppInfoViewModel>()
    private lateinit var adapterInfoOptions: AdapterInfoOptions
    private lateinit var adapterInfoItem: AdapterInfoItems
    private lateinit var applicationInfoModel: ApplicationInfoModel
    private val args by navArgs<AppInfoArgs>()
    private lateinit var appUninstallActivityResultLauncher: ActivityResultLauncher<Intent>
    private var spanCount = 3

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAppInfoBinding.bind(view)

        applicationInfoModel = ApplicationInfoModel(args.applicationInfo.applicationInfo)
        Log.i("TAG", "onViewCreated:${applicationInfoModel.applicationInfo.packageName} ")

        setInfo(applicationInfo = applicationInfoModel.applicationInfo)

        spanCount =
            if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                3
            } else {
                5
            }

        setUpInfoItemRecyclerView()
        setOptions()
        setUpOptionsRecyclerView()
        setInfoItems()
        openInformation()
        openStorage()
        openDirectories()



        appUninstallActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                when (result.resultCode) {
                    Activity.RESULT_OK -> {
                        findNavController().popBackStack(R.id.home2, false)
                        Snackbar.make(binding.root, "App Uninstalled Successfully", Snackbar.LENGTH_LONG)
                            .show()
                    }
                    Activity.RESULT_CANCELED -> {
                        Snackbar.make(binding.root, "Failed to Uninstall app", Snackbar.LENGTH_LONG)
                            .show()
                    }

                }

            }
    }


    private fun setInfo(applicationInfo: ApplicationInfo) {

        binding.apply {
            Glide.with(requireContext())
                .asDrawable()
                .load(requireActivity().packageManager.getApplicationIcon(applicationInfo.packageName))
                .into(appIcon)
            appVersionName.text =
                PackageUtil.getApplicationVersion(requireContext(), applicationInfo)
            appName.text = PackageUtil.getApplicationName(requireContext(), applicationInfo)
        }


    }

    private fun setUpOptionsRecyclerView() {
        adapterInfoOptions = AdapterInfoOptions(this)
        binding.appOptionsRecyclerView.apply {
            adapter = adapterInfoOptions
            layoutManager = GridLayoutManager(requireContext(), spanCount)
        }

    }

    private fun setOptions() {
        lifecycleScope.launchWhenStarted {
            viewModel.optionsFlow.collect {
                adapterInfoOptions.differ.submitList(it)
                adapterInfoOptions.stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.ALLOW

            }
        }
    }


    private fun setUpInfoItemRecyclerView() {
        adapterInfoItem = AdapterInfoItems(this)
        binding.infoItemRecyclerView.apply {
            adapter = adapterInfoItem
            layoutManager = GridLayoutManager(requireContext(), spanCount)
        }

    }

    private fun setInfoItems() {
        lifecycleScope.launchWhenStarted {
            viewModel.infoItemsFlow.collect {
                adapterInfoItem.differ.submitList(it)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun openInformation() {
        binding.appInformation.setOnClickListener {
            val action = AppInfoDirections.actionAppInfoToInformation(applicationInfoModel)
            findNavController().navigate(action)
        }

    }

    private fun openStorage() {
        binding.storage.setOnClickListener {
            val action = AppInfoDirections.actionAppInfoToStorage2(applicationInfoModel)
            findNavController().navigate(action)
        }
    }

    private fun openDirectories() {
        binding.directories.setOnClickListener {
            val action = AppInfoDirections.actionAppInfoToDirectories2(applicationInfoModel)
            findNavController().navigate(action)
        }
    }


    override fun onInfoOptionClick(infoOptions: InfoOptions) {
        when (infoOptions.infoOptionName) {
            "Launch" -> {
                applicationInfoModel.applicationInfo.launchApp(requireActivity())

            }
            "Open app settings" -> {
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts(
                        "package",
                        applicationInfoModel.applicationInfo.packageName,
                        null
                    )
                })

            }
            "Uninstall" -> {
                val intent = Intent(Intent.ACTION_DELETE)
                intent.data =
                    Uri.parse("package:${applicationInfoModel.applicationInfo.packageName}")
                intent.putExtra(Intent.EXTRA_RETURN_RESULT, true)
                appUninstallActivityResultLauncher.launch(intent)
            }
        }

    }

    override fun onInfoItemClick(infoItems: InfoItems) {
        when (infoItems.infoName) {
            "Activities" -> {
                val action = AppInfoDirections.actionAppInfoToActivities(applicationInfoModel)
                findNavController().navigate(action)
            }
            "Certificate" -> {
                val action = AppInfoDirections.actionAppInfoToCertificate(applicationInfoModel)
                findNavController().navigate(action)
            }
            "Dex Classes" -> {
                val action = AppInfoDirections.actionAppInfoToDex(applicationInfoModel)
                findNavController().navigate(action)
            }
            "Extras" -> {
                val action = AppInfoDirections.actionAppInfoToExtras2(applicationInfoModel)
                findNavController().navigate(action)
            }
            "Graphics" -> {
                val action = AppInfoDirections.actionAppInfoToGraphics(applicationInfoModel)
                findNavController().navigate(action)
            }
            "Manifest" -> {
                val action = AppInfoDirections.actionAppInfoToManifestFrag(applicationInfoModel)
                findNavController().navigate(action)
            }
            "Permissions" -> {
                val action = AppInfoDirections.actionAppInfoToPermission(applicationInfoModel)
                findNavController().navigate(action)
            }
            "Providers" -> {
                val action = AppInfoDirections.actionAppInfoToProviders(applicationInfoModel)
                findNavController().navigate(action)
            }
            "Resources" -> {
                val action = AppInfoDirections.actionAppInfoToResources(applicationInfoModel)
                findNavController().navigate(action)
            }
            "Receivers" -> {
                val action = AppInfoDirections.actionAppInfoToReceiver(applicationInfoModel)
                findNavController().navigate(action)
            }
            "Services" -> {
                val action = AppInfoDirections.actionAppInfoToServices(applicationInfoModel)
                findNavController().navigate(action)
            }
            "Uses Feature" -> {
                val action = AppInfoDirections.actionAppInfoToUsesFeature(applicationInfoModel)
                findNavController().navigate(action)
            }
        }
    }
}