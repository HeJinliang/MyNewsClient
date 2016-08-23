package com.hjl.zz.mynewsclient.secPager;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hjl.zz.mynewsclient.R;
import com.hjl.zz.mynewsclient.adapter.MyDefaultBaseAdapter;
import com.hjl.zz.mynewsclient.bean.NewsBean;
import com.hjl.zz.mynewsclient.bean.NewsCenterBean;
import com.hjl.zz.mynewsclient.bean.NewsCenterBean.NewsDataBean.ChildrenBean;
import com.hjl.zz.mynewsclient.pager.BasePager;
import com.hjl.zz.mynewsclient.utils.LogUtils;
import com.hjl.zz.mynewsclient.utils.NetUrl;
import com.hjl.zz.mynewsclient.view.DownRefreshListView;
import com.hjl.zz.mynewsclient.view.RollViewPager;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 版权所有 违法必究
 *
 * @author hjl
 * @project MyNewsClient
 * @file ${FILE}
 * @create_time 2016/8/18
 * @github https://github.com/HeJinliang
 * =====================================
 */
public class NewsItemPager extends BasePager {

    private List<ChildrenBean> childrens;
    private ChildrenBean childrenBean;
    private DownRefreshListView listView;
    private LinearLayout ll_top_news_viewpager;
    private TextView tv_top_news_title;
    private LinearLayout ll_dots;

    private NewsBean newsBean;
    private RollViewPager rollViewPager;

    private List<String> titles = new ArrayList<>();
    private List<String> topimages = new ArrayList<>();
    private List<ImageView> dots= new ArrayList<>();

    /**
     * 头条新闻的个数
     */
    private int itemCount;

    /**
     * 请求网络数据的回掉
     */
    private class MyRequestCallBack extends RequestCallBack<String> {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            LogUtils.logInfo("联网成功");
            //解析数据
            parseJson(responseInfo.result);
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            error.printStackTrace();
            LogUtils.logInfo("联网失败");
        }
    }

    /**
     * RollViewPager界面改变的监听
     */
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            //获取到数据的情况下
            if (itemCount>0){
                int index = position%itemCount;
                //设置选中的指示点
                for (int i = 0; i < itemCount; i++) {
                    if (i == index){
                        dots.get(i).setImageResource(R.drawable.dot_focus);

                    }else {
                        dots.get(i).setImageResource(R.drawable.dot_normal);
                    }
                }
                //随着RollViewPager的改变设置头条标题
                tv_top_news_title.setText(newsBean.data.topnews.get(index).title);
            }
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageScrollStateChanged(int state) {}
    }

    private class MyBaseAdapter extends MyDefaultBaseAdapter{

        public MyBaseAdapter(List data) {
            super(data);
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            View view = null;
            ViewHolder vh = null;
            if (convertView == null){
                view = android.view.View.inflate(context,R.layout.listview_item_news,null);
                vh = new ViewHolder();
                vh.iv_img = (ImageView) view.findViewById(R.id.iv_img);
                vh.tv_title = (TextView) view.findViewById(R.id.tv_title);
                vh.tv_pub_date = (TextView) view.findViewById(R.id.tv_pub_date);
                vh.tv_comment_count = (TextView) view.findViewById(R.id.tv_comment_count);
                view.setTag(vh);
            }else {
                view = convertView;
                vh = (ViewHolder) view.getTag();
            }

            NewsBean.DataBean.NewsItemBean newsItemBean = newsBean.data.news.get(i);

            //展示图片
            BitmapUtils bitmapUtils = new BitmapUtils(context);
            bitmapUtils.display(vh.iv_img,newsItemBean.listimage);
            //设置标题
            vh.tv_title.setText(newsItemBean.title);
            //设置刊登时间
            vh.tv_pub_date.setText(newsItemBean.pubdate);

            return view;
        }
    }

    private class ViewHolder{
        ImageView iv_img;
        TextView tv_title;
        TextView tv_pub_date;
        TextView tv_comment_count;

    }



    /**
     * 构造
     * @param context
     * @param childrens
     * @param index
     */
    public NewsItemPager(Context context,List<ChildrenBean> childrens, int index) {
        super(context);
        this.childrens = childrens;
        this.childrenBean = childrens.get(index);
    }

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.news_item_pager,null);

        //新闻子条目
        listView = (DownRefreshListView) view.findViewById(R.id.lv_news_item_pager);
        //自定义view，用来显示新闻头条
        View rollView = View.inflate(context,R.layout.layout_roll_view,null);
        //将自定义View添加到listView的头布局中
        listView.addCustomView(rollView);

        //头条新闻图片
        ll_top_news_viewpager = (LinearLayout) rollView.findViewById(R.id.ll_top_news_viewpager);
        //头条新闻标题
        tv_top_news_title = (TextView) rollView.findViewById(R.id.tv_top_news_title);
        //头条，轮训图的指示点
        ll_dots = (LinearLayout) rollView.findViewById(R.id.ll_dots);

        //自定义ViewPager，能轮播图片
        rollViewPager = new RollViewPager(context);
        ll_top_news_viewpager.addView(rollViewPager);

        //设置界面改变的监听
        rollViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        //RollViewPager的点击监听
        rollViewPager.setOnItemClickListener(new RollViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LogUtils.logInfo("监听到ViewPager的条目点击监听：下标为："+position);
            }
        });
        return view;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void initData() {

        String url = NetUrl.BASE_URL+childrenBean.url;
        //获取缓存
        //String cacheJson = SPUtils.getCacheJson(context,url);

        //解析缓存
        //if (!TextUtils.isEmpty(cacheJson)){
            //解析数据
         //   parseJson(cacheJson);
       // }
        //联网获取数据
        requestInterData(url, new MyRequestCallBack());

    }

    /**
     * 解析数据
     * @param json
     */
    private void parseJson(String json){
        Gson gson = new Gson();
        newsBean = gson.fromJson(json, NewsBean.class);

        itemCount = newsBean.data.topnews.size();

        //获取数据后，填充标题集合，头条图片集合
        //填充标题集合前先清空集合
        titles.clear();
        topimages.clear();
        for (int i = 0; i < itemCount; i++) {
            titles.add(newsBean.data.topnews.get(i).title);
            topimages.add(newsBean.data.topnews.get(i).topimage);
        }

        //处理轮播图的逻辑
        dealRollViewPager();

        //处理新闻内容列表的逻辑
        dealNewsContentListView();
    }

    /**
     * 处理新闻内容列表的逻辑
     */
    private void dealNewsContentListView() {
        listView.setAdapter(new MyBaseAdapter(newsBean.data.news));
    }

    /**
     * 处理轮播图的逻辑
     */
    private void dealRollViewPager() {
        //头条新闻标题默认显示第一个标题
        tv_top_news_title.setText(titles.get(0));

        //填充指示点，先清空
        ll_dots.removeAllViews();
        addPointDot(itemCount);

        //给RollViewPager传递数据
        rollViewPager.setTopImageUrls(topimages);

        //开始轮播图片
        rollViewPager.startRoll();

    }


    /**
     * 添加轮训器指示点
     * @param count
     */
    private void addPointDot(int count){
        //清空指示点集合前，先清空
        dots.clear();
        for (int i = 0; i < count; i++) {
                ImageView dotImg = new ImageView(context);
            if (i == 0){
                dotImg.setImageResource(R.drawable.dot_focus);
            }else {
                dotImg.setImageResource(R.drawable.dot_normal);
            }
            //将指示点添加到ll_dots布局当中
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            //将指示点加到集合中
            dots.add(dotImg);

            ll_dots.addView(dotImg,params);
        }
    }
}
