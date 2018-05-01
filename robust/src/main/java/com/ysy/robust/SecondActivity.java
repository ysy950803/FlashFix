package com.ysy.robust;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.meituan.robust.patch.annotaion.Add;
import com.meituan.robust.patch.annotaion.Modify;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ((TextView)findViewById(R.id.tv_show)).setText(getText());
    }

    @Modify
    public String getText() {
        Object obj = new Object();
        System.out.println(obj.hashCode());
        obj.notify();
        getArray();
        return "2";
    }

    @Add
    public String[] getArray() {
        return new String[]{"hello","world"};
    }
}
