package com.yyyxt.www;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.umeng.analytics.MobclickAgent;
import com.yolanda.nohttp.NoHttp;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ww on 2016/9/27.
 */

public class MyApplication extends Application {


//    {
//        //微信 appid appsecret
//        PlatformConfig.setWeixin(AppConfig.WX_APP_ID, AppConfig.WX_APP_SECRET);
//        //新浪微博 appkey appsecret
//        PlatformConfig.setSinaWeibo(AppConfig.SINA_APP_KEY, AppConfig.SINA_APP_SECRET);
//        // QQ和Qzone appid appkey
////        PlatformConfig.setQQZone(AppConfig.QQZONE_APP_ID, AppConfig.QQZONE_APP_KEY);
//        //QQ测试账号
//        PlatformConfig.setQQZone("1105472016", "zk0xKXyL4NGrqJAm");
//    }

    public static Context context;
    public static Handler handler;
    public static Thread mainThread;
    public static int mainThreadId;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();
        NoHttp.initialize(this);
        //开启错误统计
        MobclickAgent.setCatchUncaughtExceptions(true);
        //开启推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }


}
