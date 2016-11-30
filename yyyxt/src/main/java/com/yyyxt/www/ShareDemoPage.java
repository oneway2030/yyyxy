package com.yyyxt.www;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.yyyxt.www.util.UiUtils;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * Created by ww on 2016/9/28.
 */

public class ShareDemoPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedemopage);
        ButterKnife.bind(this);
        //检测版本
        ShareSDK.initSDK(this);

    }


    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                showShare(QQ.NAME);
                break;
            case R.id.bt2:
                showShare(QZone.NAME);
                break;
            case R.id.bt3:
                showShare(Wechat.NAME);
                break;
            case R.id.bt4:
                showShare(WechatMoments.NAME);
                break;
        }
    }


    private void showShare(String name) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("测试分享的标题");
        sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
        sp.setText("测试分享的文本");
        sp.setImageUrl("http://www.someserver.com/测试图片网络地址.jpg");
        sp.setSite("发布分享的网站名称");
        sp.setSiteUrl("发布分享网站的地址");
        Platform qzone = ShareSDK.getPlatform(name);
        qzone.setPlatformActionListener(paListener); // 设置分享事件回调
// 执行图文分享
        qzone.share(sp);
    }

    PlatformActionListener paListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            UiUtils.Toast("成功");
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            UiUtils.Toast("失敗");
        }

        @Override
        public void onCancel(Platform platform, int i) {
            UiUtils.Toast("取消");
        }
    };
}
