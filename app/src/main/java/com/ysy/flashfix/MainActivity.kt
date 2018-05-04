package com.ysy.flashfix

import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.ysy.plugin.IPlugin
import dalvik.system.DexClassLoader

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

//        sample_text.text = stringFromJNI()

        showClassLoaderInfo()
        sample_text.text = getTextFromPlugin()
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

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        init {
            System.loadLibrary("native-lib")
        }
    }

    private fun showClassLoaderInfo() {
        var loader = MainActivity::class.java.classLoader
        while (loader != null) {
            println(loader.toString())
            loader = loader.parent
        }
    }

    private fun getTextFromPlugin(): String {
        val jarFile = File(Environment.getExternalStorageDirectory().path
                + File.separator + "flashfix_plugin_dex.jar")

        if (!jarFile.exists()) {
            return ""
        }

        val dexClassLoader = DexClassLoader(jarFile.absolutePath, externalCacheDir.absolutePath,
                null, classLoader)
        val clazz = dexClassLoader.loadClass("com.ysy.plugin.PluginManager")
        val plugin = clazz.newInstance() as IPlugin
        return plugin.getTestText
    }
}
