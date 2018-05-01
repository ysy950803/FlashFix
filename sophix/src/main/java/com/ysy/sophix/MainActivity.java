package com.ysy.sophix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.taobao.sophix.SophixManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_query_patch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SophixManager.getInstance().queryAndLoadNewPatch();
            }
        });

        findViewById(R.id.btn_open_other).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OtherActivity.class));
            }
        });

        ((TextView) findViewById(R.id.tv_stat)).setText("Three");
    }
}
