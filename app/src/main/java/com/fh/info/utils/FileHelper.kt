package com.fh.info.utils

import java.io.File
import java.text.CharacterIterator
import java.text.StringCharacterIterator
import kotlin.math.abs

object FileHelper {

    fun String.getFileSize(): String {
        return File(this).length().getFileSize("si")
    }
}


fun Array<String>.getDirectorySize(): String {
    var total = 0L

    for (i in this.indices) {
        total = File(this[i]).length()
    }

    return total.getFileSize("si")
}


fun String.getDirectoryLength():Long{
    return File(this).length()

}

fun Long.getFileSize(type: String): String {

    return when (type) {
        "si" -> {
            this.humanReadableByteCountSI()
        }
        "binary" -> {
            this.humanReadableByteCountBinary()
        }

        else -> {
            this.humanReadableByteCountSI()
        }
    }

}


private fun Long.humanReadableByteCountBinary(): String {
    val absB = if (this == Long.MIN_VALUE) Long.MAX_VALUE else abs(this)
    if (absB < 1024) {
        return "$this B"
    }

    var value = absB
    val characterIterator: CharacterIterator = StringCharacterIterator("KMGTPE")
    var i = 40

    while (i >= 0 && absB > 0xfffccccccccccccL shr i) {
        value = value shr 10
        characterIterator.next()
        i -= 10
    }
    value *= java.lang.Long.signum(this).toLong()
    return String.format("%.1f %ciB", value / 1024.0, characterIterator.current())
}

private fun Long.humanReadableByteCountSI(): String {
    var bytes = this
    if (-1000 < bytes && bytes < 1000) {
        return "$bytes B"
    }
    val ci: CharacterIterator = StringCharacterIterator("kMGTPE")
    while (bytes <= -999950 || bytes >= 999950) {
        bytes /= 1000
        ci.next()
    }
    return String.format("%.1f %cB", bytes / 1000.0, ci.current())
}
