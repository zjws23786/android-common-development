package com.hua.dev;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hua.dev.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mOnAddressPickerBtn;
    private Button mCacheBtn;
    private Button timeBtn;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void findViewById() {
        mOnAddressPickerBtn = (Button) findViewById(R.id.onAddressPicker_btn);
        mCacheBtn = (Button) findViewById(R.id.cache_btn);
        timeBtn = (Button) findViewById(R.id.time_btn);
    }

    @Override
    protected void setListener() {
        mOnAddressPickerBtn.setOnClickListener(this);
        mCacheBtn.setOnClickListener(this);
        timeBtn.setOnClickListener(this);
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
        }
        if(intent != null){
            startActivity(intent);
        }
    }
}
