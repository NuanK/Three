package com.bwie.three.model;


import com.bwie.three.bean.PayBean;
import com.bwie.three.net.IOkHttpUtils;
import com.bwie.three.net.OkHttpUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;

public class PayModel {
    public void receive(String uid, String urlTitle, final IPayModel iPayModel) {
        OkHttpUtils instance = OkHttpUtils.getHttpUtils();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        if(urlTitle.trim().equals("9")){

        }else{
            hashMap.put("status",urlTitle);
        }
        instance.doGet("http://120.27.23.105/product/getOrders", hashMap, new IOkHttpUtils() {
            @Override
            public void onSuccess(String str) {
                if (str != null) {
                    Gson gson = new Gson();
                   PayBean PayBean = gson.fromJson(str, PayBean.class);
                    if (PayBean != null) {
                        List<PayBean.DataBean> data = PayBean.getData();
                        if (data != null) {
                            iPayModel.onSuccess(data);
                        }
                    }
                }
            }

            @Override
            public void onFailed(String message) {
                iPayModel.onFailed();
            }
        });
    }
}
