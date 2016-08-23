package com.hjl.zz.mynewsclient.secPager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hjl.zz.mynewsclient.bean.NewsCenterBean;
import com.hjl.zz.mynewsclient.pager.BasePager;

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
public class SubjectPager extends BasePager {
    public SubjectPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("专题");
        return textView;
    }

    @Override
    public void initData() {

    }
}
