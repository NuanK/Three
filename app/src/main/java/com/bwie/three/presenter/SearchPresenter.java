package com.bwie.three.presenter;

import android.os.Handler;
import android.os.Looper;

import com.bwie.three.bean.SearchBean;
import com.bwie.three.model.SearchModel;
import com.google.gson.Gson;

import java.util.List;


public class SearchPresenter {

    PresenterListener presenterListener;
    private SearchModel searchModel;
    Handler handler=new Handler(Looper.getMainLooper());
    public SearchPresenter(PresenterListener presenterListener){
        this.presenterListener=presenterListener;
        searchModel=new SearchModel();
    }

    public void getDat(String name, final String page){
        searchModel.getData(name,page,new SearchModel.ModuleListeren(){

            @Override
            public void onSuccess(String response) {
                SearchBean searchBean = new Gson().fromJson(response, SearchBean.class);
                final List<SearchBean.DataBean> data = searchBean.getData();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (presenterListener!=null){
                            presenterListener.onSuccess(data);
                        }
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {
                presenterListener.onFailed(e);
            }
        });
    }

    //内存泄漏
    public void detach(){

        presenterListener = null;
    }


    public interface PresenterListener{
        void onSuccess(List<SearchBean.DataBean>list);
        void onFailed(Exception e);
    }
}
