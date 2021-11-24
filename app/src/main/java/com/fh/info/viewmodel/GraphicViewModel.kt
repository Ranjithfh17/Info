package com.fh.info.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fh.info.data.model.GraphicModel
import com.fh.info.utils.ApkInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class GraphicViewModel(application:Application,val applicationInfo: ApplicationInfo):AndroidViewModel(application) {

    private val _graphicsFlow = MutableStateFlow<MutableList<GraphicModel>>(mutableListOf())
    val graphicsFlow: StateFlow<MutableList<GraphicModel>>
        get() = _graphicsFlow
    init {
        getGraphicsInfo()
    }

    private fun getGraphicsInfo() {
        viewModelScope.launch {

            val graphics= ApkInformation.getAllGraphicsFiles(applicationInfo.sourceDir).apply {
                    sortBy {
                        it.toLowerCase(Locale.getDefault())
                    }
                }

            val list= mutableListOf(
                GraphicModel(applicationInfo.sourceDir,graphics)
            )
            _graphicsFlow.value=list

        }
    }
}