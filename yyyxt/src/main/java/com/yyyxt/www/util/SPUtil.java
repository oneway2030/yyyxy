package com.yyyxt.www.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

public class SPUtil {
    private static final String SP_NAME = "config";

    private static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = getSp(context);
        return sp.getBoolean(key, false);
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = getSp(context);
        return sp.getString(key, null);
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sp = getSp(context);
        return sp.getInt(key, 0);
    }


    public static void put(Context context, String key, Object value) {
        SharedPreferences sp = getSp(context);
        Editor edit = sp.edit();
        if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            edit.putString(key, (String) value);
        }
        edit.commit();
    }


    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = getSp(context);
        Editor editor = sp.edit();
        editor.remove(key);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = getSp(context);
        Editor editor = sp.edit();
        editor.clear();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = getSp(context);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = getSp(context);
        return sp.getAll();
    }

}
