package com.fh.info.viewmodelfactory

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fh.info.viewmodel.*
import java.lang.Exception
import java.lang.IllegalArgumentException

class InfoViewModelProvider(
    private val application: Application,
    private val applicationInfo: ApplicationInfo
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(InfoItemViewModel::class.java) ->{
                InfoItemViewModel(application,applicationInfo) as T
            }

            modelClass.isAssignableFrom(ExtrasViewModel::class.java) ->{
                ExtrasViewModel(application, applicationInfo) as T
            }

            modelClass.isAssignableFrom(GraphicViewModel::class.java) ->{
                GraphicViewModel(application, applicationInfo) as T
            }
            modelClass.isAssignableFrom(ManifestViewModel::class.java) ->{
                ManifestViewModel(application, applicationInfo) as T
            }
            modelClass.isAssignableFrom(PermissionViewModel::class.java) ->{
                PermissionViewModel(application, applicationInfo) as T
            }

            else ->{
                throw IllegalArgumentException("Wrong viewModel")
            }

        }
    }
}
