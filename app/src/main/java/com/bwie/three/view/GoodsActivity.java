package com.bwie.three.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.three.R;
import com.bwie.three.presenter.ProductPresenter;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GoodsActivity extends AppCompatActivity implements IProductActivity,View.OnClickListener{

    private ImageView mBack;
    private ImageView mImg;
    /**
     * 锤子
     */
    private TextView mTvName;
    /**
     * 原价：
     */
    private TextView mTvBranchprice;
    /**
     * 优惠价：
     */
    private TextView mTvPrice;
    /**
     * 加入购物车
     */
    private Button mAdd;
    private LinearLayout mActivityMain;
    private ProductPresenter productPresenter;
    private String str="加入购物车成功";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        initView();
        productPresenter=new ProductPresenter(this);
        productPresenter.getProduct("1");
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mImg = (ImageView) findViewById(R.id.img);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvBranchprice = (TextView) findViewById(R.id.tv_branchprice);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mAdd = (Button) findViewById(R.id.add);
        mAdd.setOnClickListener(this);
        mActivityMain = (LinearLayout) findViewById(R.id.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                this.finish();
                break;
            case R.id.add:
                toadd(str);
                break;
        }
    }


    @Override
    public void setiv(String url) {
        ImageLoader.getInstance().displayImage(url,mImg);
    }

    @Override
    public void settv1(String tv1) {
        mTvName.setText(tv1);
    }

    @Override
    public void settv2(String tv2) {
        mTvBranchprice.setText(tv2);
    }

    @Override
    public void settv3(String tv3) {
        mTvPrice.setText(tv3);
    }

    @Override
    public void toadd(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
