package com.hua.dev;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.hua.dev.base.po.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mOnAddressPickerBtn;
    private Button mCacheBtn;
    private Button timeBtn;
    private Button activity_inside_choice_btn;
    private Button single_option_btn;
    private Button select_city_btn;
    private Button pull_down_refresh_btn;
    private Button bomb_box_btn;
    private Button selector_photo_btn;
    private Button test_jni_btn;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void findViewById() {
        mOnAddressPickerBtn = (Button) findViewById(R.id.onAddressPicker_btn);
        mCacheBtn = (Button) findViewById(R.id.cache_btn);
        timeBtn = (Button) findViewById(R.id.time_btn);
        activity_inside_choice_btn = (Button) findViewById(R.id.activity_inside_choice_btn);
        single_option_btn = (Button) findViewById(R.id.single_option_btn);
        select_city_btn = (Button) findViewById(R.id.select_city_btn);
        pull_down_refresh_btn = (Button) findViewById(R.id.pull_down_refresh_btn);
        bomb_box_btn = (Button) findViewById(R.id.bomb_box_btn);
        selector_photo_btn = (Button) findViewById(R.id.selector_photo_btn);
        test_jni_btn = (Button) findViewById(R.id.test_jni_btn);
    }

    @Override
    protected void setListener() {
        mOnAddressPickerBtn.setOnClickListener(this);
        mCacheBtn.setOnClickListener(this);
        timeBtn.setOnClickListener(this);
        activity_inside_choice_btn.setOnClickListener(this);
        single_option_btn.setOnClickListener(this);
        select_city_btn.setOnClickListener(this);
        pull_down_refresh_btn.setOnClickListener(this);
        bomb_box_btn.setOnClickListener(this);
        selector_photo_btn.setOnClickListener(this);
        test_jni_btn.setOnClickListener(this);
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
            case R.id.select_city_btn:
                intent = new Intent(MainActivity.this, CityLocationActivity.class);
                break;
            case R.id.pull_down_refresh_btn:
                intent = new Intent(MainActivity.this, PullToRefreshListActivity.class);
                break;
            case R.id.bomb_box_btn:
                intent = new Intent(MainActivity.this, BombBoxActivity.class);
                break;
            case R.id.selector_photo_btn:
                intent = new Intent(MainActivity.this, ImageAllActivity.class);
                break;
            case R.id.test_jni_btn:
                intent = new Intent(MainActivity.this, JniActivity.class);
                break;
        }
        if(intent != null){
            startActivity(intent);
        }
    }
}
