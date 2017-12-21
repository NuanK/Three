package com.bwie.three.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.three.R;
import com.bwie.three.bean.CartBean;

import com.bwie.three.bean.ProductBean;
import com.bwie.three.event.CheckEvent;
import com.bwie.three.event.PriceEvent;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.List;



public class MyAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<CartBean.DataBean> group;
    private List<List<CartBean.DataBean.ListBean>> child;
    private LayoutInflater inflater;

    public MyAdapter(Context context, List<CartBean.DataBean> group, List<List<CartBean.DataBean.ListBean>> child) {
        this.context = context;
        this.group = group;
        this.child = child;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder holder;
        if (convertView == null){
            holder = new GroupViewHolder();
            convertView = inflater.inflate(R.layout.cart_group,null);
            holder.group_cb = (CheckBox) convertView.findViewById(R.id.group_cb);
            holder.group_tv = (TextView) convertView.findViewById(R.id.group_tv);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        final CartBean.DataBean dataBean = group.get(groupPosition);
        holder.group_cb.setChecked(dataBean.isChecked());
        holder.group_tv.setText(dataBean.getSellerName());
        //一级列表的CheckBox点击事件
        holder.group_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBean.setChecked(holder.group_cb.isChecked());
                changeChildCbState(groupPosition,holder.group_cb.isChecked());
                EventBus.getDefault().post(price());
                changeAllCbState(isAllGroupCbSelected());
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null){
            holder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.cart_child,null);
            holder.child_cb = (CheckBox) convertView.findViewById(R.id.child_cb);
            holder.child_iv = (ImageView) convertView.findViewById(R.id.child_iv);
            holder.child_tv1 = (TextView) convertView.findViewById(R.id.child_tv1);
            holder.child_tv2 = (TextView) convertView.findViewById(R.id.child_tv2);
            holder.tv_del = (TextView) convertView.findViewById(R.id.tv_del);
            holder.iv_add = (ImageView)convertView.findViewById(R.id.iv_add);
            holder.iv_del = (ImageView)convertView.findViewById(R.id.iv_del);
            holder.tv_num = (TextView)convertView.findViewById(R.id.tv_num);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        final CartBean.DataBean.ListBean listBean = child.get(groupPosition).get(childPosition);
        String[] split = listBean.getImages().split("\\|");
        ImageLoader.getInstance().displayImage(split[0],holder.child_iv);
        holder.child_cb.setChecked(listBean.isChecked());
        holder.child_tv1.setText(listBean.getTitle());
        holder.child_tv2.setText(listBean.getPrice()+"");
        holder.tv_num.setText(listBean.getNum()+"");
        //二级列表的CheckBox点击事件
        holder.child_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean.setChecked(holder.child_cb.isChecked());
                EventBus.getDefault().post(price());
                if (holder.child_cb.isChecked()){
                    //当前CheckBox是选中状态
                    if (isAllChildCbSelected(groupPosition)){
                        changeGroupCbState(groupPosition,true);
                        changeAllCbState(isAllGroupCbSelected());
                    }
                } else {
                    //取消选中
                    changeGroupCbState(groupPosition,false);
                    changeAllCbState(isAllGroupCbSelected());
                }
                notifyDataSetChanged();
            }
        });
        //加号
        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=listBean.getNum();
                holder.tv_num.setText(++num+"");
                listBean.setNum(num);
                if (holder.child_cb.isChecked()){
                    PriceEvent priceEvent=price();
                    EventBus.getDefault().post(priceEvent);
                }
            }
        });
        //减号
        holder.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=listBean.getNum();
                if (num==1){
                    return;
                }
                holder.tv_num.setText(--num+"");
                listBean.setNum(num);
                if (holder.child_cb.isChecked()){
                    PriceEvent priceEvent=price();
                    EventBus.getDefault().post(priceEvent);
                }
            }
        });
        //删除
        holder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CartBean.DataBean.ListBean> datasBeen = child.get(groupPosition);
                CartBean.DataBean.ListBean remove = datasBeen.remove(childPosition);
                if (datasBeen.size()==0){
                    child.remove(groupPosition);
                    group.remove(groupPosition);
                }
                EventBus.getDefault().post(price());
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //优化
    class GroupViewHolder{
        CheckBox group_cb;
        TextView group_tv;
    }
    class ChildViewHolder{
        CheckBox child_cb;
        ImageView child_iv;
        TextView child_tv1;
        TextView child_tv2;
        TextView tv_del;
        ImageView iv_del;
        ImageView iv_add;
        TextView tv_num;
    }

    //改变全选状态
    public void changeAllCbState(boolean flag){
        CheckEvent checkEvent = new CheckEvent();
        checkEvent.setChecked(flag);
        EventBus.getDefault().post(checkEvent);
    }

    //改变一级列表checkBox状态
    private void changeGroupCbState(int groupPosition,boolean flag){
        CartBean.DataBean dataBean = group.get(groupPosition);
        dataBean.setChecked(flag);
    }

    //改变二级列表checkBox状态
    private void changeChildCbState(int groupPosition,boolean flag){
        List<CartBean.DataBean.ListBean> listBeen = child.get(groupPosition);
        for (int i = 0; i < listBeen.size(); i++) {
            CartBean.DataBean.ListBean listBean = listBeen.get(i);
            listBean.setChecked(flag);
        }
    }

    //判断一级列表是否选中
    public boolean isAllGroupCbSelected(){
        for (int i = 0; i < group.size(); i++) {
            CartBean.DataBean dataBean = group.get(i);
            if (!dataBean.isChecked()){
                return false;
            }
        }
        return true;
    }

    //判断二级列表是否选中
    public boolean isAllChildCbSelected(int groupPosition){
        List<CartBean.DataBean.ListBean> listBeen = child.get(groupPosition);
        for (int i = 0; i < listBeen.size(); i++) {
            CartBean.DataBean.ListBean listBean = listBeen.get(i);
            if (!listBean.isChecked()){
                return false;
            }
        }
        return true;
    }

    //计算价格
    private PriceEvent price(){
        int count = 0;
        int price = 0;
        for (int i = 0; i < child.size(); i++) {
            List<CartBean.DataBean.ListBean> listBeen = child.get(i);
            for (int j = 0; j < listBeen.size(); j++) {
                CartBean.DataBean.ListBean listBean = listBeen.get(j);
                if (listBean.isChecked()){
                    price += listBean.getPrice()*listBean.getNum();
                    count +=listBean.getNum();
                }
            }
        }
        PriceEvent priceEvent = new PriceEvent();
        priceEvent.setCount(count);
        priceEvent.setPrice(price);
        return priceEvent;
    }

    //设置全选和反选
    public void changeAllList(boolean flag){
        for (int i = 0; i < group.size(); i++) {
            changeGroupCbState(i,flag);
            changeChildCbState(i,flag);
        }
        EventBus.getDefault().post(price());
        notifyDataSetChanged();
    }
}
