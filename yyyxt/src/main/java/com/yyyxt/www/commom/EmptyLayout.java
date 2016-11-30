package com.yyyxt.www.commom;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yyyxt.www.R;


/**
 * Created by ww on 2016/10/20.
 */

public class EmptyLayout extends LinearLayout implements View.OnClickListener {
    //加载成功 不显示emptylayout了
    public static final int HIDE_LAYOUT = 4;
    //网络没有连接
    public static final int NETWORK_ERROR = 1;
    //正在加载数据
    public static final int NETWORK_LOADING = 2;
    //没有数据
    public static final int NODATA = 3;

    public static final int NODATA_ENABLE_CLICK = 5;

    private ProgressBar animProgress;
    private final Context context;
    public ImageView img;
    public Button bt;
    private OnClickListener listener;
    private int mErrorState;
    private String strNoDataContent = "";
    private TextView tv;

    public EmptyLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.view_error_layout, null);
        img = (ImageView) view.findViewById(R.id.error_iv);
        tv = (TextView) view.findViewById(R.id.error_tv);
        bt = (Button) view.findViewById(R.id.error_bt);
        RelativeLayout mLayout = (RelativeLayout) view.findViewById(R.id.pageerrLayout);
        animProgress = (ProgressBar) view.findViewById(R.id.pb);
//        setBackgroundColor(-1);
//        setBackgroundResource(R.mipmap.main_bg);
        setOnClickListener(this);
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // setErrorType(NETWORK_LOADING);
                if (listener != null)
                    listener.onClick(v);
            }
        });
        addView(view);
        changeErrorLayoutBgMode(context);
    }

    public void changeErrorLayoutBgMode(Context context1) {
        // mLayout.setBackgroundColor(SkinsUtil.getColor(context1,
        // "bgcolor01"));
        // tv.setTextColor(SkinsUtil.getColor(context1, "textcolor05"));
    }

    public void dismiss() {
        mErrorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoadError() {
        return mErrorState == NETWORK_ERROR;
    }

    public boolean isLoading() {
        return mErrorState == NETWORK_LOADING;
    }

    @Override
    public void onClick(View v) {
            // setErrorType(NETWORK_LOADING);
            if (listener != null)
                listener.onClick(v);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onSkinChanged();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void onSkinChanged() {
    }

    public void setErrorMessage(String msg) {
        tv.setText(msg);
    }

    /**
     * 新添设置背景
     *
     * @param imgResource 图片的id
     * @param msg         图片下面的textView显示的文字
     */
    public void setErrorImag(int imgResource, String msg) {
        try {
            img.setBackgroundResource(imgResource);
            tv.setText(msg);
        } catch (Exception e) {
        }
    }

    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case NETWORK_ERROR:
                mErrorState = NETWORK_ERROR;
                setVisibility(View.VISIBLE);
                if (isConnectivity(context)) {
                    tv.setText(R.string.error_view_load_error_click_to_refresh);
                    img.setBackgroundResource(R.mipmap.errorbg);
                } else {
                    tv.setText(R.string.error_view_network_error_click_to_refresh);
                    img.setBackgroundResource(R.mipmap.errorbg);
                }
                bt.setVisibility(View.VISIBLE);
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                break;
            case NETWORK_LOADING:
                setVisibility(View.VISIBLE);
                mErrorState = NETWORK_LOADING;
                animProgress.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
                bt.setVisibility(View.GONE);
                tv.setText(R.string.error_view_loading);
                break;
            case NODATA:
                setVisibility(View.VISIBLE);
                mErrorState = NODATA;
                img.setBackgroundResource(R.mipmap.errorbg);
                img.setVisibility(View.VISIBLE);
                bt.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                setTvNoDataContent();
                break;
            case HIDE_LAYOUT:
                setVisibility(View.GONE);
                break;
            case NODATA_ENABLE_CLICK:
                setVisibility(View.VISIBLE);
                mErrorState = NODATA_ENABLE_CLICK;
                img.setBackgroundResource(R.mipmap.errorbg);
                img.setVisibility(View.VISIBLE);
                bt.setVisibility(View.GONE);
                animProgress.setVisibility(View.GONE);
                setTvNoDataContent();
                break;
            default:
                break;
        }
    }

    public void setNoDataContent(String noDataContent) {
        strNoDataContent = noDataContent;
    }

    public void setOnLayoutClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setTvNoDataContent() {
        if (!strNoDataContent.equals(""))
            tv.setText(strNoDataContent);
        else
            tv.setText(R.string.error_view_no_data);
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)
            mErrorState = HIDE_LAYOUT;
        super.setVisibility(visibility);
    }


    /**
     * 描述：是否有网络连接.androidbase中AbWifiUtil中的方法
     *
     * @param context
     * @return
     */
    public static boolean isConnectivity(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return ((connectivityManager.getActiveNetworkInfo() != null && connectivityManager
                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || telephonyManager
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

}