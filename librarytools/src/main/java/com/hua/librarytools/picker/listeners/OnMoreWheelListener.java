package com.hua.librarytools.picker.listeners;

/**
 * Created by huajz on 2017/10/23 0023.
 * 针对地址选择等提供的外部回调接口
 */

public interface OnMoreWheelListener {
    public abstract void onFirstWheeled(int index, String item);

    public abstract void onSecondWheeled(int index, String item);

    public abstract void onThirdWheeled(int index, String item);
}

