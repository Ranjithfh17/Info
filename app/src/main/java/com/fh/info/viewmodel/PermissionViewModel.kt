package com.fh.info.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fh.info.data.model.PermissionInfo
import com.fh.info.utils.ApkInformation.getPermissions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class PermissionViewModel(application:Application,val applicationInfo: ApplicationInfo):AndroidViewModel(application) {



    private val _errorFlow = MutableStateFlow("")
    val errorFlow: Flow<String>
        get() = _errorFlow

    private val _permissionFlow =
        MutableStateFlow<MutableList<PermissionInfo>>(mutableListOf())
    val permissionFlow: StateFlow<MutableList<PermissionInfo>>
        get() = _permissionFlow

    init {
        getPermissionInfo()
    }


    private fun getPermissionInfo() {

        viewModelScope.launch {
            kotlin.runCatching {
                val permissionList = applicationInfo.getPermissions()
                val packageInfo = getApplication<Application>().packageManager.getPackageInfo(
                    applicationInfo.packageName,
                    PackageManager.GET_PERMISSIONS
                )
                val permission = ArrayList<PermissionInfo>()
                for (i in permissionList.indices) {
                    for (j in packageInfo.requestedPermissions.indices) {
                        if (permissionList[i] == packageInfo.requestedPermissions[j]) {
                            if (packageInfo.requestedPermissionsFlags[j] and PackageInfo.REQUESTED_PERMISSION_GRANTED != 0) {
                                permission.add(
                                    com.fh.info.data.model.PermissionInfo(
                                        permissionList[i],
                                        true
                                    )
                                )
                            } else {
                                permission.add(
                                    com.fh.info.data.model.PermissionInfo(
                                        permissionList[i],
                                        false
                                    )
                                )

                            }
                        }

                    }
                }

                _permissionFlow.value = permission.apply {
                    sortBy {
                        it.permissionName.toLowerCase(Locale.getDefault())
                    }
                }


            }.getOrElse {
                _errorFlow.value = it.localizedMessage
            }
        }


    }
}