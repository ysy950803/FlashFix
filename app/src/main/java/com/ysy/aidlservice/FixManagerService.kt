package com.ysy.aidlservice

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Process
import android.os.RemoteException
import android.widget.Toast
import com.ysy.flashfix.IFixManagerService

class FixManagerService : Service() {

    private val mBinder = object : IFixManagerService.Stub() {
        @Throws(RemoteException::class)
        override fun notifyPatched(pid: Int) {
            restartClient(pid)
        }
    }

    private fun restartClient(pid: Int) {
//        Process.killProcess(pid)
//        (getSystemService(Context.ACTIVITY_SERVICE)
//                as ActivityManager).restartPackage("com.ysy.sophix")
        Thread(Runnable {
//            Thread.sleep(1000)
            val intent = Intent()
            intent.setClassName("com.ysy.sophix", "com.ysy.sophix.MainActivity")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }).start()
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }
}
