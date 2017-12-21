package com.bwie.three.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.three.R;
import com.bwie.three.adapter.MyAdapter;
import com.bwie.three.bean.CartBean;
import com.bwie.three.event.CheckEvent;
import com.bwie.three.event.PriceEvent;
import com.bwie.three.presenter.CartPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class CartActivity extends AppCompatActivity implements ICartActivity, View.OnClickListener {

    private ImageView mBack;
    private ExpandableListView mElv;
    private CheckBox mCb;


    private MyAdapter myAdapter;
    private CartPresenter cartPresenter = new CartPresenter(this);
    /**
     * 0
     */
    private TextView mTvPrice;
    /**
     * 结算(0)
     */
    private TextView mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();

        cartPresenter.getCart("72");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mElv = (ExpandableListView) findViewById(R.id.elv);
        mCb = (CheckBox) findViewById(R.id.cb);
        mCb.setOnClickListener(this);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mCount = (TextView) findViewById(R.id.count);
        mCount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                this.finish();
                break;
            case R.id.cb:
                myAdapter.changeAllList(mCb.isChecked());
                break;
            case R.id.count:
                Toast.makeText(CartActivity.this,mCount.getText().toString(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ConfirmActivity.class);
                intent.putExtra("price",mTvPrice.getText()+"");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void show(List<CartBean.DataBean> group, List<List<CartBean.DataBean.ListBean>> child) {
        myAdapter = new MyAdapter(this, group, child);
        mElv.setAdapter(myAdapter);
        mElv.setGroupIndicator(null);
        //默认二级列表展开
        for (int i = 0; i < group.size(); i++) {
            mElv.expandGroup(i);
        }
    }

    @Subscribe
    public void onMessageEvent(CheckEvent event) {
        mCb.setChecked(event.isChecked());
    }

    @Subscribe
    public void onMessageEvent(PriceEvent event) {
        mTvPrice.setText(event.getPrice() + "");
        mCount.setText("结算(" + event.getCount() + ")");
    }
}
