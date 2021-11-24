package com.fh.info.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import com.fh.info.R
import com.fh.info.data.model.InfoItems
import com.fh.info.data.model.InfoOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppInfoViewModel(application: Application) : AndroidViewModel(application) {


    private val _optionsFlow = MutableStateFlow<MutableList<InfoOptions>>(mutableListOf())
    val optionsFlow: StateFlow<MutableList<InfoOptions>>
        get() = _optionsFlow

    private val _infoItemsFlow = MutableStateFlow<MutableList<InfoItems>>(mutableListOf())
    val infoItemsFlow: StateFlow<MutableList<InfoItems>>
        get() = _infoItemsFlow



    init {
        getInfoItems()
        getInfoOptions()

    }


    private fun getInfoOptions() {
        val list = mutableListOf(
            InfoOptions(R.drawable.icon_all_apps, "Launch"),
            InfoOptions(R.drawable.icon_all_apps, "Send"),
            InfoOptions(R.drawable.icon_all_apps, "Uninstall"),
            InfoOptions(R.drawable.icon_all_apps, "Open app settings")
        )
        _optionsFlow.value = list
    }

    private fun getInfoItems() {
        val itemList = mutableListOf(
            InfoItems(R.drawable.icon_all_apps, "Activities"),
            InfoItems(R.drawable.icon_all_apps, "Certificate"),
            InfoItems(R.drawable.icon_all_apps, "Dex Classes"),
            InfoItems(R.drawable.icon_all_apps, "Extras"),
            InfoItems(R.drawable.icon_all_apps, "Graphics"),
            InfoItems(R.drawable.icon_all_apps, "Manifest"),
            InfoItems(R.drawable.icon_all_apps, "Permissions"),
            InfoItems(R.drawable.icon_all_apps, "Providers"),
            InfoItems(R.drawable.icon_all_apps, "Resources"),
            InfoItems(R.drawable.icon_all_apps, "Receivers"),
            InfoItems(R.drawable.icon_all_apps, "Services"),
            InfoItems(R.drawable.icon_all_apps, "Uses Feature")
        )
        _infoItemsFlow.value = itemList
    }



}