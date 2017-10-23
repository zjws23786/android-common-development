package com.hua.librarytools.picker.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huajz on 2017/10/23 0023.
 * 地市
 */

public class City extends ItemBean {
    private String provinceId;
    private List<County> counties = new ArrayList<County>();

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public List<County> getCounties() {
        return counties;
    }

    public void setCounties(List<County> counties) {
        this.counties = counties;
    }

}
