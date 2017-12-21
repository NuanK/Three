package com.bwie.three.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.three.R;
import com.bwie.three.adapter.MyRecyclerAdapter;
import com.bwie.three.bean.SearchBean;
import com.bwie.three.presenter.SearchPresenter;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchPresenter.PresenterListener {

    private SearchPresenter searchPresenter;
    List<SearchBean.DataBean> list = new ArrayList<>();
    private Button btn_search;
    private ImageView iv;
    private ImageView iv_back;
    private EditText et_search;
    private RecyclerView recyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;
    boolean flag = true;
    private SpringView springView;
    String name = "笔记本";
    int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btn_search = (Button) findViewById(R.id.btn_search);
        iv = (ImageView) findViewById(R.id.iv);

        iv_back = (ImageView) findViewById(R.id.back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        et_search = (EditText) findViewById(R.id.et_search);
        recyclerView = (RecyclerView) findViewById(R.id.recy);
//        springView=(SpringView)findViewById(R.id.spring);

        String s = String.valueOf(num);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerAdapter = new MyRecyclerAdapter(list, SearchActivity.this, flag);
        recyclerView.setAdapter(myRecyclerAdapter);
        myRecyclerAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(SearchActivity.this,GoodsActivity.class);
                startActivity(intent);
            }
        });

        searchPresenter = new SearchPresenter(this);
        searchPresenter.getDat(name, s);
        //设置适配器
        setAdapter();
        //点击搜索事件
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(et_search.getText().toString())) {
                    list.clear();
                    Toast.makeText(SearchActivity.this, et_search.getText().toString(), Toast.LENGTH_SHORT).show();
                    searchPresenter.getDat(et_search.getText().toString(), "1");
                    setAdapter();
                }
            }
        });

        //点击切换事件
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == false) {
                    flag = true;
                    iv.setBackgroundResource(R.mipmap.grid_icon);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
                    SearchActivity.this.myRecyclerAdapter = new MyRecyclerAdapter(list, SearchActivity.this, flag);
                    recyclerView.setAdapter(SearchActivity.this.myRecyclerAdapter);
                    SearchActivity.this.myRecyclerAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(myRecyclerAdapter);
                    myRecyclerAdapter = new MyRecyclerAdapter(list, SearchActivity.this, flag);
                    recyclerView.setAdapter(myRecyclerAdapter);
                    myRecyclerAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(SearchActivity.this, GoodsActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    flag = false;
                    iv.setBackgroundResource(R.mipmap.lv_icon);
                    recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
                    SearchActivity.this.myRecyclerAdapter = new MyRecyclerAdapter(list, SearchActivity.this, flag);
                    recyclerView.setAdapter(SearchActivity.this.myRecyclerAdapter);
                    SearchActivity.this.myRecyclerAdapter.notifyDataSetChanged();
                    myRecyclerAdapter = new MyRecyclerAdapter(list, SearchActivity.this, flag);
                    recyclerView.setAdapter(myRecyclerAdapter);
                    myRecyclerAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(SearchActivity.this, GoodsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

//        springView.setHeader(new DefaultHeader(this));
//        springView.setFooter(new DefaultFooter(this));
//        springView.setListener(new SpringView.OnFreshListener() {
//            @Override
//            public void onRefresh() {
//                num=1;
//                String s1 = String.valueOf(num);
//                searchPresenter.getDat(name,s1);
//                setAdapter();
//                springView.onFinishFreshAndLoad();
//            }
//
//            @Override
//            public void onLoadmore() {
//                num++;
//                String s1 = String.valueOf(num);
//                searchPresenter.getDat(name,s1);
//                Toast.makeText(MainActivity.this,s1,Toast.LENGTH_SHORT).show();
//                setAdapter();
//                springView.onFinishFreshAndLoad();
//            }
//        });

    }

    public void setAdapter() {
        if (myRecyclerAdapter == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
            myRecyclerAdapter = new MyRecyclerAdapter(list, SearchActivity.this, flag);
            recyclerView.setAdapter(myRecyclerAdapter);
        } else {
            myRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess(List<SearchBean.DataBean> lists) {
        //Log.e("QQQQ",lists.size()+"");
        list.addAll(lists);
        myRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.detach();
    }
}
