package com.bwie.three.model;


import com.bwie.three.bean.ProductBean;
import com.bwie.three.net.OnNetListener;



public interface IProductModel {
    public void getProduct(String pid, OnNetListener<ProductBean> onNetListener);

}
