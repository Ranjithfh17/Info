package com.fh.info.callbacks

import android.content.pm.ApplicationInfo

interface AppInfoListener {
    fun onInfoClick(applicationInfo: ApplicationInfo)
}