package com.fh.info.viewmodel

import android.content.pm.ApplicationInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fh.info.utils.ApkInformation.getProviders
import com.jaredrummler.apkparser.model.AndroidComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProviderViewModel(val applicationInfo: ApplicationInfo):ViewModel() {

    private val _providersFlow = MutableStateFlow<MutableList<AndroidComponent>>(mutableListOf())
    val providersFlow: StateFlow<MutableList<AndroidComponent>>
        get() = _providersFlow

    private val _errorFlow = MutableStateFlow("")
    val errorFlow: Flow<String>
        get() = _errorFlow

    init {
        getProviderInfo()
    }

    private fun getProviderInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                _providersFlow.value = applicationInfo.getProviders().apply {
                    sortBy {
                        it.name.substring(it.name.lastIndexOf("."))
                    }
                }

            }.getOrElse {
                _errorFlow.value = it.localizedMessage
            }
        }

    }
}