package com.hua.librarytools.safetyrelated;

import android.util.Base64;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class Ptlmaner {
    public static String tcb;
    private static final String TAG = "ProtocolManager";

    public Ptlmaner() {
    }

    public static String eryt(String content) {
        if(content != null) {
            if(tcb == null) {
                tcb = requestProcess();
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
            tcb = requestProcess();
        }

        if(content != null && tcb != null) {
            byte[] arr = Base64.decode(content.getBytes(), 2);
            byte[] resArr = Xxtea.dryt(arr, tcb.getBytes());
            return new String(resArr);
        } else {
            return null;
        }
    }

    public static native String requestProcess();

    static {
        System.loadLibrary("myjni");
    }
}
