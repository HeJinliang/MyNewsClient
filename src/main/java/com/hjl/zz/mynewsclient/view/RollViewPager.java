package com.hjl.zz.mynewsclient.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.LoginFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjl.zz.mynewsclient.R;
import com.hjl.zz.mynewsclient.adapter.MyDefaultPagerAdapter;
import com.hjl.zz.mynewsclient.utils.LogUtils;
import com.lidroid.xutils.BitmapUtils;

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
public class RollViewPager extends ViewPager {


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                //轮循
                case 88:
                    int currentItem = getCurrentItem();
                    currentItem++;
                    setCurrentItem(currentItem%topImageUrls.size());
                    handler.sendEmptyMessageDelayed(88,2000);
                    break;
            }
        }
    };

    private List<String> topImageUrls;
    private Context context;
    private OnItemClickListener onItemClickListener;

    /**
     * rollViewPager的适配器
     */
    private class MyPagerAdapter extends MyDefaultPagerAdapter {

        public MyPagerAdapter(List pagers) {
            super(pagers);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = (ImageView) View.inflate(context,R.layout.viewpager_item,null);

            BitmapUtils bitmapUtils = new BitmapUtils(context);
            bitmapUtils.display(imageView,topImageUrls.get(position));

            container.addView(imageView);
            return imageView;
        }
    }

    /**
     * 构造
     * @param context
     * @param attrs
     */
    public RollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    /**
     * 构造
     * @param context
     */
    public RollViewPager(Context context) {
        super(context);
        this.context = context;;
    }

    /**
     * 接收填充的数据
     * @param topImageUrls
     */
    public void setTopImageUrls(List<String> topImageUrls){
        this.topImageUrls = topImageUrls;

        setAdapter(new MyPagerAdapter(topImageUrls));
    }

    /**
     * 添加到屏幕上的回掉
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /**
     * 从屏幕上移除的回掉
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        //当RollViewPager从屏幕上移除的时候移除handler消息
        handler.removeMessages(88);
    }


    /**
     * 开始循环滚动
     */
    public void startRoll(){
        handler.sendEmptyMessageDelayed(88,2000);
    }


    private float lastX;
    private float lastY;
    private float currentX;
    private float currentY;

    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private int startTime;
    private int endTime;


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);

                lastX = ev.getX();
                lastY = ev.getY();

                startX = ev.getX();
                startY = ev.getY();
                startTime = getCurrentItem();

                handler.removeMessages(88);

                break;

            case MotionEvent.ACTION_MOVE:

                //rollViewPager和ListView的冲突

                currentX = ev.getX();
                currentY = ev.getY();
                float disX = currentX-lastX;
                float disY = currentY-lastY;

                if (Math.abs(currentX-startX)>Math.abs(currentY-startY)){
                    //说明是左右滑动，将事件留给rollViewPager
                    //RollViewPager当前显示的页面下标
                    int currentItem = getCurrentItem();

                    if (currentItem==0 && disX>0){
                        //最后一页往左滑，事件给父ViewPager
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else if (currentItem==topImageUrls.size()-1 && disX<0){
                        //第一页往右滑，事件给父ViewPager
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }

                }else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                lastX = currentX;
                lastY = currentY;
                break;

            case MotionEvent.ACTION_UP:
                //继续轮播
                startRoll();

                endTime = getCurrentItem();
                endX = ev.getX();
                endY = ev.getY();
                //判断，如果滑动距离小于5px,间隔时间不超过500ms，则认为是点击事件
                if ((endTime-startTime)<500 && (Math.abs(endX-startX))<5 && (Math.abs(endY-startY))<5){
                    if (onItemClickListener != null){//防止用户不设置点击事件
                        onItemClickListener.onItemClick(getCurrentItem());
                    }
                }

                break;

            case MotionEvent.ACTION_CANCEL:
                //继续轮播
                startRoll();
                break;

        }
        return super.onTouchEvent(ev);
    }


    /**
     * RollViewPager的条目点击监听
     * 1.定义一个接口，里面写一个方法
     * 2.成员位置顶一个一个接口对象
     * 3.写一个方法，参数是接口，将接口对象赋值给成员位置的接口对象
     * 4.点击事件里通过接口对象掉接口的方法
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
