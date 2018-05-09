package com.hua.dev;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.hua.dev.base.po.BaseActivity;

public class SmartRefreshLayoutAllActivity extends BaseActivity implements View.OnClickListener {
    private Button default_smart_btn;
    private Button classics_header_smart_btn;
    private Button falsify_header_smart_btn;
    private Button two_level_header_smart_btn;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_smart_refresh_layout_all);
    }

    @Override
    protected void findViewById() {
        default_smart_btn = findViewById(R.id.default_smart_btn);
        classics_header_smart_btn = findViewById(R.id.classics_header_smart_btn);
        falsify_header_smart_btn = findViewById(R.id.falsify_header_smart_btn);
        two_level_header_smart_btn = findViewById(R.id.two_level_header_smart_btn);
    }

    @Override
    protected void setListener() {
        default_smart_btn.setOnClickListener(this);
        classics_header_smart_btn.setOnClickListener(this);
        falsify_header_smart_btn.setOnClickListener(this);
        two_level_header_smart_btn.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.default_smart_btn:
                intent = new Intent(this, SmartBezierRadarHeaderRefreshLayoutActivity.class);
                break;
            case R.id.classics_header_smart_btn:
                intent = new Intent(this, SmartClassicsHeaderRefreshLayoutActivity.class);
                break;
            case R.id.falsify_header_smart_btn:
                intent = new Intent(this, SmartFalsifyHeaderRefreshLayoutActivity.class);
                break;
            case R.id.two_level_header_smart_btn:
                intent = new Intent(this, SmartTwoLevelHeaderRefreshLayoutActivity.class);
                break;
        }
        if(intent != null){
            startActivity(intent);
        }
    }
}
