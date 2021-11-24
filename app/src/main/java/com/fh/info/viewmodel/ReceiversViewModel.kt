package com.fh.info.viewmodel

import android.content.pm.ApplicationInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fh.info.utils.ApkInformation.getReceivers
import com.jaredrummler.apkparser.model.AndroidComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReceiversViewModel(val applicationInfo:ApplicationInfo):ViewModel() {

    private val _receiverFlow = MutableStateFlow<MutableList<AndroidComponent>>(mutableListOf())
    val receiverFlow: StateFlow<MutableList<AndroidComponent>>
        get() = _receiverFlow

    private val _errorFlow = MutableStateFlow("")
    val errorFlow: StateFlow<String>
        get() = _errorFlow

    init {
        getReceiverInfo()
    }

    private fun getReceiverInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                _receiverFlow.value = applicationInfo.getReceivers().apply {
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