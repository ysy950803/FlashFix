package com.ysy.sophix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        ((TextView) findViewById(R.id.btn_other)).setText(getNB());
    }

    private String getNB() {
        if (OtherActivity.class.getSimpleName().contains("Other")) {
            return "NB";
        }
        return "BN";
    }
}
