package com.hua.librarytools.picker.listeners;

import com.hua.librarytools.picker.bean.City;
import com.hua.librarytools.picker.bean.County;
import com.hua.librarytools.picker.bean.Province;

/**
 * Created by huajz on 2017/10/23 0023.
 */

public interface OnLinkageListener {
    /**
     * 选择地址
     *
     * @param province the province
     * @param city    the city
     * @param county   the county ，if {@code hideCounty} is true，this is null
     */
    void onAddressPicked(Province province, City city, County county);
}
