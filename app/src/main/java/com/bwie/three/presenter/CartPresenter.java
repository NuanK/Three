package com.bwie.three.presenter;

import com.bwie.three.bean.CartBean;
import com.bwie.three.model.CartModel;
import com.bwie.three.model.ICartModel;
import com.bwie.three.net.OnNetListener;
import com.bwie.three.view.ICartActivity;

import java.util.ArrayList;
import java.util.List;



public class CartPresenter {
    private ICartActivity iCartActivity;
    private ICartModel iCartModel;

    public CartPresenter(ICartActivity iCartActivity){
        this.iCartActivity=iCartActivity;
        iCartModel=new CartModel();
    }

    public void getCart(String uid){
        iCartModel.getCart(uid, new OnNetListener<CartBean>() {
            @Override
            public void OnSuccess(CartBean cartBean) {
                List<CartBean.DataBean> data = cartBean.getData();
                List<List<CartBean.DataBean.ListBean>> list = new ArrayList<List<CartBean.DataBean.ListBean>>();
                for (int i = 0; i < data.size(); i++) {
                    List<CartBean.DataBean.ListBean> list1 = data.get(i).getList();
                    list.add(list1);
                }
                iCartActivity.show(data,list);
            }

            @Override
            public void OnFailed(Exception e) {

            }
        });
    }
}
