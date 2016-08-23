package com.hjl.zz.mynewsclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hjl.zz.mynewsclient.R;
import com.hjl.zz.mynewsclient.sp.PathResouce;

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
public class SplashActivity extends Activity {

    Handler handler = new Handler(){


        @Override
        public void handleMessage(Message msg) {
            if (isFirstOpenGuide){
                Intent intent = new Intent(activity,GuideActivity.class);
                startActivity(intent);
                sp.edit().putBoolean(PathResouce.KEY_FIRST_OPEN_GUIDE,false).commit();
            }else{
                Intent intent = new Intent(activity,MainActivity.class);
                startActivity(intent);
            }

            finish();
        }
    };

    private SplashActivity activity = this;
    private boolean isFirstOpenGuide;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sp = getSharedPreferences(PathResouce.SP_NAME, Context.MODE_PRIVATE);
        isFirstOpenGuide = sp.getBoolean(PathResouce.KEY_FIRST_OPEN_GUIDE,true);

        handler.sendEmptyMessageDelayed(88,2000);
    }
}
