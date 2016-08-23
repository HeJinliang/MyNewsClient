package com.hjl.zz.mynewsclient.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * 版权所有 违法必究
 *
 * @author hjl
 * @project MyNewsClient
 * @file ${FILE}
 * @create_time 2016/8/15
 * @github https://github.com/HeJinliang
 * =====================================
 */
public abstract class BaseFragment extends Fragment {

    public Context context;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        context = getActivity();

        View view = initView();
        initData();

        return view;
    }

    /**
     * 获取View
     * @return
     */
    public abstract View initView();

    /**
     * 填充数据
     */
    public abstract void initData();

}
