package com.ysy.flashfix

import android.Manifest
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ysy.external.IPlugin
import dalvik.system.DexClassLoader

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private var mFixNotifyReceiver: FixNotifyReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerFixNotifyReceiver()
        showClassLoaderInfo()
        initViews()
//        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterFixNotifyReceiver()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    external fun stringFromJNI(): String

    companion object {

        init {
            System.loadLibrary("native-lib")
        }
    }

    private fun initViews() {
        sample_text.text = stringFromJNI()

        fab.setOnClickListener { view ->
            Thread(Runnable {
                (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                        .killBackgroundProcesses("com.ysy.sophix")
                Thread.sleep(1024)
                val intent = Intent()
                intent.setClassName("com.ysy.sophix", "com.ysy.sophix.MainActivity")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }).start()
//            Snackbar.make(view, "成功加载新类", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
//                } else {
//                    refreshText()
//                }
//            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            refreshText()
        }
    }

    private fun showClassLoaderInfo() {
        var loader = MainActivity::class.java.classLoader
        while (loader != null) {
            println(loader.toString())
            loader = loader.parent
        }
    }

    private fun refreshText() {
        val str = getTextFromPlugin()
        sample_text.text = str

        if (str == "Success") {
            btn_open_client.visibility = View.VISIBLE
            btn_open_client.setOnClickListener {
                startActivity(Intent(this, OpenActivity::class.java))
            }
        }
    }

    private fun getTextFromPlugin(): String {
        val jarFile = File(Environment.getExternalStorageDirectory().path
                + File.separator + "flashfix_ext_dex.jar")

        if (!jarFile.exists()) {
            return "File Not Exists"
        }

        val dexClassLoader = DexClassLoader(jarFile.absolutePath, externalCacheDir.absolutePath,
                null, classLoader)
        try {
            val clazz = dexClassLoader.loadClass("com.ysy.external.PluginManager")
            val plugin = clazz.newInstance() as IPlugin
            return plugin.testText
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "Fail"
    }

//    private fun startTimer() {
//        Handler().postDelayed({
//            run()
//        }, 2000)
//    }
//
//    private fun run() {
//        try {
//            val cl = HotSwapCL(Environment.getExternalStorageDirectory().path, arrayOf("Foo"))
//            val cls = cl.loadClass("Foo")
//            val foo = cls.newInstance()
//
//            val method = foo.javaClass.getMethod("sayHello", *arrayOf())
//            method.invoke(foo, arrayOf<Any>())
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    private fun registerFixNotifyReceiver() {
        if (mFixNotifyReceiver == null) {
            mFixNotifyReceiver = FixNotifyReceiver()
        }
        val filter = IntentFilter("notifyPatched")
        registerReceiver(mFixNotifyReceiver, filter)
    }

    private fun unregisterFixNotifyReceiver() {
        if (mFixNotifyReceiver != null) {
            unregisterReceiver(mFixNotifyReceiver)
        }
    }

    private inner class FixNotifyReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action == "notifyPatched") {
                Thread(Runnable {
                    (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                            .killBackgroundProcesses("com.ysy.sophix")
                    Thread.sleep(1024)
                    val i = Intent()
                    i.setClassName("com.ysy.sophix", "com.ysy.sophix.MainActivity")
                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(i)
                }).start()
            }
        }
    }
}
