package com.hua.dev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hua.dev.base.po.BaseActivity;
import com.hua.librarytools.selectorphoto.ImageSelector;

import java.util.List;

public class SelectorPhotoActivity extends BaseActivity {
    private static final int REQUEST_CODE_SELECT_IMG = 1;
    private static final int MAX_SELECT_COUNT = 9;

    private TextView mContentTv;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_selector_photo);
    }

    @Override
    protected void findViewById() {
        mContentTv = (TextView) findViewById(R.id.content);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SELECT_IMG) {
            showContent(data);
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showContent(Intent data) {
        List<String> paths = ImageSelector.getImagePaths(data);
        if (paths.isEmpty()) {
            mContentTv.setText("没有选择图片");
            return;
        }

        mContentTv.setText(paths.toString());
    }

    public void selectImg(View v) {
        ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT);
    }
}
