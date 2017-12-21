package com.bwie.three.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bwie.three.R;

public class MyOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout myTab;
    private ViewPager viewPage;
    private String[] title = {"全部", "待支付", "已支付", "已取消"};
    private String[] urlTitle = {"9", "0", "1", "2"};
    private MyAdapter myAdapter;
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        initView();//初始化视图
        myAdapter = new MyAdapter(getSupportFragmentManager());
        viewPage.setAdapter(myAdapter);
        myTab.setupWithViewPager(viewPage);
    }

    private void initView() {
        myTab = (TabLayout) findViewById(R.id.myTab);
        viewPage = (ViewPager) findViewById(R.id.viewPage);
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                this.finish();
                break;
        }
    }

    //内部适配器
    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {

            //创建fragment对象并返回
            Bundle bundle = new Bundle();
            bundle.putString("url", urlTitle[position]);

            PayFragment payFragment = new PayFragment();
            payFragment.setArguments(bundle);
            return payFragment;
        }

        @Override
        public int getCount() {
            return title.length;
        }
    }
}
