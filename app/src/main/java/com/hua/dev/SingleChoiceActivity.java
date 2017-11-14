package com.hua.dev;

import android.view.View;
import android.widget.Button;

import com.hua.dev.base.po.BaseActivity;
import com.hua.librarytools.picker.listeners.OnItemPickListener;
import com.hua.librarytools.picker.listeners.OnSingleWheelLicstener;
import com.hua.librarytools.picker.single.SinglePicker;
import com.hua.librarytools.utils.UIToast;

import java.util.ArrayList;

public class SingleChoiceActivity extends BaseActivity implements View.OnClickListener {
    private Button single_option_btn;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_single_choice);
    }

    @Override
    protected void findViewById() {
        single_option_btn = (Button) findViewById(R.id.single_option_btn);
    }

    @Override
    protected void setListener() {
        single_option_btn.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.single_option_btn:
                onOptionPicker();
                break;
        }
    }

    private void onOptionPicker() {
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i<60; i++){
            String s = "";
            if(i<10){
                s = "0"+i;
            }else{
                s = i+"";
            }
            list.add(s);
        }
//        String[] ss = (String[]) list.toArray();
        SinglePicker<String> picker = new SinglePicker<>(this, list);
        picker.setCanLoop(false);//不禁用循环
        picker.setLineVisible(true);
        picker.setShadowVisible(true);
        picker.setTextSize(18);
        picker.setSelectedIndex(1);
        picker.setWheelModeEnable(true);
        //启用权重 setWeightWidth 才起作用
        picker.setLabel("分");
        picker.setWeightEnable(true);
        picker.setWeightWidth(1);
        picker.setSelectedTextColor(0xFF279BAA);//前四位值是透明度
        picker.setUnSelectedTextColor(0xFF999999);
        picker.setOnSingleWheelListener(new OnSingleWheelLicstener() {
            @Override
            public void onWheeled(int index, String item) {
                UIToast.showBaseToast(SingleChoiceActivity.this, "index=" + index + ", item=" + item, R.style.AnimationToast);
            }
        });
        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                UIToast.showBaseToast(SingleChoiceActivity.this, "index=" + index + ", item=" + item, R.style.AnimationToast);
            }
        });
        picker.show();
    }
}
