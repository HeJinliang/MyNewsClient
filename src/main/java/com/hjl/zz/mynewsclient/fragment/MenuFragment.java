package com.hjl.zz.mynewsclient.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hjl.zz.mynewsclient.R;
import com.hjl.zz.mynewsclient.activity.MainActivity;
import com.hjl.zz.mynewsclient.adapter.MyDefaultBaseAdapter;
import com.hjl.zz.mynewsclient.bean.NewsCenterBean;
import com.hjl.zz.mynewsclient.pager.BasePager;
import com.hjl.zz.mynewsclient.pager.NewsCenterPager;

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
public class MenuFragment extends BaseFragment {

    private ListView lv_menu_news_center;
    private List<NewsCenterBean.NewsDataBean> data;
    private int currentPosition = 0;
    private MyBaseAdapter adapter;
    private NewsCenterPager pager;
    private MainActivity activity;


    /**
     * listview条目单机事件监听
     */
    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            currentPosition = i;
            adapter.notifyDataSetChanged();

            //MenuFragment————NewsCenterPager传递数据
            pager.switchSecMenu(i);

            //关闭菜单
            activity.slidingMenu.toggle();
        }
    }

    /**
     * ListView适配器
     */
    private class MyBaseAdapter extends MyDefaultBaseAdapter{

        public MyBaseAdapter(List data) {
            super(data);
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View view = null;
            ViewHolder vh = null;
            if (convertView == null){
                view = android.view.View.inflate(context,R.layout.listview_item_menu,null);
                vh = new ViewHolder();
                vh.iv_menu_item = (ImageView) view.findViewById(R.id.iv_menu_item);
                vh.tv_menu_item = (TextView) view.findViewById(R.id.tv_menu_item);
                view.setTag(vh);
            }else {
                view = convertView;
                vh = (ViewHolder) view.getTag();
            }

            NewsCenterBean.NewsDataBean newsDataBean = data.get(i);
            vh.tv_menu_item.setText(newsDataBean.title);

            if (i == currentPosition){
                vh.iv_menu_item.setBackgroundResource(R.drawable.menu_arr_select);
                vh.tv_menu_item.setTextColor(Color.RED);
                view.setBackgroundResource(R.drawable.menu_item_bg_select);
            }else {
                vh.iv_menu_item.setBackgroundResource(R.drawable.menu_arr_normal);
                vh.tv_menu_item.setTextColor(Color.WHITE);
                view.setBackgroundDrawable(null);
            }
            return view;
        }
    }

    private class ViewHolder{
        ImageView iv_menu_item;
        TextView tv_menu_item;
    }

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.fragment_menu,null);

        lv_menu_news_center = (ListView) view.findViewById(R.id.lv_menu_news_center);
        return view;
    }

    @Override
    public void initData() {
    }


    /**
     * 初始化NewsCenterPager传递过来的数据
     * 初始化二级菜单的数据
     * @param data
     */
    public void initSecMenu(List<NewsCenterBean.NewsDataBean> data){
        this.data = data;

        adapter = new MyBaseAdapter(data);

        lv_menu_news_center.setAdapter(adapter);
        lv_menu_news_center.setOnItemClickListener(new MyOnItemClickListener());

        //MenuFragment————NewsCenterPager传递数据
        activity = (MainActivity) getActivity();
        ContentFragment contentFragment = activity.getContentFragment();
        List<BasePager> pagers = contentFragment.pagers;
        pager = (NewsCenterPager) pagers.get(1);
        pager.switchSecMenu(0);
    }
}
