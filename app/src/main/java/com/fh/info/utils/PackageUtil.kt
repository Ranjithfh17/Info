package com.fh.info.utils

import android.app.Activity
import android.app.usage.StorageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.fh.info.R
import com.fh.info.data.model.PackageSize

object PackageUtil {

    fun ApplicationInfo.isPackageInstalled(packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun getApplicationName(context: Context, applicationInfo: ApplicationInfo): String {
        return try {
            context.packageManager.getApplicationLabel(applicationInfo).toString()
        } catch (exception: PackageManager.NameNotFoundException) {
            context.getString(R.string.not_found)
        }
    }

    fun getApplicationVersion(context: Context, applicationInfo: ApplicationInfo): String {
        return try {
            context.packageManager.getPackageInfo(
                applicationInfo.packageName,
                PackageManager.GET_META_DATA
            ).versionName
        } catch (exception: Exception) {
            context.getString(R.string.not_found)
        }
    }

    fun getApplicationVersionCode(context: Context, applicationInfo: ApplicationInfo): String {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                context.packageManager.getPackageInfo(
                    applicationInfo.packageName,
                    0
                ).longVersionCode.toString()
            } else {
                context.packageManager.getPackageInfo(
                    applicationInfo.packageName,
                    0
                ).versionCode.toString()
            }
        } catch (exception: Exception) {
            context.getString(R.string.not_found)
        }
    }

    fun ApplicationInfo.getApplicationInstalledTime(
        context: Context,
        applicationInfo: ApplicationInfo
    ): String {
        return try {
            DateUtil.format(
                context.packageManager.getPackageInfo(
                    applicationInfo.packageName,
                    0
                ).firstInstallTime
            )
        } catch (exception: Exception) {
            context.getString(R.string.not_found)
        }
    }

    fun ApplicationInfo.getApplicationUpdateTime(
        context: Context,
        applicationInfo: ApplicationInfo
    ): String {
        return try {
            DateUtil.format(
                context.packageManager.getPackageInfo(
                    applicationInfo.packageName,
                    0
                ).lastUpdateTime
            )
        } catch (exception: Exception) {
            context.getString(R.string.not_found)
        }
    }

    fun ApplicationInfo.getPackageSize(context: Context): PackageSize {

        val storageStatsManager =
            context.getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
        return try {
            val storageStats = storageStatsManager.queryStatsForUid(this.storageUuid, this.uid)
            PackageSize(storageStats.cacheBytes, storageStats.dataBytes, storageStats.appBytes)

        } catch (exception: Exception) {
            PackageSize()
        }

    }

    fun ApplicationInfo.launchApp(activity: Activity) {
        try {
            val intent = activity.packageManager.getLaunchIntentForPackage(this.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            activity.startActivity(intent)

        } catch (exception: Exception) {
            Toast.makeText(
                activity.baseContext,
                "Failed to launch app due to ${exception.message} ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    fun ApplicationInfo.uninstallApplication() {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.putExtra(Intent.EXTRA_RETURN_RESULT, true)
        intent.data = Uri.parse("package${this.packageName}")


    }


}