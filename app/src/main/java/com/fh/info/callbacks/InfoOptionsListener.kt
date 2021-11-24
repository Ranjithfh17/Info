package com.fh.info.callbacks

import com.fh.info.data.model.InfoOptions

interface InfoOptionsListener {
    fun onInfoOptionClick(infoOptions: InfoOptions)
}