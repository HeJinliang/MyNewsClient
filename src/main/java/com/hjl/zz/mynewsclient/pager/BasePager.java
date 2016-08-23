package com.hjl.zz.mynewsclient.pager;

import android.content.Context;
import android.view.View;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

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
public abstract class BasePager {

    public Context context;

    public BasePager(Context context){
        this.context = context;
    }

    public abstract View initView();

    public abstract void initData();


    /**
     * 请求网络数据
     * @param url
     * @param callBack
     */
    public void requestInterData(String url, RequestCallBack<String> callBack){
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET,url,null,callBack);
    }

}
