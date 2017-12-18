package com.bwie.three.presenter;

import android.util.Log;
import android.widget.Toast;

import com.bwie.three.bean.ProductBean;
import com.bwie.three.model.IProductModel;
import com.bwie.three.model.ProductModel;
import com.bwie.three.net.OnNetListener;
import com.bwie.three.view.IProductActivity;

public class ProductPresenter {
    private IProductModel iProductModel;
    private IProductActivity iProductActivity;

    public ProductPresenter(IProductActivity iProductActivity) {
        this.iProductActivity = iProductActivity;
        iProductModel = new ProductModel();
    }

    public void getGood(String pid){
        iProductModel.getGoods(pid, new OnNetListener<ProductBean>() {
            @Override
            public void OnSuccess(ProductBean productBean) {

                ProductBean.DataBean data = productBean.getData();
                String[] split = data.getImages().split("\\|");
                iProductActivity.setiv1(split[0]);
                iProductActivity.settv1(data.getTitle());
                iProductActivity.settv2(data.getPrice()+"");
                iProductActivity.settv3(data.getBargainPrice()+"");
            }

            @Override
            public void OnFailed(Exception e) {

            }
        });
    }

}
