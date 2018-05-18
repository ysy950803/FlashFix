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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mFixManagerService: IFixManagerService? = null
    private var mTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTime = System.currentTimeMillis()
        initViews()
        bindFixManagerService()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "" + mTime + " " + Process.myPid(), Toast.LENGTH_SHORT).show()
    }

    private fun initViews() {
        tv_version.text = "" + mTime + " " + Process.myPid()

        findViewById<View>(R.id.btn_query_patch).setOnClickListener {
            SophixManager.getInstance().queryAndLoadNewPatch()
            Handler().postDelayed({
                Toast.makeText(applicationContext, "拉取成功", Toast.LENGTH_SHORT).show()
            }, 1000)
        }

        findViewById<View>(R.id.btn_apply_patch).setOnClickListener {
            notifyPatched()
//            Process.killProcess(Process.myPid())
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
        sendBroadcast(Intent("notifyPatched"))

        val home = Intent(Intent.ACTION_MAIN)
        home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        home.addCategory(Intent.CATEGORY_HOME)
        startActivity(home)
//        if (mFixManagerService != null) {
//            try {
//                mFixManagerService!!.notifyPatched(Process.myPid())
//            } catch (e: RemoteException) {
//                e.printStackTrace()
//            }
//        }
    }
}
