package com.hua.dev.network;

import com.alibaba.fastjson.JSON;
import com.hua.dev.MyApplication;
import com.hua.dev.base.po.BaseListPo;
import com.hua.dev.base.po.BaseObjPo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/15 0015.
 * 网络请求相关配置
 */

public class BaseNetworkManager {
    public  static String API_SERVER = "http://api.chebaotec.com/ppcappalpha/";

    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;
    private static Retrofit.Builder mRetrofitBuilder;
    protected static Retrofit getRetrofit() {
        initOkHttpClient();
        if (mRetrofit == null) {
            //构建Retrofit
            mRetrofit = new Retrofit.Builder()
                    //配置服务器路径
                    .baseUrl(API_SERVER)
                    //配置转化库，默认是Gson
                    .addConverterFactory(FastJsonConverterFactory.create())
                    //配置回调库，采用RxJava
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    //设置OKHttpClient为网络客户端
                    .client(mOkHttpClient)
                    .build();
        }
        return mRetrofit;
    }

    protected static Retrofit getRetrofitUrl(String url) {
        initOkHttpClient();
        if (mRetrofitBuilder == null) {
            //构建Retrofit
            mRetrofitBuilder = new Retrofit.Builder()
                    //配置服务器路径
//                    .baseUrl(API_SERVER)
                    //配置转化库，默认是Gson
                    .addConverterFactory(FastJsonConverterFactory.create())
                    //配置回调库，采用RxJava
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    //设置OKHttpClient为网络客户端
                    .client(mOkHttpClient);
        }
        mRetrofitBuilder.baseUrl(url);
        return mRetrofitBuilder.build();
    }

    /**
     * 初始化OKHTTP
     */

    private static void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (BaseNetworkManager.class) {
                if (mOkHttpClient == null) {
                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(MyApplication.getInstance().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .cookieJar(new CookiesManager())
                            .addInterceptor(interceptor)
//                            .addInterceptor(new ApplicationInterceptors())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20,TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    /**
     * 自动管理Cookies
     */
    public static class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(MyApplication.getInstance());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }

    /**
     * 处理请求参数加密
     *
     * @param map
     * @return
     */
    protected static Map processingParameters(Map map) {
        Map fieldMap = new HashMap();
        BaseHttpRequest request = new BaseHttpRequest();
        String datacontent = JSON.toJSONString(map);
//        request.setData(Ptlmaner.eryt(datacontent));
        request.setData(datacontent);  //不加密
        fieldMap.put("data", request.getData());
        return fieldMap;
    }

    /**
     * 处理请求参数加密
     *
     * @param map
     * @return
     */
    protected static String processingParametersNew(Map map) {
        BaseHttpRequest request = new BaseHttpRequest();
        String datacontent = JSON.toJSONString(map);
        request.setData(datacontent);
        return request.getData();
    }

    /**
     * 处理请求参数不加密（以key为data形式）
     *
     * @param map
     * @return
     */
    protected static Map notEncrypted(Map map) {
        Map fieldMap = new HashMap();
        BaseHttpRequest request = new BaseHttpRequest();
        String datacontent = JSON.toJSONString(map);
        request.setData(datacontent);
        fieldMap.put("data", request.getData());
        return fieldMap;
    }

    //订阅
    protected static <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public static  class HttpResultList<T> implements Func1<BaseListPo<T>, T> {

        @Override
        public T call(BaseListPo<T> httpResult) {
            if (httpResult.getRcode() != 0) {
                if (httpResult.getRcode() == 21){
                    throw new ApiException(21);
                }else{
                    throw new ApiException(100);
                }

            }
            return (T) httpResult.getData();
        }
    }

    /**
     * 返回最原始数据
     * @param <T>
     */
    public static class HttpResultOriginalData<T> implements Func1<BaseListPo<T>,T>{


        @Override
        public T call(BaseListPo<T> tBaseListPo) {
            return (T) tBaseListPo;
        }
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public static  class HttpResultObject<T> implements Func1<BaseObjPo<T>, T> {

        @Override
        public T call(BaseObjPo<T> httpResult) {
            if (httpResult.getRcode() != 0) {
                if (httpResult.getRcode() == ApiException.NOT_DATA){
                    throw new ApiException(ApiException.NOT_DATA);
                }else {
                    throw new ApiException(100);
                }
            }
            return (T) httpResult.getData();
        }
    }

}
