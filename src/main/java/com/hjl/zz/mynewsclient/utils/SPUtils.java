package com.hjl.zz.mynewsclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

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
public class SPUtils {

    /**
     * 获取缓存
     * @param context
     * @param key
     * @return
     */
    public static String getCacheJson(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences("cache",Context.MODE_PRIVATE);

        return sp.getString(key,"");
    }

    /**
     * 添加缓存
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setCacheJson(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences("cache",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
}
