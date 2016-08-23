package com.hjl.zz.mynewsclient.pager;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hjl.zz.mynewsclient.R;
import com.hjl.zz.mynewsclient.activity.MainActivity;
import com.hjl.zz.mynewsclient.bean.NewsCenterBean;
import com.hjl.zz.mynewsclient.fragment.MenuFragment;
import com.hjl.zz.mynewsclient.secPager.InteractionPager;
import com.hjl.zz.mynewsclient.secPager.NewsPager;
import com.hjl.zz.mynewsclient.secPager.PicturesPager;
import com.hjl.zz.mynewsclient.secPager.SubjectPager;
import com.hjl.zz.mynewsclient.utils.LogUtils;
import com.hjl.zz.mynewsclient.utils.NetUrl;
import com.hjl.zz.mynewsclient.utils.SPUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 版权所有 违法必究
 *
 * @author hjl
 * @project MyNewsClient
 * @file ${FILE}
 * @create_time 2016/8/16
 * @github https://github.com/HeJinliang
 * =====================================
 */
public class NewsCenterPager extends BasePager {

    private NewsCenterBean newsCenterBean;
    private ImageButton imgbtn_left;
    private TextView txt_title;
    private ImageButton imgbtn_right;
    private ImageButton imgbtn_right2;
    private FrameLayout fl_news_center;
    private List<BasePager> secNewsPagers;


    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_news_center,null);

        LinearLayout ll_title_bar = (LinearLayout) view.findViewById(R.id.ll_title_bar);
        Button btn_left = (Button) view.findViewById(R.id.btn_left);
        imgbtn_left = (ImageButton) view.findViewById(R.id.imgbtn_left);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        ImageButton imgbtn_title = (ImageButton) view.findViewById(R.id.imgbtn_title);
        imgbtn_right = (ImageButton) view.findViewById(R.id.imgbtn_right);
        imgbtn_right2 = (ImageButton) view.findViewById(R.id.imgbtn_right2);
        fl_news_center = (FrameLayout) view.findViewById(R.id.fl_news_center);

        return view;
    }

    @Override
    public void initData() {
//        //获取缓存
//        String cacheJson = SPUtils.getCacheJson(context,NetUrl.NEWS_CENTER_CATEGORIES);
//        //解析缓存
//        if (!TextUtils.isEmpty(cacheJson)){
//            //解析数据
//            parseJson(cacheJson);
//
//        }else {
//        }
        //请求数据
        requestInterData(NetUrl.NEWS_CENTER_CATEGORIES, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                //缓存数据
                SPUtils.setCacheJson(context,NetUrl.NEWS_CENTER_CATEGORIES,json);
                //解析数据
                parseJson(json);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                LogUtils.logInfo("请求失败");
            }
        });

        //二级新闻pager
        secNewsPagers = new ArrayList<>();

    }


    /**
     * 解析数据
     * @param json
     */

    private void parseJson(String json){
        LogUtils.logInfo("开始解析数据  "+json);
        Gson gson = new Gson();
        newsCenterBean = gson.fromJson(json, NewsCenterBean.class);

        LogUtils.logInfo(newsCenterBean.toString());

        //NewsCenterPager————MenuFragment传递数据  初始化二级菜单目录
        MainActivity mainActivity = (MainActivity) context;
        MenuFragment menuFragment = mainActivity.getMenuFragment();
        menuFragment.initSecMenu(newsCenterBean.data);

        //新闻中心默认选中的是新闻界面
        switchSecMenu(0);

    }

    /**
     * 处理外部传递过来的数据
     * 切换二级菜单
     */
    public void switchSecMenu(int position){
        secNewsPagers.clear();
        secNewsPagers.add(new NewsPager(context,newsCenterBean.data.get(0).children));
        secNewsPagers.add(new SubjectPager(context));
        secNewsPagers.add(new PicturesPager(context));
        secNewsPagers.add(new InteractionPager(context));

        fl_news_center.removeAllViews();
        BasePager pager = secNewsPagers.get(position);
        fl_news_center.addView(pager.initView());
        pager.initData();

        imgbtn_left.setVisibility(View.VISIBLE);
        imgbtn_left.setImageResource(R.drawable.img_menu);
        imgbtn_right2.setVisibility(View.GONE);
        switch (position){
            case 0:
                txt_title.setText(newsCenterBean.data.get(0).title);
                break;
            case 1:
                txt_title.setText(newsCenterBean.data.get(1).title);
                break;
            case 2:
                txt_title.setText(newsCenterBean.data.get(2).title);
                imgbtn_right2.setVisibility(View.VISIBLE);
                imgbtn_right2.setImageResource(R.drawable.icon_pic_list_type);
                break;
            case 3:
                txt_title.setText(newsCenterBean.data.get(3).title);
                break;

        }
    }
}
