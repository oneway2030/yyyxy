package com.yyyxt.www;

import android.app.Activity;
import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;
import com.yyyxt.www.util.Util;

import butterknife.ButterKnife;
import butterknife.OnClick;



public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //检测版本
//        new VersionUpdateUtil().requstVersion(this);

    }

    @OnClick(R.id.bt)
    public void onClick() {
        Util.goActivity(this,ShareDemoPage.class);

    }






    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


}
