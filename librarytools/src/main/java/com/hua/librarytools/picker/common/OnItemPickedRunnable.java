package com.hua.librarytools.picker.common;

import com.hua.librarytools.picker.listeners.OnItemPickListener;
import com.hua.librarytools.picker.widget.WheelView;

/**
 * Created by huajz on 2017/10/23 0023.
 */

final public class OnItemPickedRunnable implements Runnable {
    final private WheelView wheelView;
    private OnItemPickListener onItemPickListener;
    public OnItemPickedRunnable(WheelView wheelView, OnItemPickListener onItemPickListener) {
        this.wheelView = wheelView;
        this.onItemPickListener = onItemPickListener;
    }

    @Override
    public final void run() {
        onItemPickListener.onItemPicked(wheelView.getCurrentPosition(),wheelView.getCurrentItem());
    }
}
