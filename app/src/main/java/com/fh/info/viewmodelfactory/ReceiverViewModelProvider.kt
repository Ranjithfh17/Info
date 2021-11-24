package com.fh.info.viewmodelfactory

import android.content.pm.ApplicationInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fh.info.viewmodel.ProviderViewModel
import com.fh.info.viewmodel.ReceiversViewModel
import com.fh.info.viewmodel.ResourcesViewModel

class ReceiverViewModelProvider(val applicationInfo: ApplicationInfo):ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReceiversViewModel(applicationInfo = applicationInfo) as T
    }
}