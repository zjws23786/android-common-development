package com.hua.dev;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hua.dev.base.po.BaseActivity;
import com.hua.librarytools.imageselector.utils.ImageSelectorUtils;
import com.hua.librarytools.utils.ActionSheetDialog;
import com.hua.librarytools.utils.FileUtils;
import com.hua.librarytools.utils.ProviderUtil;

import java.io.File;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mOnAddressPickerBtn;
    private Button mCacheBtn;
    private Button timeBtn;
    private Button activity_inside_choice_btn;
    private Button single_option_btn;
    private Button select_city_btn;
    private Button pull_down_refresh_btn;
    private Button bomb_box_btn;
    private Button selector_photo_btn;
    private Button test_jni_btn;
    private Button bottom_dialog_btn;
    private Button calendar_btn;

    public static final int REQUEST_CAMRARE=0x00000010; //相机
    private static final int REQUEST_CODE = 0x00000011; //相册

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void findViewById() {
        mOnAddressPickerBtn = (Button) findViewById(R.id.onAddressPicker_btn);
        mCacheBtn = (Button) findViewById(R.id.cache_btn);
        timeBtn = (Button) findViewById(R.id.time_btn);
        activity_inside_choice_btn = (Button) findViewById(R.id.activity_inside_choice_btn);
        single_option_btn = (Button) findViewById(R.id.single_option_btn);
        select_city_btn = (Button) findViewById(R.id.select_city_btn);
        pull_down_refresh_btn = (Button) findViewById(R.id.pull_down_refresh_btn);
        bomb_box_btn = (Button) findViewById(R.id.bomb_box_btn);
        selector_photo_btn = (Button) findViewById(R.id.selector_photo_btn);
        test_jni_btn = (Button) findViewById(R.id.test_jni_btn);
        bottom_dialog_btn = (Button) findViewById(R.id.bottom_dialog_btn);
        calendar_btn = (Button) findViewById(R.id.calendar_btn);
    }

    @Override
    protected void setListener() {
        mOnAddressPickerBtn.setOnClickListener(this);
        mCacheBtn.setOnClickListener(this);
        timeBtn.setOnClickListener(this);
        activity_inside_choice_btn.setOnClickListener(this);
        single_option_btn.setOnClickListener(this);
        select_city_btn.setOnClickListener(this);
        pull_down_refresh_btn.setOnClickListener(this);
        bomb_box_btn.setOnClickListener(this);
        selector_photo_btn.setOnClickListener(this);
        test_jni_btn.setOnClickListener(this);
        bottom_dialog_btn.setOnClickListener(this);
        calendar_btn.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.onAddressPicker_btn:
                intent = new Intent(MainActivity.this, AddressPickActivity.class);
                break;
            case R.id.cache_btn:
                intent = new Intent(MainActivity.this, ApplicationCacheActivity.class);
                break;
            case R.id.time_btn:
                intent = new Intent(MainActivity.this, AboutTimeActivity.class);
                break;
            case R.id.activity_inside_choice_btn:
                intent = new Intent(MainActivity.this, ActivityInsideChoiceActivity.class);
                break;
            case R.id.single_option_btn:
                intent = new Intent(MainActivity.this, SingleChoiceActivity.class);
                break;
            case R.id.select_city_btn:
                intent = new Intent(MainActivity.this, CityLocationActivity.class);
                break;
            case R.id.pull_down_refresh_btn:
                intent = new Intent(MainActivity.this, PullToRefreshListActivity.class);
                break;
            case R.id.bomb_box_btn:
                intent = new Intent(MainActivity.this, BombBoxActivity.class);
                break;
            case R.id.selector_photo_btn:
                intent = new Intent(MainActivity.this, ImageAllActivity.class);
                break;
            case R.id.test_jni_btn:
                intent = new Intent(MainActivity.this, JniActivity.class);
                break;
            case R.id.bottom_dialog_btn:
                new ActionSheetDialog(this)
                        .builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("拍照", Color.BLUE,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        getImageFromCamera();
                                    }
                                })
                        .addSheetItem("相册", Color.RED,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        ImageSelectorUtils.openPhoto(MainActivity.this, REQUEST_CODE, true, 0);
                                    }
                                }).show();
                break;
            case R.id.calendar_btn:
                intent = new Intent(MainActivity.this, CalendarActivity.class);
                break;
        }
        if(intent != null){
            startActivity(intent);
        }
    }

    protected void getImageFromCamera() {
        photoImgPath = FileUtils.generateImgePathInStoragePath(this);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File cropFile = new File(photoImgPath);

        Uri fileUri;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            fileUri = Uri.fromFile(cropFile);
        } else {
            /**
             * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider，并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            fileUri = FileProvider.getUriForFile(this, ProviderUtil.getFileProviderName(this), cropFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
        startActivityForResult(intent, REQUEST_CAMRARE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMRARE && resultCode == RESULT_OK ) {
            if (photoImgPath != null) {
                Uri uri = Uri.fromFile(new File(photoImgPath));
                Log.v("hjz",uri.toString());

            }

        }
    }
}
