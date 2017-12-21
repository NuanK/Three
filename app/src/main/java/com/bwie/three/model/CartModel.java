package com.bwie.three.model;

import android.os.Handler;
import android.os.Looper;

import com.bwie.three.bean.CartBean;
import com.bwie.three.net.Api;
import com.bwie.three.net.OkHttpUtils;
import com.bwie.three.net.OnNetListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class CartModel implements ICartModel{
    public Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void getCart(String uid, final OnNetListener<CartBean> onNetListener) {
        //拼接参数
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
//        params.put("source", "android");
        //调用网络请求获取数据
        OkHttpUtils.getHttpUtils().doPost(Api.CART, params, new Callback() {
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
                final CartBean cartBean = new Gson().fromJson(string, CartBean.class);
                List<CartBean.DataBean> data = cartBean.getData();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.OnSuccess(cartBean);
                    }
                });
            }
        });
    }
}
