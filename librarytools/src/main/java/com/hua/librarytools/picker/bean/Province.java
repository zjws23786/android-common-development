package com.hua.librarytools.picker.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huajz on 2017/10/23 0023.
 * 省份
 */

public class Province extends ItemBean {
    private List<City> cities = new ArrayList<City>();

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

}