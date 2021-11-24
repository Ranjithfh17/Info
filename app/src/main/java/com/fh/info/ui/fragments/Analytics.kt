package com.fh.info.ui.fragments

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fh.info.R
import com.fh.info.databinding.FragmentAnalyticsBinding
import com.fh.info.utils.SdkInfo
import com.fh.info.utils.getFileSize
import com.fh.info.viewmodel.AnalyticsViewModel
import com.scottyab.rootbeer.RootBeer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext


class Analytics : Fragment(R.layout.fragment_analytics) {

    private lateinit var binding: FragmentAnalyticsBinding
    private val viewModel by viewModels<AnalyticsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnalyticsBinding.bind(view)

        appInfo()
        analyticsInfo()
        ramInfo()
        sensorInfo()
    }

    private fun appInfo() {
        lifecycleScope.launchWhenStarted {
            viewModel.appsFlow.collect {
                var totalApps: Int
                var userApps = 0
                var systemApps = 0
                withContext(Dispatchers.Default) {
                    for (i in it.indices) {
                        if ((it[i].flags and ApplicationInfo.FLAG_SYSTEM) != 0) {
                            systemApps += 1

                        }
                        if ((it[i].flags and ApplicationInfo.FLAG_SYSTEM) == 0) {
                            userApps += 1

                        }
                    }

                    totalApps = it.size

                    binding.userApps.text =
                        StringBuilder().append(userApps).append("/").append(totalApps)
                    binding.systemApps.text =
                        StringBuilder().append(systemApps).append("/").append(totalApps)


                }
            }
        }
    }


    private fun analyticsInfo() {
        lifecycleScope.launchWhenStarted {

            binding.apply {
                os.text = SdkInfo.getSdkVersion(Build.VERSION.SDK_INT)
                securityUpdateDate.text = Build.VERSION.SECURITY_PATCH
                root.text = RootBeer(requireContext()).checkSuExists().toString()

            }

        }
    }

    @SuppressLint("ServiceCast")
    private fun ramInfo() {
        lifecycleScope.launchWhenStarted {
            val available: Long
            val used: Long

            withContext(Dispatchers.Default) {
                val memoryInfo = ActivityManager.MemoryInfo()
                val activityManager =
                    requireActivity().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                activityManager.getMemoryInfo(memoryInfo)

                available = memoryInfo.totalMem
                used = memoryInfo.totalMem - memoryInfo.availMem

                binding.apply {
                    totalRam.text = available.getFileSize("si")
                    usedRam.text = used.getFileSize("si")
                }


            }

        }
    }

    private fun sensorInfo() {
        val sensorManager =
            requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getSensorList(Sensor.TYPE_ALL)

        for (s in sensor.indices) {
            if (s == 0) {
                binding.sensor.text = sensor[s].name
            } else {
                binding.sensor.text = java.lang.StringBuilder().append(binding.sensor.text).append("\n").append(sensor[s].name)
            }
        }

    }


}