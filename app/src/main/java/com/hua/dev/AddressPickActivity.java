package com.hua.dev;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hua.dev.base.BaseActivity;
import com.hua.librarytools.picker.AddressPicker;
import com.hua.librarytools.picker.bean.City;
import com.hua.librarytools.picker.bean.County;
import com.hua.librarytools.picker.bean.Province;
import com.hua.librarytools.picker.listeners.OnLinkageListener;
import com.hua.librarytools.utils.ConvertUtils;

import java.util.ArrayList;

public class AddressPickActivity extends BaseActivity implements OnLinkageListener, View.OnClickListener {
    private Button addressPickerBtn;
    private boolean hideProvince = false;
    private boolean hideCounty = false;
    private String selectedProvince = "广东省", selectedCity = "广州市", selectedCounty = "天河区";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_pick);
        addressPickerBtn = (Button) findViewById(R.id.address_picker_btn);

    }

    @Override
    protected void setLayout() {
        addressPickerBtn.setOnClickListener(this);
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void setListener() {

    }


    @Override
    protected void init() {


    }



    @Override
    public void onAddressPicked(Province province, City city, County county) {
        Toast.makeText(this,province.getAreaName() + city.getAreaName() + county.getAreaName(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.address_picker_btn:
                provincialTownsPickMethod();
                break;
        }
    }

    //省-城市-县级
    private void provincialTownsPickMethod() {
        ArrayList<Province> result = new ArrayList<>();
        try {
            String json = ConvertUtils.toString(this.getAssets().open("city.json"));
            result.addAll(JSON.parseArray(json, Province.class));
            if (result.size() > 0) {
                AddressPicker picker = new AddressPicker(this, result);
                picker.setHideProvince(hideProvince);
                picker.setHideCounty(hideCounty);
                picker.setCanLoop(true);
                picker.setWheelModeEnable(true);
                if (hideCounty) {
                    picker.setColumnWeight(1 / 3.0f, 2 / 3.0f);//将屏幕分为3份，省级和地级的比例为1:2
                } else {
                    picker.setColumnWeight(2 / 8.0f, 3 / 8.0f, 3 / 8.0f);//省级、地级和县级的比例为2:3:3
                }
                picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);
                picker.setOnLinkageListener(this);
                picker.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
