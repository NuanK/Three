package com.bwie.three.presenter;

import com.bwie.three.model.ConfirmModel;
import com.bwie.three.model.IConfirmModel;


public class ConfirmPresenter implements IConfirmModel {
    private ConfirmModel confirmModel;
    private IConfirmPresenter iConfirmPresenter;

    public ConfirmPresenter(IConfirmPresenter iConfirmPresenter) {
        this.iConfirmPresenter = iConfirmPresenter;
        confirmModel = new ConfirmModel();
    }

    public void receive(String uid, String price) {
        confirmModel.receive(uid, price, this);
    }

    @Override
    public void onSuccess(String msg) {
        iConfirmPresenter.onSuccess(msg);
    }

    @Override
    public void onFailed() {
        iConfirmPresenter.onFailed();
    }
}
