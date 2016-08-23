package com.hjl.zz.mynewsclient.utils;

import com.lidroid.xutils.db.annotation.Foreign;

import java.util.IllegalFormatCodePointException;

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
public class NetUrl {

    public static String BASE_URL = "http://10.0.2.2/zhbj";
    //新闻中心的接口url
    public static String NEWS_CENTER_CATEGORIES = BASE_URL+"/categories.json";
    public static final String PHOTOS_URL = BASE_URL + "/photos/photos_1.json";

}
