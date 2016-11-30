package com.yyyxt.www.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yyyxt.www.R;
import com.yyyxt.www.bean.VresionUpdateInfo;
import com.yyyxt.www.util.NetUtils;
import com.yyyxt.www.util.UiUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionUpdateDialogActivity extends Activity {
    /*****
     * 设置对话框的标题 和更新的内容
     */
    private TextView tv_tilte, tv_message, tv_down;
    private RelativeLayout rela_yout;
    private Button bt_update;
    private ProgressBar progress;
    private int isUpDataNmu = 0;
    private VresionUpdateInfo vresionInfo;
    protected static final int DOWNLOAD = 0;
    protected static final int INSTALL_NEW_APK = 1;
    protected static final int ERROR_CONNECTION = 2;
    protected static final int DOWNLOAD_APK = 3;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERROR_CONNECTION:// 联网失败
                    Toast.makeText(getApplicationContext(), "更新版本失败！！",
                            Toast.LENGTH_SHORT).show();
                    break;
                case INSTALL_NEW_APK:// 安装最新的apk
                    finish();
                    installNewApk();
                    break;
                case DOWNLOAD_APK:// 安装最新的apk
                    tv_down.setText(msg.arg1 + "%");
                    progress.setProgress(msg.arg1);
                    SystemClock.sleep(100);
                    break;
                default:
                    break;
            }
        }
    };
    private LinearLayout rl;
    private Button needBt;
    private View pressedView;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);
        setContentView(R.layout.dalog_version_update);
        vresionInfo = (VresionUpdateInfo) getIntent().getSerializableExtra("VersionUpdaInfo");
        initView();
//        initData();
    }


    public void click(View v) {
        switch (v.getId()) {
            case R.id.bt_cancel:
                finish();
                break;
            case R.id.bt_update:
                pressedView = rl;
                startUpdate(rl);
                break;
            case R.id.bt_need_update:
                pressedView = needBt;
                startUpdate(needBt);
                break;
        }
    }
    public void startUpdate(View v) {
        if (storagePermitted(this)) {
            upDate(v);
        } else {
            requestStoragePermission(this);
        }
    }

    private static boolean storagePermitted(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }
    private static void requestStoragePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if(pressedView != null){
                        upDate(pressedView);
                    }
                } else {
                    UiUtils.Toast("请授权读写权限");
                }
                break;
        }
    }




    private void upDate(View v) {
        v.setVisibility(View.GONE);
        rela_yout.setVisibility(View.VISIBLE);
        tv_down.setText("0%");
        downloadNewApk();
    }

    private void initView() {
        tv_tilte = (TextView) findViewById(R.id.tv_tilte);
        tv_message = (TextView) findViewById(R.id.tv_message);
        bt_update = (Button) findViewById(R.id.bt_update);
        rela_yout = (RelativeLayout) findViewById(R.id.rela_yout);
        tv_down = (TextView) findViewById(R.id.tv_down);
        progress = (ProgressBar) findViewById(R.id.progress);
        rl = (LinearLayout) findViewById(R.id.bt_rl);
        needBt = (Button) findViewById(R.id.bt_need_update);
        if (vresionInfo != null) {
            if (0 == vresionInfo.autoup) {
                rl.setVisibility(View.VISIBLE);
                needBt.setVisibility(View.GONE);
            } else if (1 == vresionInfo.autoup) {
                rl.setVisibility(View.GONE);
                needBt.setVisibility(View.VISIBLE);
            }
            String tmepText = vresionInfo.text.replace("\\r\\n", "\r\n");
            tv_tilte.setText(vresionInfo.title);
            tv_message.setText(tmepText);
        }
    }

    // 下载服务器上最新版本
    public void downloadNewApk() {
        if (NetUtils.isConnected(VersionUpdateDialogActivity.this)) {
            new Thread() {
                public void run() {
                    try {
//                        String url2="http://down.doutubiaoqing.com/doutu.apk";
//                        URL url = new URL(url2);
                        URL url = new URL(vresionInfo.downUrl);
                        HttpURLConnection client = (HttpURLConnection) url.openConnection();
                        client.setConnectTimeout(10000);
                        client.setRequestMethod("GET");
                        int code = client.getResponseCode();
                        // 判断 响应码
                        if (code == 200) {
                            InputStream is = client.getInputStream();
                            int length = client.getContentLength();
                            // 把流变为文件 存储在sdcard
                            String name = getFileName(vresionInfo.downUrl);
                            File file = new File(Environment.getExternalStorageDirectory(),
                                    name);
                            FileOutputStream fos = new FileOutputStream(file);
                            byte[] buffer = new byte[1024];
                            int len = 0;
                            int count = 0;
                            while ((len = is.read(buffer)) != -1) {
                                count += len;
                                // 计算进度条位置
                                int progress = (int) (((float) count / (float) length) * 100);
                                // Log.i("WZGS", "显示的位置：：" + progress);
                                // Log.i("ssdd", "总长度===" + length);
                                fos.write(buffer, 0, len);
                                if (progress - isUpDataNmu >= 5) {
                                    isUpDataNmu = progress;
                                    Message msg = Message.obtain();
                                    msg.what = DOWNLOAD_APK;
                                    msg.arg1 = isUpDataNmu;
                                    mHandler.sendMessage(msg);
                                }
                            }
                            fos.close();
                            is.close();
                            // apk下载完成
                            // Log.i(TAG, "apk下载完成");
                            Message msg = Message.obtain();
                            msg.what = INSTALL_NEW_APK;
                            mHandler.sendMessage(msg);
                            // Log.i(TAG, "下载更新");
                        } else {
                            Message msg = Message.obtain();
                            msg.what = ERROR_CONNECTION;
                            mHandler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else {
            Toast.makeText(VersionUpdateDialogActivity.this, "请检查您的网络",
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 截取url 最后的文件名
     *
     * @param url
     * @return
     */
    public String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }


    /**
     * 安装最新的apk 思考： 在系统里面已经存在一个应用安装器（Activity） 隐士意图激活Activity //
     */
    public void installNewApk() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String name = getFileName(vresionInfo.downUrl);
        File file = new File(Environment.getExternalStorageDirectory(), name);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (vresionInfo.autoup == 1) {
                return true;
            } else if (vresionInfo.autoup == 0) {
                finish();

            } else {
                return false;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


}
