package com.hjl.zz.mynewsclient.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.hjl.zz.mynewsclient.R;
import com.hjl.zz.mynewsclient.adapter.MyDefaultPagerAdapter;
import com.hjl.zz.mynewsclient.pager.BasePager;
import com.hjl.zz.mynewsclient.pager.GovAffairsPager;
import com.hjl.zz.mynewsclient.pager.HomePager;
import com.hjl.zz.mynewsclient.pager.NewsCenterPager;
import com.hjl.zz.mynewsclient.pager.SettingPager;
import com.hjl.zz.mynewsclient.pager.SmartServicePager;
import com.hjl.zz.mynewsclient.view.LazyViewPager;
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
 * @create_time 2016/8/14
 * @github https://github.com/HeJinliang
 * =====================================
 */
public class ContentFragment extends BaseFragment {


    private MyViewPager vp_home;
    private RadioGroup rg_home;
    private RadioButton rb_home;
    private RadioButton rb_news_center;
    private RadioButton rb_smart_service;
    private RadioButton rb_gov_affairs;
    private RadioButton rb_setting;
    public List<BasePager> pagers;

    /**
     * ViewPager的PagerAdapter
     */
    private class MyPagerAdapter extends MyDefaultPagerAdapter{

        public MyPagerAdapter(List pagers) {
            super(pagers);
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = pagers.get(position);
            View view = pager.initView();
            pager.initData();
            container.addView(view);
            return view;
        }
    }




    /**
     * ViewPager改变的监听
     */
    private class MyOnPageChangeListener implements LazyViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    rb_home.setChecked(true);
                    break;
                case 1:
                    rb_news_center.setChecked(true);
                    break;
                case 2:
                    rb_smart_service.setChecked(true);
                    break;
                case 3:
                    rb_gov_affairs.setChecked(true);
                    break;
                case 4:
                    rb_setting.setChecked(true);
                    break;
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    /**
     * VieoGroup改变的监听
     */
    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {


        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            int index = 0;

            switch (i){
                case R.id.rb_home:
                    index = 0;
                    break;
                case R.id.rb_news_center:
                    index = 1;
                    break;
                case R.id.rb_smart_service:
                    index = 2;
                    break;
                case R.id.rb_gov_affairs:
                    index = 3;
                    break;
                case R.id.rb_setting:
                    index = 4;
                    break;

            }
            vp_home.setCurrentItem(index);
        }
    }

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.fragment_content,null);

        vp_home = (MyViewPager) view.findViewById(R.id.vp_home);
        rg_home = (RadioGroup) view.findViewById(R.id.rg_home);
        rb_home = (RadioButton) view.findViewById(R.id.rb_home);
        rb_news_center = (RadioButton) view.findViewById(R.id.rb_news_center);
        rb_smart_service = (RadioButton) view.findViewById(R.id.rb_smart_service);
        rb_gov_affairs = (RadioButton) view.findViewById(R.id.rb_gov_affairs);
        rb_setting = (RadioButton) view.findViewById(R.id.rb_setting);

        return view;
    }

    @Override
    public void initData() {

        pagers = new ArrayList<>();
        pagers.add(new HomePager(context));
        pagers.add(new NewsCenterPager(context));
        pagers.add(new SmartServicePager(context));
        pagers.add(new GovAffairsPager(context));
        pagers.add(new SettingPager(context));

        vp_home.setAdapter(new MyPagerAdapter(pagers));

        rb_news_center.setChecked(true);
        vp_home.setCurrentItem(1);

        vp_home.setOnPageChangeListener(new MyOnPageChangeListener());
        rg_home.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

    }
}
