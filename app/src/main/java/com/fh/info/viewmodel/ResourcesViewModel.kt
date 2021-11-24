package com.fh.info.viewmodel

import android.content.pm.ApplicationInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fh.info.utils.ApkInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class ResourcesViewModel(val applicationInfo: ApplicationInfo) : ViewModel() {

    private val _resourcesFlow = MutableStateFlow<MutableList<String>>(mutableListOf())
    val resourcesFlow: StateFlow<MutableList<String>>
        get() = _resourcesFlow

    init {
        getResourcesInfo()
    }

    private fun getResourcesInfo() {
        viewModelScope.launch {
            _resourcesFlow.value = ApkInformation.getXmlFiles(applicationInfo.sourceDir).apply {
                sortedBy {
                    it.toLowerCase(Locale.getDefault())

                }
            }
        }
    }
}