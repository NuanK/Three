package com.bwie.three.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.bwie.three.bean.ProductBean;
import com.bwie.three.net.Api;
import com.bwie.three.net.OkHttpUtils;
import com.bwie.three.net.OnNetListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class ProductModel implements IProductModel{

    private Handler handler=new Handler(Looper.getMainLooper());

    public void getGoods(String pid, final OnNetListener<ProductBean> onNetListener){
        //拼接参数
        Map<String,String> params = new HashMap<>();
        params.put("pid",pid);
//        params.put("source","android");
        //调用网络请求获取数据
        OkHttpUtils.getHttpUtils().doPost(Api.PRODUCT, params, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.OnFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final ProductBean productBean = new Gson().fromJson(string, ProductBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.OnSuccess(productBean);
                    }
                });
            }
        });
    }
}
