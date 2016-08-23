package com.hjl.zz.mynewsclient.secPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.hjl.zz.mynewsclient.R;
import com.hjl.zz.mynewsclient.adapter.MyDefaultPagerAdapter;
import com.hjl.zz.mynewsclient.bean.NewsCenterBean;
import com.hjl.zz.mynewsclient.indicator.TabPageIndicator;
import com.hjl.zz.mynewsclient.pager.BasePager;
import com.hjl.zz.mynewsclient.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 版权所有 违法必究
 *
 * @author hjl
 * @project MyNewsClient
 * @file ${FILE}
 * @create_time 2016/8/17
 * @github https://github.com/HeJinliang
 * =====================================
 */
public class NewsPager extends BasePager {

    private ViewPager viewPager;
    private TabPageIndicator indicator;
    private List<NewsCenterBean.NewsDataBean.ChildrenBean> children;
    private List<NewsItemPager> pagers = new ArrayList<>();

    /**
     * viewPager的适配器
     */
    class MyPagerAdapter extends MyDefaultPagerAdapter {

        public MyPagerAdapter(List pagers) {
            super(pagers);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            NewsItemPager pager = pagers.get(position);
            View view = pager.initView();
            pager.initData();
            container.addView(view);
            return view;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).title;
        }
    }

    /**
     * viewPager界面改变的监听
     */
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            NewsItemPager pager = pagers.get(position);
            //pager.initView();
            pager.initData();
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    /**
     * 构造器
     * @param context
     * @param children
     */
    public NewsPager(Context context, List<NewsCenterBean.NewsDataBean.ChildrenBean> children) {
        super(context);
        this.children = children;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_news_center_news,null);
        viewPager = (ViewPager) view.findViewById(R.id.vp_sub_pager_news);
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        return view;
    }

    @Override
    public void initData() {

        pagers.clear();
        for (int i = 0; i < children.size(); i++) {
            pagers.add(new NewsItemPager(context, children, i));
        }

        //添加适配器
        viewPager.setAdapter(new MyPagerAdapter(pagers));
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        indicator.setViewPager(viewPager);

    }

}
