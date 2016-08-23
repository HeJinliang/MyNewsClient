package com.hjl.zz.mynewsclient.utils;

import android.util.Log;

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
public class LogUtils {

    private static boolean isOpenLog = true;

    public static void logInfo(String content){
        if (isOpenLog){
            Log.i("hjlLog", "内容: "+content);
        }
    }

}
