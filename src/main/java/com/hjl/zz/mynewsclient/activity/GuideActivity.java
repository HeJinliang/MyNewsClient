package com.hjl.zz.mynewsclient.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.hjl.zz.mynewsclient.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 版权所有 违法必究
 *
 * @author hjl
 * @project MyNewsClient
 * @file ${FILE}
 * @create_time 2016/8/15
 * @github https://github.com/HeJinPang
 * =====================================
 */
public class GuideActivity extends Activity implements View.OnClickListener {

    private List<Integer> drawableRess = new ArrayList<Integer>();
    private GuideActivity activity = this;
    private Button button;

    /**
     * PagerAdapter适配器
     */
    private class MyPageAdapte extends PagerAdapter {


        @Override
        public int getCount() {
            return drawableRess.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(activity);
            imageView.setBackgroundResource(drawableRess.get(position));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    /**
     * viewpager页面改变的监听
     */
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.i("onPageSelected", "onPageSelected: "+position);
            if (position == 2){
                button.setVisibility(View.VISIBLE);
                Log.i("onPageSelected", "onPageSelected: "+"显示按键");
            }else{
                button.setVisibility(View.GONE);
                Log.i("onPageSelected", "onPageSelected: "+"隐藏按键");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guide);

        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        button = (Button) findViewById(R.id.button);

        drawableRess.add(R.drawable.guide_1);
        drawableRess.add(R.drawable.guide_2);
        drawableRess.add(R.drawable.guide_3);

        MyPageAdapte adapter = new MyPageAdapte();
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(new MyOnPageChangeListener());
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
