package com.fh.info.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import com.fh.info.R
import com.fh.info.data.model.Options
import com.fh.info.utils.PackageUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.stream.Collectors

class OptionsViewModel(app: Application) : AndroidViewModel(app) {

    private var list: MutableList<Options> = mutableListOf()


    private val _optionsFlow = MutableStateFlow<MutableList<Options>>(mutableListOf())
    val optionsFlow: StateFlow<MutableList<Options>>
        get() = _optionsFlow

    private val _recentlyInstalledFlow = MutableStateFlow<MutableList<PackageInfo>>(mutableListOf())
    val recentlyInstalledFlow: StateFlow<MutableList<PackageInfo>>
        get() = _recentlyInstalledFlow

    private val _recentlyUpdatedFlow = MutableStateFlow<MutableList<PackageInfo>>(mutableListOf())
    val recentlyUpdatedFlow: StateFlow<MutableList<PackageInfo>>
        get() = _recentlyUpdatedFlow


    init {
        setOptionsList()
        recentlyUpdatedApps()
        recentlyInstalledApps()
    }


    private fun setOptionsList() {
        list.add(Options(R.drawable.icon_all_apps, "All Apps"))
        list.add(Options(R.drawable.icon_all_apps, "Analytics"))
        list.add(Options(R.drawable.icon_all_apps, "Settings"))
        list.add(Options(R.drawable.icon_all_apps, "Search"))

        _optionsFlow.value = list
    }

    private fun recentlyInstalledApps(){

        var recentApps=getApplication<Application>().applicationContext.packageManager.getInstalledPackages(PackageManager.GET_META_DATA) as MutableList

        recentApps=recentApps.stream().filter {
            it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0
        }.collect(Collectors.toList()) as MutableList

        for (i in recentApps.indices){
            recentApps[i].applicationInfo.name= PackageUtil.getApplicationName(
                getApplication<Application>().applicationContext,
                recentApps[i].applicationInfo
            )
        }

        recentApps.sortByDescending {
            it.firstInstallTime
        }
        _recentlyInstalledFlow.value=recentApps

    }

    private fun recentlyUpdatedApps(){

        var updatedApps=getApplication<Application>().applicationContext.packageManager.getInstalledPackages(PackageManager.GET_META_DATA) as MutableList

        updatedApps=updatedApps.stream().filter {
            it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0
        }.collect(Collectors.toList()) as MutableList

        for (i in updatedApps.indices){
            updatedApps[i].applicationInfo.name= PackageUtil.getApplicationName(
                getApplication<Application>().applicationContext,
                updatedApps[i].applicationInfo
            )
        }

        updatedApps.sortByDescending {
            it.firstInstallTime
        }
        _recentlyUpdatedFlow.value=updatedApps

    }





}