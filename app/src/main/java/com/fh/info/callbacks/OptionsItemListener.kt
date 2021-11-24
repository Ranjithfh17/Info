package com.fh.info.callbacks

import com.fh.info.data.model.Options

interface OptionsItemListener {
    fun onOptionItemClick(options: Options)
}