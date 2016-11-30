package com.yyyxt.www.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yyyxt.www.R;
import com.yyyxt.www.util.UiUtils;

import java.util.HashMap;

import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;



/**
 * Created by ww on 2016/10/24.
 */

public class CommomPop extends PopupWindow {

    private  View mMenuView;
    private Context mContext;
    String ShareUrl;

    public CommomPop(Context context) {
        super(context);
        this.mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mMenuView = inflater.inflate(R.layout.invitation_pop, null);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        ButterKnife.bind(this, mMenuView);
        ShareSDK.initSDK(context);
        //显示图片
//        loadAD();
        //设置按钮监听
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popwindow_anim_style);
        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new
//
// ColorDrawable(0xebeced);
        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
//        mMenuView.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                int height = mMenuView.findViewById(R.id.pop_ll).getTop();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });

    }
//    showShare(QQ.NAME);
//    showShare(Wechat.NAME);
//    Util.copyContext(ShareUrl, mContext);


    private void showShare(String name) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("测试分享的标题");
        sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
        sp.setText("测试分享的文本");
        sp.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        sp.setSite("发布分享的网站名称");
        sp.setSiteUrl("发布分享网站的地址");
        if (WechatMoments.NAME.equals(name) || Wechat.NAME.equals(name)) {
            sp.setShareType(Platform.SHARE_IMAGE);
        }
        Platform mPlatform = ShareSDK.getPlatform(name);
        mPlatform.setPlatformActionListener(paListener); // 设置分享事件回调
// 执行图文分享
        mPlatform.share(sp);
    }

    PlatformActionListener paListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            UiUtils.Toast("成功");
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            UiUtils.Toast("失败");
        }

        @Override
        public void onCancel(Platform platform, int i) {
            UiUtils.Toast("取消");
        }
    };
}
