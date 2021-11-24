package com.fh.info.utils

import android.content.Context
import android.content.pm.ApplicationInfo

object SortApps {

    fun MutableList<ApplicationInfo>.sortByInstallDate(context: Context){
        this.sortByDescending {
            context.packageManager.getPackageInfo(it.packageName,0).firstInstallTime
        }

    }
}