package com.hua.librarytools.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.hua.librarytools.common.LibraryConstant;

/**
 * @author hjz
 * @date 2017/12/12 0012
 */

public class CustomPopWindow extends PopupWindow {
    private int[] location = new int[2];


    public CustomPopWindow(View layout, int width, int height) {
        super(layout, width, height);
    }

    /**
     * 在指定控件相关位置
     *
     * @param relativeView    指定控件
     * @param position  相对parent位置（如上面或下面）
     * @param marginTop 距离顶部的位置 单位px
     */
    public void showPopWindow(View relativeView, String position, int marginTop) {
        //获取高度之前先进行相关测量
//        Log.v("hjz",this.getContentView().getMeasuredHeight()+ "高度");
        if (this.isShowing()) {
            this.dismiss();
        } else {
            if (position.equals(LibraryConstant.POP_ABOVE)) {
                // 获得位置 这里的v是目标控件，就是你要放在这个v的上面还是下面
                relativeView.getLocationOnScreen(location);

                this.showAtLocation(relativeView, Gravity.NO_GRAVITY,
                        (location[0] + relativeView.getWidth() / 2) - this.getContentView().getMeasuredWidth()/2,
                        location[1] - this.getContentView().getMeasuredHeight());
            } else if (position.equals(LibraryConstant.POP_BELOW)) {
                relativeView.getLocationOnScreen(location);
                //这种方式无论有虚拟按键还是没有都可完全显示，因为它显示的在整个父布局中
                this.showAtLocation(relativeView, Gravity.NO_GRAVITY,
                        (location[0] + relativeView.getWidth() / 2) - this.getContentView().getMeasuredWidth()/2,
                        location[1] + relativeView.getHeight() + marginTop);
            }
        }
    }

    /**
     * 在指定控件相关位置
     *
     * @param relativeView             指定控件
     * @param position           相对parent位置（如上面或下面）
     * @param verticalDistance   垂直方向距离  单位dp
     * @param horizontalDistance 水平方向距离  单位dp
     */
    public void showPopWindow(View relativeView, String position, int verticalDistance, int horizontalDistance) {
        if (this.isShowing()) {
            this.dismiss();
        } else {
            if (position.equals(LibraryConstant.POP_ABOVE)) {
                // 获得位置 这里的v是目标控件，就是你要放在这个v的上面还是下面
                relativeView.getLocationOnScreen(location);
                this.showAtLocation(relativeView, Gravity.NO_GRAVITY,
                        (location[0] + relativeView.getWidth() / 2) - this.getContentView().getMeasuredWidth()/2,
                        location[1] - this.getContentView().getMeasuredHeight());
            } else if (position.equals(LibraryConstant.POP_BELOW)) {
                relativeView.getLocationOnScreen(location);
                //这种方式无论有虚拟按键还是没有都可完全显示，因为它显示的在整个父布局中
                this.showAtLocation(relativeView, Gravity.NO_GRAVITY,
                        (location[0] + relativeView.getWidth() / 2) - this.getContentView().getMeasuredWidth()/2,
                        location[1] + relativeView.getHeight() + verticalDistance);
            }
        }
    }

    public static class Builder {
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置固定宽度的大小
         *
         * @param rootView
         * @param width    固定的宽度 单位px
         * @param height   固定的高度 单位px
         * @return
         */
        public CustomPopWindow create(View rootView, int width, int height) {
            CustomPopWindow popWindow = new CustomPopWindow(rootView, width, height);
            //优先进行计算【如果不进行测量就获取不到对应的宽和高】
            popWindow.getContentView().measure(0, 0);
            popWindow.setOutsideTouchable(true);
            popWindow.setFocusable(true);
            popWindow.setBackgroundDrawable(new BitmapDrawable());
            return popWindow;
        }

        /**
         * 宽度占百分比，高度固定
         *
         * @param rootView
         * @param widthPercentage 宽度百分比
         * @param height          高度固定
         * @return
         */
        public CustomPopWindow create(View rootView, float widthPercentage, int height) {

            int screenWidth = 0;
//            int screenHeight = 0;
            if (screenWidth == 0){
                WindowManager wm = (WindowManager)context.getSystemService("window");
                int rotation = wm.getDefaultDisplay().getRotation();
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                screenWidth = rotation == 0 ? metrics.widthPixels:metrics.heightPixels;
//                screenHeight = rotation == 0 ? metrics.heightPixels:metrics.heightPixels;
            }
            int width = 0;
            if (widthPercentage > 0) {
                width = (int) (screenWidth * widthPercentage);
            } else {
                width = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            CustomPopWindow popWindow = new CustomPopWindow(rootView, width, height);
            //优先进行计算【如果不进行测量就获取不到对应的宽和高】
            popWindow.getContentView().measure(0, 0);
            popWindow.setOutsideTouchable(true);
            popWindow.setFocusable(true);
            popWindow.setBackgroundDrawable(new BitmapDrawable());
            return popWindow;
        }
    }
}

