package com.bwie.three.net;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkHttpUtils {
    private static OkHttpUtils httpUtils;
    private static OkHttpClient client;

    private static Handler handler = new Handler();
    public OkHttpUtils(){
        client=new OkHttpClient.Builder()
                .addInterceptor(new MyInterceptor())
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .build();
    }


    public static OkHttpUtils getHttpUtils(){
        if (httpUtils==null){
            synchronized (OkHttpUtils.class){
                if (httpUtils==null){
                    httpUtils=new OkHttpUtils();
                }
            }
        }
        return httpUtils;
    }

    /**
     * 封装的异步Get请求
     */
    public void doGet(String path, Map<String, String> map, final IOkHttpUtils okHttpCallBack) {
        //创建一个字符串容器
        StringBuilder sb = null;
        if (map.size() == 0) {
            if (null == sb) {
                sb = new StringBuilder();
                sb.append(path);
            }
        } else {
            for (String key : map.keySet()) {
                if (null == sb) {
                    sb = new StringBuilder();
                    sb.append(path);
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key).append("=").append(map.get(key));
            }
        }


        //System.out.println("分类 : "+path + sb.toString());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new MyInterceptor())//使用拦截器
                .build();
        Request request = new Request.Builder()
                .url(sb.toString())
                .get()
                .build();
        //OKHttp 网络
        Call call = okHttpClient.newCall(request);
        //异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
                Log.e("SKN", "OK请求失败");
                okHttpCallBack.onFailed(e.getMessage());
            }


            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String str = response.body().string();
                //请求成功
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("WWWW", "请求成功" + str);
                        okHttpCallBack.onSuccess(str);
                    }
                });
            }
        });
    }

    public void doGet(String url, Callback callback){
        Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

    public void doPost(String url, Map<String,String>params,Callback callback){
        if (params==null){
            throw new RuntimeException("参数空了");
        }else {
            FormBody.Builder builder=new FormBody.Builder();
            for (Map.Entry<String,String>entry:params.entrySet()) {
                builder.add(entry.getKey(),entry.getValue());
            }

            FormBody formBody=builder.build();
            Request request=new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            client.newCall(request).enqueue(callback);
        }
    }

}
