package com.fh.info.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import com.fh.info.utils.PackageUtil.getApplicationName
import com.fh.info.utils.SortApps.sortByInstallDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.stream.Collector
import java.util.stream.Collectors

class AppsViewModel(application: Application) : AndroidViewModel(application) {

    private val _appsFlow = MutableStateFlow<MutableList<ApplicationInfo>>(mutableListOf())
    val appsFlow: Flow<MutableList<ApplicationInfo>>
        get() = _appsFlow

    private val _searchFlow = MutableStateFlow<MutableList<ApplicationInfo>>(mutableListOf())
    val searchFlow: Flow<MutableList<ApplicationInfo>>
        get() = _searchFlow



    init {
        getAllApps()
    }




    @SuppressLint("QueryPermissionsNeeded")
    fun getAllApps() {
        val apps = getApplication<Application>()
            .applicationContext.packageManager
            .getInstalledApplications(PackageManager.GET_META_DATA) as MutableList


//       apps= apps.stream().filter { filter ->
//            filter.flags and ApplicationInfo.FLAG_SYSTEM != 0
//        }.collect(Collectors.toList()) as MutableList<ApplicationInfo>

//      apps=  apps.stream().filter { filter ->
//            filter.flags and ApplicationInfo.FLAG_SYSTEM == 0
//
//        }.collect(Collectors.toList()) as MutableList<ApplicationInfo>


        for (i in apps.indices) {
            apps[i].name =
                getApplicationName(getApplication<Application>().applicationContext, apps[i])
        }
        apps.sortByInstallDate(getApplication<Application>().applicationContext)
        _appsFlow.value = apps
    }




     fun searchApp(appName: String) {
        val apps =
            getApplication<Application>().applicationContext.
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA) as MutableList
        for (i in apps.indices){
            apps[i].name= getApplicationName(getApplication<Application>().applicationContext,apps[i])
        }

        val list: MutableList<ApplicationInfo> = apps.stream().filter {
            it.name.contains(appName, true) || it.packageName.contains(appName, true)
        }.collect(Collectors.toList()) as MutableList<ApplicationInfo>

        apps.sortByInstallDate(getApplication<Application>().applicationContext)

        _searchFlow.value=list


    }





}