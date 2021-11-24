package com.fh.info.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import com.fh.info.R
import com.fh.info.data.model.Information
import com.fh.info.utils.ApkInformation.getApkMetaData
import com.fh.info.utils.ApkInformation.getDexData
import com.fh.info.utils.ApkInformation.getGlEsVersion
import com.fh.info.utils.PackageUtil
import com.fh.info.utils.PackageUtil.getApplicationInstalledTime
import com.fh.info.utils.PackageUtil.getApplicationUpdateTime
import com.fh.info.utils.SdkInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import net.dongliu.apk.parser.bean.ApkMeta
import java.text.NumberFormat

@RequiresApi(Build.VERSION_CODES.Q)
class InformationViewModel(app: Application) :
    AndroidViewModel(app) {
    private val _informationFlow = MutableStateFlow<MutableList<Information>>(mutableListOf())
    val informationFlow: Flow<MutableList<Information>>
        get() = _informationFlow

    @RequiresApi(Build.VERSION_CODES.Q)
     fun getInformation(applicationInfo: ApplicationInfo) {
        val context = getApplication<Application>().applicationContext
        val packageInfo = context.packageManager.getPackageInfo(
            applicationInfo.packageName,
            PackageManager.GET_META_DATA
        )


        val version = PackageUtil.getApplicationVersion(context, applicationInfo)
        val versionCode = PackageUtil.getApplicationVersionCode(context, applicationInfo)

        val apex = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (packageInfo.isApex) {
                context.getString(R.string.yes)
            } else {
                context.getString(R.string.no)
            }
        } else {
            context.getString(R.string.not_found)
        }

        val installLocation = kotlin.runCatching {
            when (packageInfo.installLocation) {
                PackageInfo.INSTALL_LOCATION_AUTO -> context.getString(R.string.auto)
                PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY -> context.getString(R.string.internal)
                PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL -> context.getString(R.string.external_preferred)
                else -> {
                    if (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                        context.getString(R.string.system)
                    } else {
                        context.getString(R.string.not_found)
                    }
                }
            }

        }.getOrElse {
            context.getString(R.string.not_found)
        }


        val gLesVersion = kotlin.runCatching {
            if (applicationInfo.getGlEsVersion().isEmpty()) {
                context.getString(R.string.not_found)
            } else {
                applicationInfo.getGlEsVersion()
            }
        }.getOrElse {
            context.getString(R.string.not_found)
        }

        val uid = applicationInfo.uid
        val installTime = applicationInfo.getApplicationInstalledTime(context, applicationInfo)
        val updateTime = applicationInfo.getApplicationUpdateTime(context, applicationInfo)

        val method = kotlin.runCatching {
            val dexData = applicationInfo.getDexData()!!
            var count = 0
            for (i in dexData) {
                count = i.header.methodIdsSize
            }
            if (dexData.size > 1) {
                String.format(
                    context.getString(R.string.multi),
                    NumberFormat.getNumberInstance().format(count)
                )
            } else {
                String.format(
                    context.getString(R.string.single),
                    NumberFormat.getNumberInstance().format(count)
                )
            }

        }.getOrElse {
            it.message!!
        }


        val minimumSdk = kotlin.runCatching {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                "${packageInfo.applicationInfo.minSdkVersion},${SdkInfo.getSdkVersion(packageInfo.applicationInfo.minSdkVersion)}"
            } else {

                when (val apkMeta: Any? = applicationInfo.getApkMetaData()) {
                    is ApkMeta -> {
                        "${apkMeta.minSdkVersion},${SdkInfo.getSdkTitle(apkMeta.minSdkVersion)}"
                    }
                    is com.jaredrummler.apkparser.model.ApkMeta -> {
                        "${apkMeta.minSdkVersion},${SdkInfo.getSdkTitle(apkMeta.minSdkVersion)}"
                    }

                    else -> {
                        context.getString(R.string.not_found)
                    }
                }

            }

        }.getOrElse {
            "This apk does not contain any recognizable dex classes"
        }


        val targetSdkVersion = kotlin.runCatching {
            "${packageInfo.applicationInfo.targetSdkVersion},${SdkInfo.getSdkTitle(packageInfo.applicationInfo.targetSdkVersion.toString())}"
        }.getOrElse {
            it.message!!
        }

        val applicationType = if (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
            context.getString(R.string.system)
        } else {
            context.getString(R.string.user)

        }

        val list= mutableListOf(
            Information("VersionName",version),
            Information("VersionCode",versionCode),
            Information("InstallLocation",installLocation),
            Information("GLES Version",gLesVersion),
            Information("UID",uid.toString()),
            Information("Install Date",installTime),
            Information("Update Date",updateTime),
            Information("Minimum SdK ",minimumSdk),
            Information("Target SdK ",targetSdkVersion),
            Information("Method Count ",method),
            Information("Apex  ",apex),
            Information("Application Type  ",applicationType),
        )

        _informationFlow.value=list


    }
}