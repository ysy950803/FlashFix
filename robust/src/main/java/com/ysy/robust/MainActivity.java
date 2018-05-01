package com.ysy.robust;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meituan.robust.PatchExecutor;
import com.meituan.robust.patch.annotaion.Modify;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.btn_patch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runRobust();
            }
        });

        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }

    private void runRobust() {
        new PatchExecutor(getApplicationContext(), new PatchManipulateImp(), new RobustCallBackSample()).start();
    }
}
