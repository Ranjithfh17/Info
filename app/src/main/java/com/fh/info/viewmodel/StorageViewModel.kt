package com.fh.info.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import com.fh.info.R
import com.fh.info.data.model.Storage
import com.fh.info.utils.PackageUtil.getPackageSize
import com.fh.info.utils.getDirectoryLength
import com.fh.info.utils.getDirectorySize
import com.fh.info.utils.getFileSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class StorageViewModel(app: Application) : AndroidViewModel(app) {

    private val _storageFlow=MutableStateFlow<Storage>(Storage())
    val storageFlow:Flow<Storage>
    get() = _storageFlow


    fun getStorageInfo(applicationInfo: ApplicationInfo) {
        val context = getApplication<Application>().applicationContext

        val installedApplication =
            getApplication<Application>().packageManager.getInstalledApplications(
                PackageManager.GET_META_DATA
            ) as ArrayList

        var size=0L
        for (i in installedApplication.indices){
            size += installedApplication[i].sourceDir.getDirectoryLength()
        }

        val app=applicationInfo.sourceDir.getDirectoryLength().toDouble() / size.toDouble() *100.0
        val value=(app* (360.0 / 100.0)).toFloat()



        val packageSize = applicationInfo.getPackageSize(context)
        val apkSize = packageSize.codeSize.getFileSize("si")
        val splitSize =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && applicationInfo.splitPublicSourceDirs != null) {
                "${applicationInfo.splitPublicSourceDirs.getDirectorySize()}(${applicationInfo.splitNames.size})"
            }else{
                context.getString(R.string.not_found)
            }

        val cacheSize=packageSize.cacheSize.getFileSize("si")
        val dataSize=packageSize.dataSize.getFileSize("si")

        val storage=Storage(
            apkSize,
            splitSize,
            cacheSize,
            dataSize,
            value
        )

        _storageFlow.value=storage


    }

}