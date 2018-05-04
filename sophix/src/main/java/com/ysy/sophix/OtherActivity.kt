package com.ysy.sophix

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class OtherActivity : AppCompatActivity() {

    private val nb: String
        get() = if (OtherActivity::class.java.simpleName.contains("Other")) {
            "NB"
        } else "BN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)

        (findViewById<View>(R.id.btn_other) as TextView).text = nb
    }
}
