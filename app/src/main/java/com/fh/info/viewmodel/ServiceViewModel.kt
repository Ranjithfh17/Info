package com.fh.info.viewmodel

import android.content.pm.ApplicationInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fh.info.utils.ApkInformation.getServices
import com.jaredrummler.apkparser.model.AndroidComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServiceViewModel(val applicationInfo: ApplicationInfo) :ViewModel(){

    private val _serviceFlow = MutableStateFlow<MutableList<AndroidComponent>>(mutableListOf())
    val serviceFlow: StateFlow<MutableList<AndroidComponent>>
        get() = _serviceFlow

    private val _errorFlow = MutableStateFlow("")
    val errorFlow: StateFlow<String>
        get() = _errorFlow

    init {
        getServicesInfo()
    }

    private fun getServicesInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                _serviceFlow.value = applicationInfo.getServices().apply {
                    sortedBy {
                        it.name.substring(it.name.lastIndexOf("."))
                    }
                }

            }.getOrElse {
                _errorFlow.value = it.localizedMessage
            }
        }
    }
}