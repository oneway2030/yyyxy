package com.yyyxt.www.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.yyyxt.www.R;
import com.yyyxt.www.commom.BusInstant;
import com.yyyxt.www.commom.EmptyLayout;

import butterknife.ButterKnife;



/**
 * Created by ww on 2016/10/7.
 */

public abstract class BaseActivity extends Activity {

    protected Context mContext = BaseActivity.this;
//    @BindView(R.id.empty_view)
    protected EmptyLayout mErrorLayout;
//    @BindView(R.id.layout)
    protected FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mErrorLayout = (EmptyLayout) findViewById(R.id.empty_view);
        layout = (FrameLayout) findViewById(R.id.layout);
        View viewroot = View.inflate(this, getLayoutID(), null);
        ButterKnife.bind(this, viewroot);
        layout.addView(viewroot);
        BusInstant.getBus().register(this);
        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorClick();
            }
        });
        initView();
        registerCommonButton();
        initData();
    }

    /**
     * 注册多个界面共享的按钮的点击监听
     */
    protected void registerCommonButton() {
        View view = findViewById(R.id.back);
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    /**
     * 返回当前 Activity 使用的布局
     */
    public abstract int getLayoutID();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始数据
     */
    public abstract void initData();

    /**
     * 错误页面的点击回调
     */
    public abstract void errorClick();

    /**
     * 全局吐司
     */
    protected void toast(String msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 全局跳转
     */
    protected void goToActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusInstant.getBus().unregister(this);
    }


    protected void showErrorView(boolean isNotNet) {
        if (isNotNet) {
            layout.setVisibility(View.VISIBLE);
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        } else {
            layout.setVisibility(View.GONE);
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }
    }
}
