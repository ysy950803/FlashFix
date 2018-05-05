package com.ysy.sophix

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import com.taobao.sophix.SophixManager
import com.ysy.flashfix.IFixManagerService

class MainActivity : AppCompatActivity() {

    private var mFixManagerService: IFixManagerService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        bindFixManagerService()
    }

    private fun initViews() {
        findViewById<View>(R.id.btn_query_patch).setOnClickListener {
            SophixManager.getInstance().queryAndLoadNewPatch()
            Handler().postDelayed({
                Toast.makeText(applicationContext, "拉取成功", Toast.LENGTH_SHORT).show()
            }, 1000)
        }

        findViewById<View>(R.id.btn_apply_patch).setOnClickListener {
            notifyPatched()
            Process.killProcess(Process.myPid())
        }

        findViewById<View>(R.id.btn_open_new).setOnClickListener {
            startActivity(Intent(this, ScrollingActivity::class.java))
        }
    }

    private fun bindFixManagerService() {
        val intent = Intent("com.ysy.aidlservice.FixManagerService")
        intent.`package` = "com.ysy.flashfix"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mFixManagerService = IFixManagerService.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mFixManagerService = null
        }
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
