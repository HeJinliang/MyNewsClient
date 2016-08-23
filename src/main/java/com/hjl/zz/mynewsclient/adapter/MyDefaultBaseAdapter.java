package com.hjl.zz.mynewsclient.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 *
 * 版权所有 违法必究
 *
 * @author hjl
 * @project MyNewsClient
 * @file ${FILE}
 * @create_time 2016/8/22
 * @github https://github.com/HeJinliang
 * =====================================
 */
public abstract class MyDefaultBaseAdapter<T> extends BaseAdapter {

    private final List<T> data;

    public MyDefaultBaseAdapter(List<T> data){
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * 此方法 listview.getItemAtPosition -- getItem
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    /**
     * 此方法 listview.getItemIdAtPosition -- getItemId
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public abstract View getView(int i, View convertView, ViewGroup viewGroup);
}
