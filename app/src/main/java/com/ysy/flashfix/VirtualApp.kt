package com.ysy.flashfix

import android.app.Application
import android.content.Context
import com.didi.virtualapk.PluginManager

class VirtualApp : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        PluginManager.getInstance(base).init()
    }
}
