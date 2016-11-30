package com.yyyxt.www.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/29.
 */
public class VresionUpdateInfo implements Serializable {
    private static final long serialVersionUID = 3L;
    public int versionCode;
    public String versionName;
    public String downUrl;
    public String title;
    public String text;

    /**
     * autoup
     * 0  可以取消更新
     * 1  必须更新
     */
    public int autoup;

    public VresionUpdateInfo() {
    }

    @Override
    public String toString() {
        return "VresionUpdateInfo{" +
                "versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", downurl='" + downUrl + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", autoup=" + autoup +
                '}';
    }
}
