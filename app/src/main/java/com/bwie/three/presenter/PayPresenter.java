package com.bwie.three.presenter;


import com.bwie.three.bean.PayBean;
import com.bwie.three.model.IPayModel;
import com.bwie.three.model.PayModel;

import java.util.List;

public class PayPresenter implements IPayModel {
    private PayModel payModel;
    private IPayPresenter iPayPresenter;

    public PayPresenter(IPayPresenter iPayPresenter) {
        this.iPayPresenter = iPayPresenter;
        payModel = new PayModel();
    }

    public void receive(String uid,String urlTitle) {
        payModel.receive(uid,urlTitle, this);
    }

    @Override
    public void onSuccess(List<PayBean.DataBean> data) {
        iPayPresenter.onSuccess(data);
    }

    @Override
    public void onFailed() {
        iPayPresenter.onFailed();
    }
}
