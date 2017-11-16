package com.hua.dev.network;

import android.content.Intent;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

/**
 * 应用拦截器
 */

public class ApplicationInterceptors implements Interceptor {
    public static final String TAG = "ApplicationInterceptors";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest= chain.request();
        Response originResponse = chain.proceed(originalRequest);
        // originResponse.body().string();调用一次之后流就会被关闭，在回调就会报错close
        // copy一份Response
        MediaType mediaType = originResponse.body().contentType();
        String content= originResponse.body().string();
        originResponse.close();
//        GLog.e(TAG,content);
//        BaseObjPo response= JSON.parseObject(content,BaseObjPo.class);
//        if (response.getRcode() == 33){//用户session失效
//            Request loginRequest = getLoginRequest(); //token失效重登陆
//            if (loginRequest == null){
//                return originResponse.newBuilder()
//                        .body(ResponseBody.create(mediaType, content))
//                        .build();
//            }else{
//                Response loginResponse=chain.proceed(loginRequest);
//                String loginContent=loginResponse.body().string();
//                BaseObjPo loginBaseResponse=JSON.parseObject(loginContent,BaseObjPo.class);
//                if (loginBaseResponse.getRcode()==0){
////                    SharedPreferenceUtils.setPreferences(MyApplication.getInstance(), Constant.ACCOUNT_PRE,
////                            Constant.ACCESS_TOKEN_KEY, JSON.parseObject(loginBaseResponse.getData().toString()).getString("access_token"));
//                    loginResponse.close();
//                    Response retryResponse = chain.proceed(originalRequest);
//                    return retryResponse;
//                }else {
//                    return originResponse.newBuilder()
//                            .body(ResponseBody.create(mediaType, content))
//                            .build();
//                }
//            }
//        }
        return originResponse.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }

    //session失效重新登陆
    public Request getLoginRequest() {
//        AccountModel.Account account = AccountUtils.getLoginAccount(MyApplication.getInstance());
//        String userName = account.getMobile();
//        String passWord = account.getPassword();
//        if (userName==null || passWord==null){
//            Intent intent = new Intent(MyApplication.getInstance(), LoginActivity.class);
//            MyApplication.getInstance().startActivity(intent);
//            return null;
//        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("mobile", userName);
//        map.put("password", passWord);
//        final String params = BaseNetworkManager.processingParametersNew(map);
//        return new Request.Builder()
//                .url(BaseNetworkManager.API_SERVER + "n/login")
//                .post(new RequestBody() {
//                    @Override
//                    public MediaType contentType() {
//                        return null;
//                    }
//
//                    @Override
//                    public void writeTo(BufferedSink sink) throws IOException {
//                        sink.writeUtf8(params);
//                    }
//                })
//                .build();
        return null;
    }

}
