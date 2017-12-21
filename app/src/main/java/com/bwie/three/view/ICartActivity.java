package com.bwie.three.view;

import com.bwie.three.bean.CartBean;

import java.util.List;


public interface ICartActivity {
    //展示列表数据方法
    public void show(List<CartBean.DataBean> group, List<List<CartBean.DataBean.ListBean>> child);
}
