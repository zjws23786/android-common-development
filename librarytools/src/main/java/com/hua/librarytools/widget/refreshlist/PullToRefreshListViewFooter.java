package com.hua.librarytools.widget.refreshlist;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hua.librarytools.R;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class PullToRefreshListViewFooter extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_LOADING = 2;
    public final static int STATE_NOMORE = 3;

    private Context mContext;
    private RelativeLayout mContainer;
    private View mContentView;
    private LinearLayout mLoadLayout;
    private TextView mLoadView;
    private TextView mHintView;
    private View mHintLayout;
    private View mHintLeftLine;
    private View mHintRightLine;

    public PullToRefreshListViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public PullToRefreshListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setState(int state) {
        if (state == STATE_NORMAL) {
            mLoadLayout.setVisibility(View.GONE);
            mHintLayout.setVisibility(View.GONE);
            mHintLeftLine.setVisibility(View.GONE);
            mHintRightLine.setVisibility(View.GONE);
        } else if (state == STATE_LOADING) {
            mLoadView.setText("加载中…");
            mLoadLayout.setVisibility(View.VISIBLE);
            mHintLayout.setVisibility(View.INVISIBLE);
            mHintLeftLine.setVisibility(View.INVISIBLE);
            mHintRightLine.setVisibility(View.INVISIBLE);
        } else if (state == STATE_NOMORE) {
            mHintView.setText("没有更多内容");
            mLoadLayout.setVisibility(View.GONE);
            mHintLayout.setVisibility(View.VISIBLE);
            mHintLeftLine.setVisibility(View.VISIBLE);
            mHintRightLine.setVisibility(View.VISIBLE);
        }
    }

    public void setState(int state, String hint) {
        if (state == STATE_NORMAL) {
            mLoadLayout.setVisibility(View.GONE);
            mHintLayout.setVisibility(View.GONE);
            mHintLeftLine.setVisibility(View.GONE);
            mHintRightLine.setVisibility(View.GONE);
        } else if (state == STATE_LOADING) {
            mLoadView.setText("加载中…");
            mLoadLayout.setVisibility(View.VISIBLE);
            mHintLayout.setVisibility(View.INVISIBLE);
            mHintLeftLine.setVisibility(View.INVISIBLE);
            mHintRightLine.setVisibility(View.INVISIBLE);
        } else if (state == STATE_NOMORE) {
            mHintView.setText(hint);
            mLoadLayout.setVisibility(View.GONE);
            mHintLayout.setVisibility(View.VISIBLE);
            mHintLeftLine.setVisibility(View.VISIBLE);
            mHintRightLine.setVisibility(View.VISIBLE);
        }
    }

    public void setGoneOrVisible(int visible){
        mContentView.setVisibility(visible);
    }

    public void setBottomMargin(int height) {
        if (height < 0) return ;
        LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }

    public int getBottomMargin() {
        LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
        return lp.bottomMargin;
    }


    /**
     * normal status
     */
    public void normal() {
        mHintLayout.setVisibility(View.VISIBLE);
        mLoadLayout.setVisibility(View.GONE);
    }


    /**
     * loading status
     */
    public void loading() {
        mHintLayout.setVisibility(View.GONE);
        mLoadLayout.setVisibility(View.VISIBLE);
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
        lp.height = 0;
        mContentView.setLayoutParams(lp);
    }

    /**
     * show footer
     */
    public void show() {
        LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        mContentView.setLayoutParams(lp);
    }

    private void initView(Context context) {
        mContext = context;
        mContainer = (RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.app_pull_listview_footer, null);
        addView(mContainer);
        mContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        mContentView = mContainer.findViewById(R.id.xlistview_footer_content);
        mLoadLayout = (LinearLayout) mContainer.findViewById(R.id.xlistview_footer_load_more_layout);
        mLoadView = (TextView) mContainer.findViewById(R.id.xlistview_footer_load_textview);
        mHintView = (TextView)mContainer.findViewById(R.id.xlistview_footer_hint_textview);
        mHintLayout = findViewById(R.id.xlistview_footer_hint_text_layout);
        mHintLeftLine = findViewById(R.id.xlistview_footer_hint_left_line);
        mHintRightLine = findViewById(R.id.xlistview_footer_hint_right_line);
    }

}
