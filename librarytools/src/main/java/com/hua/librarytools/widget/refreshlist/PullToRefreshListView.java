package com.hua.librarytools.widget.refreshlist;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.hua.librarytools.R;
import com.hua.librarytools.utils.PreferencesUtils;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/11/14 0014.
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * Implement IXListViewListener, and see stopRefresh() / stopLoadMore().
 */

public class PullToRefreshListView extends ListView implements AbsListView.OnScrollListener {

    private float mLastY = -1; // save event y
    private Scroller mScroller; // used for scroll back
    private OnScrollListener mScrollListener; // user's scroll listener

    // the interface to trigger refresh and load more.
    private PullAndRefreshListViewListener pullAndRefreshListViewListener;  //下拉刷新和上拉加载更多的监听器

    // -- header view
    private PullToRefreshListViewHeader mHeaderView;
    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private RelativeLayout mHeaderViewContent;
    private TextView mHeaderTimeView;
    private int mHeaderViewHeight; // header view's height
    private boolean mEnablePullRefresh = true;
    private boolean mPullRefreshing = false; // is refreashing.
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");

    // -- footer view
    private PullToRefreshListViewFooter mFooterView;
    private RelativeLayout mFooterViewContent;
    private int mFooterViewHeight; // footer view's height
    private boolean mEnablePullLoad;
    private boolean mPullLoading;
    private boolean mIsFooterReady = false;

    // total list items, used to detect is at the bottom of listview.
    //统计listview总itme = mTotalItemCount - 2（头部+底部）
    private int mTotalItemCount;
    // for mScroller, scroll back from header or footer.
    private int mScrollBack;
    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;
    private GestureDetector detector = new GestureDetector(new YScrollDetector());
    private final static int SCROLL_DURATION = 400; // scroll back duration
    // private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >=
    // 50px
    // at bottom, trigger
    // load more.
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
    // feature.

    private long refreshTime = 0;
    private Context context = null;
    private String timeTag = "";
    private int totalItemCount = 1000;
    private static final int LAST_REFRESH_TIME = 1000;

    public String getTimeTag() {
        return timeTag;
    }

    public void setTimeTag(String timeTag) {
        this.timeTag = timeTag;
        String last = getLastRefreshTime();
        mHeaderTimeView.setText(last);
    }

    /**
     * @param context
     */
    public PullToRefreshListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        //OVER_SCROLL_NEVER 不允许用户过度滚动此视图
        this.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //滚动由快变慢的过程
        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        super.setOnScrollListener(this);
        this.context = context;
        // init header view
        mHeaderView = new PullToRefreshListViewHeader(context);
        mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
        mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_time);
        addHeaderView(mHeaderView);

        // init footer view
        mFooterView = new PullToRefreshListViewFooter(context);
        mFooterViewContent = (RelativeLayout) mFooterView.findViewById(R.id.xlistview_footer_content);
        addFooterView(mFooterView);

        // init header height   注册一个观察者来监听视图树
        // 当视图树的布局、视图树的焦点、视图树将要绘制、视图树滚动等发生改变时，ViewTreeObserver都会收到通知，
        // ViewTreeObserver不能被实例化，可以调用View.getViewTreeObserver()来获得
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
                //当视图树的布局发生改变或者View在视图树的可见状态发生改变时会调用的接口
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeaderViewHeight = mHeaderViewContent.getHeight();
                        getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });

        // init footer height
        mFooterView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mFooterViewHeight = mFooterViewContent.getHeight();
                        getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    /**
     * enable or disable pull down refresh feature.
     * 启用或禁用向下刷新功能
     * enable == true 启用
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh) { // disable, hide the content
            mHeaderViewContent.setVisibility(View.GONE);
        } else {
            mHeaderViewContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * enable or disable pull up load more feature.
     * 启用或禁用上拉加载更多功能
     *
     * @param enable  enable==true 启用
     */
    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            mFooterView.hide();
        } else {
            mPullLoading = false;
            mFooterView.show();
            mFooterView.setState(PullToRefreshListViewFooter.STATE_NORMAL);
        }
    }

    /**
     * stop refresh, reset header view.
     * 停止刷新
     */
    public void stopRefresh() {
        if (mPullRefreshing == true) {
            mPullRefreshing = false;
            long times = System.currentTimeMillis() - refreshTime;
            if (times >= LAST_REFRESH_TIME) {
                resetHeaderHeight();
                changeRefreshTime();
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetHeaderHeight();
                        changeRefreshTime();
                    }
                }, LAST_REFRESH_TIME - times);
            }
            mHeaderView.setState(PullToRefreshListViewHeader.STATE_NORMAL);
        }
    }

    /**
     * stop refresh, reset header view.
     * 停止刷新
     * @param stop
     */
    public void stopRefresh(boolean stop) {
        if (stop) {
            mPullRefreshing = true;
        }
        stopRefresh();
    }

    /**
     * 时间发生改变，重新刷新
     */
    private void changeRefreshTime() {
        // 2 重置刷新时间
        setRefreshTime();
        String last = getLastRefreshTime();
        mHeaderTimeView.setText(last);
    }

    /**
     * stop load more, reset footer view.
     */
    public void stopLoadMore() {
        // if (mPullLoading == true) {
        mPullLoading = false;
        mFooterView.hide();
        mFooterView.setState(PullToRefreshListViewFooter.STATE_NORMAL);
        // }
    }

    /**
     * 到了底页没有数据的提示
     * @param hint  提示语
     */
    public void noMoreData(String hint) {
        // if (mPullLoading == true) {
        mPullLoading = false;
        mEnablePullLoad = false;
//		mFooterView.hide();
        mFooterView.show();
        mFooterView.setState(PullToRefreshListViewFooter.STATE_NOMORE, hint);
        // }
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener l = (OnXScrollListener) mScrollListener;
            l.onXScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta) {
        mHeaderView.setVisiableHeight((int) delta + mHeaderView.getVisiableHeight());
        if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                mHeaderView.setState(PullToRefreshListViewHeader.STATE_READY);
            } else {
                mHeaderView.setState(PullToRefreshListViewHeader.STATE_NORMAL);
            }
        }
        setSelection(0); // scroll to top each time  每次滚动到顶部
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return;
        }
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);
        // trigger computeScroll
        invalidate();
    }

    /**
     * reset header view's height.
     */
    private void resetFooterHeight() {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0)
            return;
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return;
        }
        int finalHeight = 0;
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
        invalidate();
    }

    public void showHeaderAndRefresh() {
        // 1 改变当前显示的时间，以便下一次显示
        String last = getLastRefreshTime();
        mHeaderTimeView.setText(last);
        mPullRefreshing = true;
        mHeaderView.setState(PullToRefreshListViewHeader.STATE_REFRESHING);
        if (pullAndRefreshListViewListener != null) {
            refreshTime = System.currentTimeMillis();
            pullAndRefreshListViewListener.onRefresh();
        }
        // 魅族手机获取不到 mHeaderView.getVisiableHeight()的高度，这里手动指定高度，为了解决弹上去的bug
        mHeaderView.setVisiableHeight(mHeaderViewHeight);
        mScroller.startScroll(0, 0, 0, mHeaderViewHeight, 1);
        invalidate();
    }

    public void refresh() {
        if (pullAndRefreshListViewListener != null) {
            refreshTime = System.currentTimeMillis();
            pullAndRefreshListViewListener.onRefresh();
        }
    }

    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.show();
        mFooterView.setState(PullToRefreshListViewFooter.STATE_LOADING);
        if (pullAndRefreshListViewListener != null) {
            pullAndRefreshListViewListener.onLoadMore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;

                if (!mEnablePullRefresh && deltaY >= 0
                        && getFirstVisiblePosition() == 0) {// 不下拉刷新
                    // 的时候不滑动头部
                    return false;
                }
                mLastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0 && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();
                } else if (getLastVisiblePosition() == mTotalItemCount - 2) {
                    // last item, already pulled up or want to pull up.
                    // updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;
            default:
                mLastY = -1; // reset
                if (getFirstVisiblePosition() == 0) {
                    // invoke refresh
                    if (mEnablePullRefresh && ev.getPointerCount() == 1
                            && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                        mPullRefreshing = true;
                        mHeaderView.setState(PullToRefreshListViewHeader.STATE_REFRESHING);
                        if (pullAndRefreshListViewListener != null) {
                            refreshTime = System.currentTimeMillis();
                            pullAndRefreshListViewListener.onRefresh();
                        }
                    }
                    resetHeaderHeight();
                } else if (getLastVisiblePosition() == mTotalItemCount - 2) {
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight(mScroller.getCurrY());
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
        int lastVisiPos = this.getLastVisiblePosition();
        if (lastVisiPos == this.totalItemCount - 1 && !mPullLoading
                && !mPullRefreshing && mEnablePullLoad) {
            startLoadMore();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
        // send to user's listener
        mTotalItemCount = totalItemCount;
//        Log.v("hjz","mTotalItemCount="+mTotalItemCount);
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,totalItemCount);
        }
        this.totalItemCount = totalItemCount;
    }

    // @Override
    // public boolean onInterceptTouchEvent(MotionEvent ev) {
    // return super.onInterceptTouchEvent(ev)&&detector.onTouchEvent(ev);
    // }
    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if (distanceY != 0 && distanceX != 0) {

            }
            if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }

    public void setPullAndRefreshListViewListener(PullAndRefreshListViewListener l) {
        pullAndRefreshListViewListener = l;
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     */
    public interface OnXScrollListener extends OnScrollListener {
        public void onXScrolling(View view);
    }

    /**
     * implements this interface to get refresh/load more event.
     */
    public interface PullAndRefreshListViewListener {
        public void onRefresh();

        public void onLoadMore();
    }

    private void setRefreshTime() {
        long currentTime = System.currentTimeMillis();
        PreferencesUtils.setPreferences(this.context, "PullToRefresh", timeTag,currentTime);
    }

    private String getLastRefreshTime() {
        long lastRefreshTime = PreferencesUtils.getPreference(this.context,"PullToRefresh", timeTag, System.currentTimeMillis());
        String time = dateFormat.format(lastRefreshTime);
        return time;
    }

}