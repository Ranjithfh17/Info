package com.fh.info.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun format(date: Long): String {
        val simpleDateFormat = SimpleDateFormat("EEE, yyyy MMM dd, hh:mm:a", Locale.getDefault())
        return simpleDateFormat.format(date)
    }


}