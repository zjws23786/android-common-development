package com.hua.librarytools.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.WebView;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class SharedPreferenceUtils {

    /**
     *  保存boolean类型
     * @param context 上下文
     * @param preference  文件名
     * @param key   在文件中对应的key值
     * @param value key对应的vlaue值
     */
    public static void setPreferences(Context context, String preference, String key, boolean value) {
        if(context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(preference, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

    /**
     * 保存long类型
     */
    public static void setPreferences(Context context, String preference, String key, long value) {
        if(context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(preference, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(key, value);
            editor.commit();
        }
    }

    /**
     * 保存String类型
     */
    public static void setPreferences(Context context, String preference, String key, String value) {
        if(context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(preference, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    /**
     * 保存int类型
     */
    public static void setPreferences(Context context, String preference, String key, int value) {
        if(context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(preference, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            editor.commit();
        }
    }

    /**
     * 获取String值
     * @param context 上下文
     * @param preference 文件名
     * @param key
     * @param defaultValue  默认值
     * @return
     */
    public static String getPreference(Context context, String preference, String key, String defaultValue) {
        if(context == null) {
            return null;
        } else {
            SharedPreferences sharedPreferences = context.getSharedPreferences(preference, 0);
            return sharedPreferences.getString(key, defaultValue);
        }
    }

    /**
     *获取boolean值
     */
    public static boolean getPreference(Context context, String preference, String key, boolean defaultValue) {
        if(context == null) {
            return defaultValue;
        } else {
            SharedPreferences sharedPreferences = context.getSharedPreferences(preference, 0);
            return sharedPreferences.getBoolean(key, defaultValue);
        }
    }

    /**
     * 获取long值
     */
    public static long getPreference(Context context, String preference, String key, long defaultValue) {
        if(context == null) {
            return -1L;
        } else {
            SharedPreferences sharedPreferences = context.getSharedPreferences(preference, 0);
            return sharedPreferences.getLong(key, defaultValue);
        }
    }

    /**
     * 获取int值
     */
    public static int getPreference(Context context, String preference, String key, int defaultValue) {
        if(context == null) {
            return -1;
        } else {
            SharedPreferences sharedPreferences = context.getSharedPreferences(preference, 0);
            return sharedPreferences.getInt(key, defaultValue);
        }
    }

    /**
     * 清除指定的文件的内容
     */
    public static void clearPreference(Context context, String preference) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 清除指定的文件的对应key的内容
     */
    public static void clearPreference(Context context, String preference, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

}


