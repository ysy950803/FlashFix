package com.ysy.sophix

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.taobao.sophix.SophixManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val resIdList = intArrayOf(R.string.title_home,
            R.string.title_dashboard,
            R.string.title_notifications)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(resIdList[2])
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(resIdList[1])
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(resIdList[0])
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SophixManager.getInstance().queryAndLoadNewPatch()
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        btn_query_patch.setOnClickListener({
            SophixManager.getInstance().queryAndLoadNewPatch()
            Toast.makeText(this, "queryAndLoadNewPatch", Toast.LENGTH_SHORT).show()
        })
    }
}
