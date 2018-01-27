package com.hua.librarytools.utils.safetyrelated;

import android.util.Base64;

/**
 * Created by huajz on 2018/1/27 0027.
 */

public class Ptlmaner {
    public static String tcb;

    public Ptlmaner() {
    }

    public static String eryt(String content) {
        if(content != null) {
            if(tcb == null) {
                requestProcess();
            }

            if(tcb != null) {
                byte[] xxtea = Xxtea.eryt(content.getBytes(), tcb.getBytes());
                byte[] base64 = Base64.encode(xxtea, 2);
                return new String(base64);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String dryt(String content) {
        if(tcb == null) {
            requestProcess();
        }

        if(content != null && tcb != null) {
            byte[] arr = Base64.decode(content.getBytes(), 2);
            byte[] resArr = Xxtea.dryt(arr, tcb.getBytes());
            return new String(resArr);
        } else {
            return null;
        }
    }

    public static native void requestProcess();

    static {
        System.loadLibrary("myjni");
    }
}
