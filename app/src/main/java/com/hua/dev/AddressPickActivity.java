package com.hua.dev;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hua.dev.base.po.BaseActivity;
import com.hua.librarytools.picker.AddressPicker;
import com.hua.librarytools.picker.bean.City;
import com.hua.librarytools.picker.bean.County;
import com.hua.librarytools.picker.bean.Province;
import com.hua.librarytools.picker.listeners.OnLinkageListener;
import com.hua.librarytools.utils.ConvertUtils;

import java.io.IOException;
import java.util.ArrayList;

public class AddressPickActivity extends BaseActivity implements OnLinkageListener, View.OnClickListener {
    private Button addressPickerBtn;
    private Button provincial_city_btn;
    private Button city_county_btn;
    private boolean hideProvince = false;
    private boolean hideCounty = false;
    private String selectedProvince = "广东省", selectedCity = "广州市", selectedCounty = "天河区";

    ArrayList<Province> result = new ArrayList<>();

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_address_pick);
    }

    @Override
    protected void findViewById() {
        addressPickerBtn = (Button) findViewById(R.id.address_picker_btn);
        provincial_city_btn = (Button) findViewById(R.id.provincial_city_btn);
        city_county_btn = (Button) findViewById(R.id.city_county_btn);
    }

    @Override
    protected void setListener() {
        addressPickerBtn.setOnClickListener(this);
        provincial_city_btn.setOnClickListener(this);
        city_county_btn.setOnClickListener(this);
    }


    @Override
    protected void init() {
        try {
            String json = ConvertUtils.toString(this.getAssets().open("city.json"));
            result.addAll(JSON.parseArray(json, Province.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onAddressPicked(Province province, City city, County county) {
        Toast.makeText(this, (province == null ? "": province.getAreaName()) +
                (city== null ? "" : city.getAreaName()) +
                (county == null ? "" : county.getAreaName()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_picker_btn:
                provincialTownsPickMethod();
                break;
            case R.id.provincial_city_btn:
                provincialCityMethod();
                break;
            case R.id.city_county_btn:
                cityCountyMethod();
                break;
        }
    }

    //省-城市-县级
    private void provincialTownsPickMethod() {
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

    }

    //省 城市
    private void provincialCityMethod() {
        if (result.size() > 0) {
            AddressPicker picker = new AddressPicker(this, result);
            picker.setHideProvince(hideProvince);
            picker.setHideCounty(true);
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
    }

    //城市 县区
    private void cityCountyMethod() {
        if (result.size() > 0) {
            AddressPicker picker = new AddressPicker(this, result);
            picker.setHideProvince(true);
            picker.setHideCounty(false);
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
    }
}
