package com.bwie.three.model;

import com.bwie.three.bean.PayBean;

import java.util.List;

public interface IPayModel {

    void onSuccess(List<PayBean.DataBean> data);

    void onFailed();

}