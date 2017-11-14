package com.hua.dev;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hua.dev.base.po.BaseActivity;
import com.hua.librarytools.utils.DataCleanManager;

public class ApplicationCacheActivity extends BaseActivity implements View.OnClickListener {
    private Button mGetCacheBtn;
    private Button mClearCacheBtn;
    private TextView mShowCacheTv;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_application_cache);
    }

    @Override
    protected void findViewById() {
        mGetCacheBtn = (Button) findViewById(R.id.get_cache_btn);
        mClearCacheBtn = (Button) findViewById(R.id.clear_cache_btn);
        mShowCacheTv = (TextView) findViewById(R.id.show_cache_tv);
    }

    @Override
    protected void setListener() {
        mGetCacheBtn.setOnClickListener(this);
        mClearCacheBtn.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_cache_btn:
                mShowCacheTv.setText(DataCleanManager.getTotalCacheSize(this));
                break;
            case R.id.clear_cache_btn:
                DataCleanManager.clearAllCache(this);
                mShowCacheTv.setText(DataCleanManager.getTotalCacheSize(this));
                break;
        }
    }
}
