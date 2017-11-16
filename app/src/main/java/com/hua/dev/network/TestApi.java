package com.hua.dev.network;

import com.hua.dev.base.RecommendModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class TestApi extends BaseNetworkManager {
    protected static final TestApi.TestService service = getRetrofit().create(TestApi.TestService.class);

    private interface TestService {
        @GET("s/xcbd/cms/recommendList.xsp")
        Observable<RecommendModel> getRecommendList(@QueryMap Map<String, Object> map);
    }

    public static void getRecommendList(Subscriber<RecommendModel> subscriber, Integer pageNo, Integer pageSize) {
        Map requestParams = new HashMap();
        if (pageNo != null) requestParams.put("pageNo",pageNo);
        if (pageSize != null) requestParams.put("pageSize",pageSize);
        String url = "http://mrobot.pcauto.com.cn";
        TestApi.TestService serviceTest = getRetrofitUrl(url).create(TestApi.TestService.class);
        Observable observable = serviceTest.getRecommendList(requestParams);
        toSubscribe(observable, subscriber);
    }

}
