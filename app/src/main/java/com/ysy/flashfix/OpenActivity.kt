package com.ysy.flashfix

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class OpenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)

        val intent = Intent()
        intent.setClassName("com.ysy.sophix", "com.ysy.sophix.MainActivity")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
