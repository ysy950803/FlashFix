package com.ysy.sophix

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.Process
import android.os.RemoteException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.taobao.sophix.SophixManager

class MainActivity : AppCompatActivity() {

    private var mFixManagerService: IFixManagerService? = null

    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mFixManagerService = IFixManagerService.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mFixManagerService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        bindFixManagerService()
    }

    private fun initViews() {
        findViewById<View>(R.id.btn_query_patch).setOnClickListener { SophixManager.getInstance().queryAndLoadNewPatch() }

        findViewById<View>(R.id.btn_open_other).setOnClickListener {
            startActivity(Intent(this@MainActivity, OtherActivity::class.java))
            notifyPatched()
        }

        (findViewById<View>(R.id.tv_stat) as TextView).text = "Three"
    }

    private fun bindFixManagerService() {
        val intent = Intent("com.ysy.aidlservice.FixManagerService")
        intent.`package` = "com.ysy.flashfix"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        if (mFixManagerService != null) {
            unbindService(mServiceConnection)
        }
        super.onDestroy()
    }

    private fun notifyPatched() {
        if (mFixManagerService != null) {
            try {
                mFixManagerService!!.notifyPatched(Process.myPid())
            } catch (e: RemoteException) {
                e.printStackTrace()
            }

        }
    }
}
