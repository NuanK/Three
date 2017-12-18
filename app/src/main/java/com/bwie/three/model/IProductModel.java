package com.bwie.three.model;


import com.bwie.three.bean.ProductBean;
import com.bwie.three.net.OnNetListener;



public interface IProductModel {
    //获取商品详情方法
    public void getGoods(String pid, OnNetListener<ProductBean> onNetListener);

}
