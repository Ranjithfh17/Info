package com.fh.info.viewmodelfactory

import android.content.pm.ApplicationInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fh.info.viewmodel.ProviderViewModel
import com.fh.info.viewmodel.ResourcesViewModel
import com.fh.info.viewmodel.ServiceViewModel
import com.fh.info.viewmodel.UsesFeatureViewModel

class UsesFeatureViewModelProvider(val applicationInfo: ApplicationInfo):ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsesFeatureViewModel(applicationInfo = applicationInfo) as T
    }
}