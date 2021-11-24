package com.fh.info.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fh.info.utils.ApkInformation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class ExtrasViewModel(application: Application, val applicationInfo: ApplicationInfo) :
    AndroidViewModel(application) {

    private val _extraFlow = MutableStateFlow<MutableList<String>>(mutableListOf())
    val extraFlow: StateFlow<MutableList<String>>
        get() = _extraFlow

    init {
        extraFileInfo()
    }

    private fun extraFileInfo() {
        viewModelScope.launch {
            _extraFlow.value = ApkInformation.getAllExtraFiles(applicationInfo.sourceDir).apply {
                sortBy {
                    it.toLowerCase(Locale.getDefault())
                }
            }
        }
    }
}