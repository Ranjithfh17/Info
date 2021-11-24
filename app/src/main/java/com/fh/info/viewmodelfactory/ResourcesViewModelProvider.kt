package com.fh.info.viewmodelfactory

import android.content.pm.ApplicationInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fh.info.viewmodel.ProviderViewModel
import com.fh.info.viewmodel.ResourcesViewModel

class ResourcesViewModelProvider(val applicationInfo: ApplicationInfo):ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ResourcesViewModel(applicationInfo = applicationInfo) as T
    }
}