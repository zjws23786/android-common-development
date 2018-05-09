package com.hua.dev;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hua.dev.adapter.PullToRefreshAdapter;
import com.hua.dev.base.RecommendModel;
import com.hua.dev.base.po.BaseActivity;
import com.hua.dev.network.TestApi;
import com.hua.dev.utils.DynamicTimeFormat;
import com.hua.dev.utils.StatusBarUtil;
import com.hua.librarytools.smartrefresh.layout.SmartRefreshLayout;
import com.hua.librarytools.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.hua.librarytools.smartrefresh.layout.api.OnTwoLevelListener;
import com.hua.librarytools.smartrefresh.layout.api.RefreshHeader;
import com.hua.librarytools.smartrefresh.layout.api.RefreshLayout;
import com.hua.librarytools.smartrefresh.layout.header.ClassicsHeader;
import com.hua.librarytools.smartrefresh.layout.header.FalsifyHeader;
import com.hua.librarytools.smartrefresh.layout.header.TwoLevelHeader;
import com.hua.librarytools.smartrefresh.layout.listener.OnLoadMoreListener;
import com.hua.librarytools.smartrefresh.layout.listener.OnRefreshListener;
import com.hua.librarytools.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.hua.librarytools.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class SmartTwoLevelHeaderRefreshLayoutActivity extends BaseActivity {
    private ListView mListView;
    private RefreshLayout refreshLayout;
    private TwoLevelHeader header;
    private int pageNo = 1;
    private int pageSize = 20;
    private PullToRefreshAdapter adapter;
    private List<RecommendModel.TopicListBean> lists = new ArrayList<>();


    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_smart_refresh_layout_two_level);
    }

    @Override
    protected void findViewById() {
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        final View floor = findViewById(R.id.secondfloor);
        mListView = (ListView) findViewById(R.id.demo_list_view);
        final RelativeLayout toolbar = (RelativeLayout)findViewById(R.id.toolbar);
        header = (TwoLevelHeader)findViewById(R.id.header);

        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Toast.makeText(SmartTwoLevelHeaderRefreshLayoutActivity.this,"触发刷新事件",Toast.LENGTH_SHORT).show();
                refreshLayout.finishRefresh(2000);
            }
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                toolbar.setAlpha(1 - Math.min(percent, 1));
                floor.setTranslationY(Math.min(offset - floor.getHeight() + toolbar.getHeight() , refreshLayout.getLayout().getHeight() - floor.getHeight()));
            }
//            @Override
//            public void onHeaderPulling(@NonNull RefreshHeader header, float percent, int offset, int bottomHeight, int maxDragHeight) {
//                toolbar.setAlpha(1 - Math.min(percent, 1));
//                floor.setTranslationY(Math.min(offset - floor.getHeight() + toolbar.getHeight(), refreshLayout.getLayout().getHeight() - floor.getHeight()));
//            }
//            @Override
//            public void onHeaderReleasing(@NonNull RefreshHeader header, float percent, int offset, int bottomHeight, int maxDragHeight) {
//                toolbar.setAlpha(1 - Math.min(percent, 1));
//                floor.setTranslationY(Math.min(offset - floor.getHeight() + toolbar.getHeight(), refreshLayout.getLayout().getHeight() - floor.getHeight()));
//            }
        });

        header.setOnTwoLevelListener(new OnTwoLevelListener() {
            @Override
            public boolean onTwoLevel(@NonNull RefreshLayout refreshLayout) {
                Toast.makeText(SmartTwoLevelHeaderRefreshLayoutActivity.this,"触发二楼事件",Toast.LENGTH_SHORT).show();
                findViewById(R.id.secondfloor_content).animate().alpha(1).setDuration(2000);
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        header.finishTwoLevel();
                        findViewById(R.id.secondfloor_content).animate().alpha(0).setDuration(1000);
                    }
                },5000);
                return true;//true 将会展开二楼状态 false 关闭刷新
            }
        });

//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                Toast.makeText(getContext(),"触发刷新事件",Toast.LENGTH_SHORT).show();
//                refreshLayout.finishRefresh(2000);
//            }
//        });

        //状态栏透明和间距处理
//        StatusBarUtil.immersive(this);
//        StatusBarUtil.setMargin(this,  findViewById(R.id.classics));
//        StatusBarUtil.setPaddingSmart(this, findViewById(R.id.toolbar));
//        StatusBarUtil.setPaddingSmart(this, findViewById(R.id.contentPanel));

    }

    @Override
    protected void setListener() {
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                loadData();
                refreshLayout.finishRefresh();
                refreshLayout.setNoMoreData(false);
            }
        });
        //加载更多
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
    }

    @Override
    protected void init() {

        adapter = new PullToRefreshAdapter(this);
        mListView.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        Subscriber<RecommendModel> subscriber = new Subscriber<RecommendModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RecommendModel recommendModel) {
                header.setVisibility(View.VISIBLE);
                if(recommendModel != null){
                    if (pageNo == 1){
                        lists.clear();
                    }
                    lists.addAll(recommendModel.getTopicList());
                    adapter.setData(lists);
                    adapter.notifyDataSetChanged();
                    int total = recommendModel.getTotal();
//                    if (pageNo < total){
                    if (pageNo < 5){
                        pageNo++;
                        refreshLayout.finishLoadMore();
                    }else{
                        refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                    }
                }

            }
        };
        //http://mrobot.pcauto.com.cn/s/xcbd/cms/recommendList.xsp?pageNo=1&pageSize=20
        TestApi.getRecommendList(subscriber,pageNo, pageSize);
    }

}
