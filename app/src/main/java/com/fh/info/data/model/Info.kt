package com.fh.info.data.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(val packageName: Bitmap, val appVersion:String):Parcelable