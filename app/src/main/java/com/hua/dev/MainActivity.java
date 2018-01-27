package com.hua.dev;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hh.androidjni.PtlmanerJni;
import com.hua.dev.base.po.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mOnAddressPickerBtn;
    private Button mCacheBtn;
    private Button timeBtn;
    private Button activity_inside_choice_btn;
    private Button single_option_btn;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void findViewById() {
        TextView tv = (TextView) findViewById(R.id.tv);
        PtlmanerJni jniTest = new PtlmanerJni();
        tv.setText(jniTest.getTcb());
        mOnAddressPickerBtn = (Button) findViewById(R.id.onAddressPicker_btn);
        mCacheBtn = (Button) findViewById(R.id.cache_btn);
        timeBtn = (Button) findViewById(R.id.time_btn);
        activity_inside_choice_btn = (Button) findViewById(R.id.activity_inside_choice_btn);
        single_option_btn = (Button) findViewById(R.id.single_option_btn);
    }

    @Override
    protected void setListener() {
        mOnAddressPickerBtn.setOnClickListener(this);
        mCacheBtn.setOnClickListener(this);
        timeBtn.setOnClickListener(this);
        activity_inside_choice_btn.setOnClickListener(this);
        single_option_btn.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.onAddressPicker_btn:
                intent = new Intent(MainActivity.this, AddressPickActivity.class);
                break;
            case R.id.cache_btn:
                intent = new Intent(MainActivity.this, ApplicationCacheActivity.class);
                break;
            case R.id.time_btn:
                intent = new Intent(MainActivity.this, AboutTimeActivity.class);
                break;
            case R.id.activity_inside_choice_btn:
                intent = new Intent(MainActivity.this, ActivityInsideChoiceActivity.class);
                break;
            case R.id.single_option_btn:
                intent = new Intent(MainActivity.this, SingleChoiceActivity.class);
                break;
        }
        if(intent != null){
            startActivity(intent);
        }
    }
}
