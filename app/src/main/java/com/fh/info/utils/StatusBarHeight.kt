package com.fh.info.utils

import android.content.res.Resources

object StatusBarHeight {

    /*get statusBar height using system frame work*/

    fun getStatusBarHeight(resources: Resources): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result

    }


}