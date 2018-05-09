package com.hua.dev;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.hua.dev.adapter.PullToRefreshAdapter;
import com.hua.dev.base.RecommendModel;
import com.hua.dev.base.po.BaseActivity;
import com.hua.dev.network.TestApi;
import com.hua.librarytools.smartrefresh.layout.SmartRefreshLayout;
import com.hua.librarytools.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.hua.librarytools.smartrefresh.layout.api.RefreshHeader;
import com.hua.librarytools.smartrefresh.layout.api.RefreshLayout;
import com.hua.librarytools.smartrefresh.layout.header.BezierRadarHeader;
import com.hua.librarytools.smartrefresh.layout.header.FalsifyHeader;
import com.hua.librarytools.smartrefresh.layout.listener.OnLoadMoreListener;
import com.hua.librarytools.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class SmartFalsifyHeaderRefreshLayoutActivity extends BaseActivity {
    private ListView mListView;
    private RefreshLayout refreshLayout;
    private int pageNo = 1;
    private int pageSize = 20;
    private PullToRefreshAdapter adapter;
    private List<RecommendModel.TopicListBean> lists = new ArrayList<>();


    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_smart_falsify_header_refresh_layout_list);
    }

    @Override
    protected void findViewById() {
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        //如果app下拉刷新风格要保持一致，建议在application中注册即可
        refreshLayout.setRefreshHeader(new FalsifyHeader(this));//也可以在 XML 中添加 FalsifyHeader
        mListView = (ListView) findViewById(R.id.demo_list_view);


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
        //触发自动刷新
        refreshLayout.autoRefresh();
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
