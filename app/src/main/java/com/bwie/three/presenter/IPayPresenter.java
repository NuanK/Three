package com.bwie.three.presenter;

import com.bwie.three.bean.PayBean;

import java.util.List;

public interface IPayPresenter {

    void onSuccess(List<PayBean.DataBean> data);

    void onFailed();

}
