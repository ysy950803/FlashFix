package com.ysy.aidlservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.os.RemoteException

import com.ysy.flashfix.IFixManagerService

class FixManagerService : Service() {

    private val mBinder = object : IFixManagerService.Stub() {
        @Throws(RemoteException::class)
        override fun notifyPatched(pid: Int) {
            restartClient(pid)
        }
    }

    private fun restartClient(pid: Int) {
        Process.killProcess(pid)
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }
}
