package com.bwie.three.model;

import com.bwie.three.bean.CartBean;
import com.bwie.three.net.OnNetListener;



public interface ICartModel {
    public void getCart(String uid, OnNetListener<CartBean>onNetListener);
}
