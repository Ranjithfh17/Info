package com.fh.info.data.model

import android.content.pm.ApplicationInfo
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApplicationInfoModel(val applicationInfo: ApplicationInfo):Parcelable
