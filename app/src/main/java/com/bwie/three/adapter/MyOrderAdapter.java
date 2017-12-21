package com.bwie.three.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bwie.three.R;
import com.bwie.three.bean.PayBean;

import java.util.List;




public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder>{

    private Context context;
    private List<PayBean.DataBean> data;

    public MyOrderAdapter(Context context, List<PayBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_pay, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.price.setText(data.get(position).getPrice() + "");
        if (data.get(position).getStatus() == 0) {
            holder.state.setTextColor(Color.RED);
            holder.state.setText("待支付");
        } else if (data.get(position).getStatus() == 1) {
            holder.state.setTextColor(Color.BLACK);
            holder.state.setText("已支付");
        } else if (data.get(position).getStatus() == 2) {
            holder.state.setTextColor(Color.BLACK);
            holder.state.setText("已取消");
        } else {

        }
        holder.listName.setText(data.get(position).getTitle());
        holder.editTime.setText("创建时间:" + data.get(position).getCreatetime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView listName;
        TextView state;
        TextView price;
        TextView editTime;
        Button btnCancle;

        public ViewHolder(View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.listName);
            state = itemView.findViewById(R.id.state);
            price = itemView.findViewById(R.id.price);
            editTime = itemView.findViewById(R.id.editTime);
            btnCancle = itemView.findViewById(R.id.btnCancle);
        }
    }
}
