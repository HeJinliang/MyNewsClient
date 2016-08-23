package com.hjl.zz.mynewsclient.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

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
public abstract class MyDefaultPagerAdapter<T> extends PagerAdapter {

    private final List<T> pagers;

    public MyDefaultPagerAdapter(List<T> pagers){
        this.pagers = pagers;
    }

    @Override
    public int getCount() {
        return pagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public abstract Object instantiateItem(ViewGroup container, int position);
}
