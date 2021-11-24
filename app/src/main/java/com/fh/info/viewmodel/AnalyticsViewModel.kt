package com.fh.info.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AnalyticsViewModel(app:Application):AndroidViewModel(app) {

    private val _appsFlow = MutableStateFlow<MutableList<ApplicationInfo>>(mutableListOf())
    val appsFlow: StateFlow<MutableList<ApplicationInfo>>
        get() = _appsFlow

    init {
        getTotalApps()
    }

    private fun getTotalApps() {
        val apps =
            getApplication<Application>().packageManager.
            getInstalledApplications(PackageManager.GET_META_DATA) as MutableList
        _appsFlow.value = apps
    }
}