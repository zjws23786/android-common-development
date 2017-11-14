package com.hua.dev.utils;

import com.alibaba.fastjson.JSON;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class JsonUtils {

    public static <T> T fromJson(String json, Class<T> tClass){
        T t = null;
        try {
            t = JSON.parseObject(json, tClass);
        } catch (Exception e) {
        }
        return t;
    }
}
