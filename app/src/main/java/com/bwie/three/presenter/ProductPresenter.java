package com.bwie.three.presenter;

import android.util.Log;

import com.bwie.three.bean.ProductBean;
import com.bwie.three.model.IProductModel;
import com.bwie.three.model.ProductModel;
import com.bwie.three.net.OnNetListener;
import com.bwie.three.view.IProductActivity;



public class ProductPresenter {
    private IProductModel iProductModel;
    private IProductActivity iProductActivity;
    public ProductPresenter(IProductActivity iProductActivity){
        this.iProductActivity=iProductActivity;
        iProductModel=new ProductModel();
    }

    public void getProduct(String pid){
        iProductModel.getProduct(pid, new OnNetListener<ProductBean>() {
            @Override
            public void OnSuccess(ProductBean productBean) {
                Log.e("ssss", "======"+productBean.getMsg() );
                ProductBean.DataBean data = productBean.getData();
                String[] split=data.getImages().split("\\|");
                iProductActivity.setiv(split[0]);
                iProductActivity.settv1(data.getTitle());
                iProductActivity.settv2("原价："+data.getBargainPrice());
                iProductActivity.settv3("优惠价："+data.getPrice());
            }

            @Override
            public void OnFailed(Exception e) {

            }
        });
    }

}
