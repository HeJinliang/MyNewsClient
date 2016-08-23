package com.hjl.zz.mynewsclient.pager;

import android.content.Context;
import android.view.View;

import com.hjl.zz.mynewsclient.R;

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
public class GovAffairsPager extends BasePager {

    public GovAffairsPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_gov_affairs,null);
        return view;
    }

    @Override
    public void initData() {

    }
}
