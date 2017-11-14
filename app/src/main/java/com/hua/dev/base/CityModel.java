package com.hua.dev.base;

import java.util.List;

/**
 * Created by hjz on 2016/12/5.
 */

public class CityModel {

    private String code;
    private String message;
    /**
     * city : 阿拉善盟
     * cityId : 152900
     * citypy : A
     * type : 2
     */

    private List<CityBean> addressList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CityBean> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<CityBean> addressList) {
        this.addressList = addressList;
    }

}
