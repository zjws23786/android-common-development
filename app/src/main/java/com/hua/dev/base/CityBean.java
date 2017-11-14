package com.hua.dev.base;

/**
 * Created by hjz on 2016/12/5.
 */

public class CityBean {
    //{"city":"阿拉善盟","cityId":152900,"citypy":"A","type":2}
    private String city;
    private int cityId;
    private String citypy;
    private int type;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCitypy() {
        return citypy;
    }

    public void setCitypy(String citypy) {
        this.citypy = citypy;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
