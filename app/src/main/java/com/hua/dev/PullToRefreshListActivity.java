package com.hua.dev;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hua.dev.adapter.PullToRefreshAdapter;
import com.hua.dev.base.RecommendModel;
import com.hua.dev.base.po.BaseActivity;
import com.hua.dev.network.TestApi;
import com.hua.librarytools.widget.refreshlist.PullToRefreshListView;
import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;

public class PullToRefreshListActivity extends BaseActivity {
    private PullToRefreshListView mListView;
    private int pageNo = 1;
    private int pageSize = 20;
    private PullToRefreshAdapter adapter;
    private List<RecommendModel.TopicListBean> lists = new ArrayList<>();


    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_pull_to_refresh_list);
    }

    @Override
    protected void findViewById() {
        mListView = (PullToRefreshListView) findViewById(R.id.price_discount_listview);
    }

    @Override
    protected void setListener() {
        mListView.setPullAndRefreshListViewListener(new PullToRefreshListView.PullAndRefreshListViewListener() {
            @Override
            public void onRefresh() {  //下拉刷新
                pageNo = 1;
                loadData();
            }

            @Override
            public void onLoadMore() {  //加载更多
                loadData();
            }
        });
    }

    @Override
    protected void init() {
        adapter = new PullToRefreshAdapter(this);
        mListView.setAdapter(adapter);
        mListView.setPullLoadEnable(true); //加载更多
//        mListView.setPullRefreshEnable(false);
        mListView.setTimeTag("PullToRefreshTest");  //保存当前刷新的时间在Preferences内存中
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
                        mListView.setPullLoadEnable(true);
                    }
                    lists.addAll(recommendModel.getTopicList());
                    adapter.setData(lists);
                    adapter.notifyDataSetChanged();
                    mListView.stopRefresh(true);
                    mListView.stopLoadMore();
                    int total = recommendModel.getTotal();
                    if (recommendModel.getTopicList() == null  || recommendModel.getTopicList().isEmpty()){
                        mListView.noMoreData("更多内容在圈子里哦");
                    }else{
                        if (pageNo < total){
                            pageNo++;
                        }else{
                            mListView.noMoreData("更多内容在圈子里哦");
                        }
                    }

                }

            }
        };
        //http://mrobot.pcauto.com.cn/s/xcbd/cms/recommendList.xsp?pageNo=1&pageSize=20
        TestApi.getRecommendList(subscriber,pageNo, pageSize);
    }

}
