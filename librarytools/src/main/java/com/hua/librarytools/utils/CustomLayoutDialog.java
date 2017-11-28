package com.hua.librarytools.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.hua.librarytools.R;

/**
 * Created by hjz on 2017/11/28 0028.
 * 自定义布局的dialog
 */

public class CustomLayoutDialog extends Dialog {

    public CustomLayoutDialog(Context context) {
        super(context);
    }

    public CustomLayoutDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public CustomLayoutDialog create(int res) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomLayoutDialog dialog = new CustomLayoutDialog(context, R.style.LibraryDialog);
            View layout = inflater.inflate(res, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            return dialog;
        }

        /**
         * @param res
         * @param widthPercentage  占宽的百分比（值得大小0到1之间）
         * @param heightPercentage 占高的百分比（值得大小0到1之间）
         * @return
         */
        public CustomLayoutDialog create(int res, float widthPercentage, float heightPercentage) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomLayoutDialog dialog = new CustomLayoutDialog(context, R.style.LibraryDialog);
            View layout = inflater.inflate(res, null);
            dialog.setContentView(layout);
            /*
             * 将对话框的大小按屏幕大小的百分比设置
             */
            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            int screenWidth = 0;
            int screenHeight = 0;
            if (screenWidth == 0 || screenHeight == 0){
                WindowManager wm = (WindowManager)context.getSystemService("window");
                int rotation = wm.getDefaultDisplay().getRotation();
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                screenWidth = rotation == 0 ? metrics.widthPixels:metrics.heightPixels;
                screenHeight = rotation == 0 ? metrics.heightPixels:metrics.heightPixels;
            }
            if (widthPercentage > 0){
                p.width = (int)(screenWidth * widthPercentage);
            }
            if (heightPercentage > 0){
                p.height = (int)(screenHeight * heightPercentage);
            }
            dialogWindow.setAttributes(p);
            return dialog;
        }
    }

}

