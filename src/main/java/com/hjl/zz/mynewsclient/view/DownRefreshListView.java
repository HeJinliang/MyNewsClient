package com.hjl.zz.mynewsclient.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.LoginFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hjl.zz.mynewsclient.R;
import com.hjl.zz.mynewsclient.utils.LogUtils;

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
public class DownRefreshListView extends ListView {


    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 88:
                    //将头布局再次设置成下拉刷新状态
                    ll_refresh_header_view.setPadding(0,-measuredHeight,0,0);
                    iv_refresh_header_arrow.setVisibility(View.VISIBLE);
                    Toast.makeText(context,"刷新完成",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    private Context context;
    private LinearLayout ll_refresh_header_root;
    private LinearLayout ll_refresh_header_view;
    private int measuredHeight;

    /**
     * 下拉刷新  paddingTop<0
     */
    private int refresh_down_state = 0;
    /**
     * 释放刷新 paddingTop>=0
     */
    private int refresh_release_state = 1;
    /**
     * 正在刷新 释放刷新&&松手
     */
    private int refresh_ing_state = 2;
    /**
     * 当前刷新状态，默认是下拉刷新
     */
    private int refresh_current_state = refresh_down_state;

    private int paddingTop;
    private RotateAnimation down2up;
    private RotateAnimation up2down;
    private ProgressBar pb_refresh_header;
    private ImageView iv_refresh_header_arrow;
    private TextView tv_refresh_header_text;
    private TextView tv_refresh_header_time;


    public DownRefreshListView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DownRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init(){
        //添加头布局
        View view = View.inflate(context, R.layout.refresh_header,null);
        addHeaderView(view);

        initView(view);

        initAnimation();

        ll_refresh_header_view.measure(0,0);
        measuredHeight = ll_refresh_header_view.getMeasuredHeight();
        ll_refresh_header_view.setPadding(0,-measuredHeight,0,0);//将ll_refresh_header_view完全隐藏

    }

    /**
     * 初始化动画
     */
    private void initAnimation() {

        down2up = new RotateAnimation(0,-180, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        down2up.setFillAfter(true);
        down2up.setDuration(500);

        up2down = new RotateAnimation(-180,-360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        down2up.setFillAfter(true);
        down2up.setDuration(500);

    }

    /**
     * 获取控件
     * @param view
     */
    private void initView(View view) {
        ll_refresh_header_root = (LinearLayout) view.findViewById(R.id.ll_refresh_header_root);
        ll_refresh_header_view = (LinearLayout) view.findViewById(R.id.ll_refresh_header_view);
        pb_refresh_header = (ProgressBar) view.findViewById(R.id.pb_refresh_header);
        iv_refresh_header_arrow = (ImageView) view.findViewById(R.id.iv_refresh_header_arrow);
        tv_refresh_header_text = (TextView) view.findViewById(R.id.tv_refresh_header_text);
        tv_refresh_header_time = (TextView) view.findViewById(R.id.tv_refresh_header_time);
    }

    /**
     * 将自定义View添加到listView头布局中
     * @param view
     */
    public void addCustomView(View view){

        ll_refresh_header_root.addView(view);

    }


    private float downX;
    private float downY;
    private float currentX;
    private float currentY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float disX;
        float disY;
        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                if (downY == 0){
                    downY = ev.getY();
                    break;
                }

                currentX = ev.getX();
                currentY = ev.getY();

                disY = currentY-downY;

                //下拉
                if (disY>0){
                    paddingTop = (int) (-measuredHeight+disY);

                    LogUtils.logInfo("paddingTop:"+paddingTop);

                    ll_refresh_header_view.setPadding(0, paddingTop,0,0);
                    if (refresh_current_state==refresh_down_state && paddingTop>=0){
                        //释放刷新
                        refresh_current_state = refresh_release_state;
                        switchRefreshState(refresh_current_state);
                    }else if (refresh_current_state == refresh_release_state && paddingTop < 0){
                        //下拉刷新
                        refresh_current_state = refresh_down_state;
                        switchRefreshState(refresh_down_state);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (refresh_current_state == refresh_release_state){
                    //正在刷新
                    refresh_current_state = refresh_ing_state;
                    switchRefreshState(refresh_current_state);
                    refresh_current_state = refresh_down_state;
                }else{
                    //从下拉刷新的时候释放，复原位置
                    ll_refresh_header_view.setPadding(0,-measuredHeight,0,0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void switchRefreshState(int state) {
        switch (state){
            //下拉刷新
            case 0:
                iv_refresh_header_arrow.startAnimation(up2down);

                Toast.makeText(context,"下拉刷新",Toast.LENGTH_SHORT).show();

                break;
            //释放刷新
            case 1:
                //如果是刷新过程正再次下拉释放刷新，先清除上一次刷新的延时信息,显示箭头
                handler.removeMessages(88);
                iv_refresh_header_arrow.setVisibility(View.VISIBLE);

                iv_refresh_header_arrow.startAnimation(down2up);
                Toast.makeText(context,"释放刷新",Toast.LENGTH_SHORT).show();

                break;
            //正在刷新
            case 2:
                iv_refresh_header_arrow.setVisibility(View.GONE);
                iv_refresh_header_arrow.clearAnimation();

                ll_refresh_header_view.setPadding(0,0,0,0);

                //将头布局再次设置成下拉刷新状态,并将指示箭头设置朝下
                refresh_current_state = refresh_down_state;
                iv_refresh_header_arrow.startAnimation(up2down);

                Toast.makeText(context,"正在刷新",Toast.LENGTH_SHORT).show();
                //模拟刷新两秒
                handler.sendEmptyMessageDelayed(88,2000);
                break;

        }
    }
}
