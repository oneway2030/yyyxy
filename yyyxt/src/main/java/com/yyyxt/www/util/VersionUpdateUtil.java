package com.yyyxt.www.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.yyyxt.www.bean.VresionUpdateInfo;
import com.yyyxt.www.nohttp.CallServer;
import com.yyyxt.www.nohttp.HttpListener;
import com.yyyxt.www.ui.VersionUpdateDialogActivity;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by Administrator on 2016/6/29.
 */
public class VersionUpdateUtil {
    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public int getVersion(Context mContext) {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void requstVersion(final Context mContext) {
        if (NetUtils.isConnected(mContext)) {
            String url = "http://api.doutubiaoqing.com/index.php/App/Index/updateVersion";
            Request<String> request = NoHttp.createStringRequest(url, RequestMethod.GET);
            CallServer.getRequestInstance().add((Activity) mContext, 0, request, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    VresionUpdateInfo vresionInfo = new Gson().fromJson(response.get(), VresionUpdateInfo.class);
                    if (getVersion(mContext) < vresionInfo.versionCode) {
                        Intent intent = new Intent(mContext, VersionUpdateDialogActivity.class);
                        intent.putExtra("VersionUpdaInfo", vresionInfo);
                        mContext.startActivity(intent);
                    }
                }

                @Override
                public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

                }
            }, true, true);

        }
    }
}