package com.hua.librarytools.picker.listeners;

/**
 * Created by huajz on 2017/10/23 0023.
 * 点击确认按钮选中item的回调
 */

public interface OnItemPickListener<T> {
    void onItemPicked(int index, T item);
}
