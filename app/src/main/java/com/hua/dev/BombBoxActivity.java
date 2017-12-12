package com.hua.dev;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hua.dev.base.po.BaseActivity;
import com.hua.librarytools.common.LibraryConstant;
import com.hua.librarytools.utils.CustomPopWindow;
import com.hua.librarytools.utils.DensityUtil;

public class BombBoxActivity extends BaseActivity implements View.OnClickListener {
    private Button mPopBelowBtn;
    private Button mPopAboveBtn;

    private TextView mSuperClearTv;
    private TextView mHighClearTv;
    private TextView mSdClearTv;
    private TextView mFluentTv;


    private CustomPopWindow detinitionPop;  //视频清晰度切换
    private View popRootView;


    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_bomb_box);
    }

    @Override
    protected void findViewById() {
        mPopAboveBtn = (Button) findViewById(R.id.pop_above_btn);
        mPopBelowBtn = (Button) findViewById(R.id.pop_below_btn);


        popRootView = LayoutInflater.from(this).inflate(R.layout.pop_definition_layout, null);
//        popRootView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mSuperClearTv = (TextView) popRootView.findViewById(R.id.super_clear_tv);
        mHighClearTv = (TextView) popRootView.findViewById(R.id.high_clear_tv);
        mSdClearTv = (TextView) popRootView.findViewById(R.id.sd_clear_tv);
        mFluentTv = (TextView) popRootView.findViewById(R.id.fluent_tv);
        CustomPopWindow.Builder builder = new CustomPopWindow.Builder(this);
        detinitionPop = builder.create(popRootView, DensityUtil.dip2px(this,50), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void setListener() {
        mPopBelowBtn.setOnClickListener(this);
        mPopAboveBtn.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pop_above_btn:
                detinitionPop.showPopWindow(mPopAboveBtn, LibraryConstant.POP_ABOVE,0);
                break;
            case R.id.pop_below_btn:
                detinitionPop.showPopWindow(mPopBelowBtn, LibraryConstant.POP_BELOW, DensityUtil.dip2px(this,12));
                break;
        }
    }
}
