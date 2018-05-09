package com.hua.dev;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.hua.dev.adapter.PullToRefreshAdapter;
import com.hua.dev.base.RecommendModel;
import com.hua.dev.base.po.BaseActivity;
import com.hua.dev.network.TestApi;
import com.hua.librarytools.widget.refreshlist.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class SwipeRefreshLayoutActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ListView mListView;
    private int pageNo = 1;
    private int pageSize = 20;
    private PullToRefreshAdapter adapter;
    private SwipeRefreshLayout mSwipeLayout;
    private List<RecommendModel.TopicListBean> lists = new ArrayList<>();


    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_swipe_refresh_layout_list);
    }

    @Override
    protected void findViewById() {
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.demo_swiperefreshlayout);
        mListView = (ListView) findViewById(R.id.demo_list_view);


    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void init() {
        //设置进度条的颜色主题，最多能设置四种 加载颜色是循环播放的，只要没有完成刷新就会一直循环，holo_blue_bright>holo_green_light>holo_orange_light>holo_red_light
//         mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

        //上面的方法已经废弃
        mSwipeLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        mSwipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);

        //设置下拉刷新的监听
        mSwipeLayout.setOnRefreshListener(this);

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
                if(recommendModel != null){
                    if (pageNo == 1){
                        lists.clear();
                    }
                    lists.addAll(recommendModel.getTopicList());
                    adapter.setData(lists);
                    adapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                    int total = recommendModel.getTotal();
                    if (pageNo < total){
                        pageNo++;
                    }
                }

            }
        };
        //http://mrobot.pcauto.com.cn/s/xcbd/cms/recommendList.xsp?pageNo=1&pageSize=20
        TestApi.getRecommendList(subscriber,pageNo, pageSize);
    }

    //当下拉刷新后触发
    @Override
    public void onRefresh() {
        pageNo = 1;
        loadData();
    }

}
