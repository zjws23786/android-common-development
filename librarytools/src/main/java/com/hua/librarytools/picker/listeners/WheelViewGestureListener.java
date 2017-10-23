package com.hua.librarytools.picker.listeners;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.hua.librarytools.picker.widget.WheelView;

/**
 * Created by huajz on 2017/10/23 0023.
 */

final public class WheelViewGestureListener extends GestureDetector.SimpleOnGestureListener {

    final WheelView wheelView;

    public WheelViewGestureListener(WheelView wheelView) {
        this.wheelView = wheelView;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        wheelView.scrollBy(velocityY);
        return true;
    }
}
