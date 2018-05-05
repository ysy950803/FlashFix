package com.ysy.aidlservice

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
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
        println("pid:$pid")
        Thread(Runnable {
            Thread.sleep(100)
            val intent = Intent()
            intent.setClassName("com.ysy.sophix", "com.ysy.sophix.MainActivity")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
//            (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
//                    .killBackgroundProcesses("com.ysy.flashfix")
//            System.exit(0)
        }).start()
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }
}
