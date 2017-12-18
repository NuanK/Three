package com.bwie.three.net;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;



public class OkHttpUtils {
    private static OkHttpUtils httpUtils;
    private static OkHttpClient client;

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
