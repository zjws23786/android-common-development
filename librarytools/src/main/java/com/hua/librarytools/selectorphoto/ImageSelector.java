package com.hua.librarytools.selectorphoto;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.hua.librarytools.selectorphoto.activity.ImageSelectorActivity;

import java.util.List;

/**
 * @author hjz
 * @date 2018/1/19 0019
 * @description
 */

public class ImageSelector {
    private ImageSelector() {
    }

    public static void show(@NonNull Activity activity, int resquestCode) {
        show(activity, resquestCode, 0);
    }

    public static void show(@NonNull Activity activity, int resquestCode, int maxCount) {
        ImageSelectorActivity.show(activity, resquestCode, maxCount);
    }

    public static List<String> getImagePaths(Intent data) {
        return ImageSelectorActivity.getImagePaths(data);
    }
}