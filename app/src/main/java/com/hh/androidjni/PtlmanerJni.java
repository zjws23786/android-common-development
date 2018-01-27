package com.hh.androidjni;

/**
 * Created by huajz on 2018/1/27 0027.
 */

public class PtlmanerJni {
    public static String tcb;

    public String getTcb() {
        if (tcb == null){
            tcb = getString();
        }
        return tcb;
    }

    static {
        System.loadLibrary("myjni");
    }

    public native String getString();
}
