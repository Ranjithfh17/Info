package com.fh.info.viewmodel

import android.content.pm.ApplicationInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fh.info.data.model.UsesFeatures
import com.fh.info.utils.ApkInformation.getFeatures
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UsesFeatureViewModel(val applicationInfo: ApplicationInfo):ViewModel() {

    private val _featureFlow = MutableStateFlow<MutableList<UsesFeatures>>(mutableListOf())
    val featureFlow: Flow<MutableList<UsesFeatures>>
        get() = _featureFlow

    private val _errorFlow = MutableStateFlow("")
    val errorFlow: Flow<String>
        get() = _errorFlow

    init {
        getUsesFeaturesInfo()
    }

    private fun getUsesFeaturesInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                _featureFlow.value = applicationInfo.getFeatures().apply {
                    sortBy {
                        it.name
                    }
                }

            }.getOrElse {
                _errorFlow.value = it.localizedMessage
            }
        }
    }
}