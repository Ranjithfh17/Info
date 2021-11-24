package com.fh.info.callbacks

import com.fh.info.data.model.InfoItems
import com.fh.info.data.model.InfoOptions

interface InfoItemListener {
    fun onInfoItemClick(infoItems: InfoItems)
}