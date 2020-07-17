package com.zhangyf.localebook.util;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheUtils {
    private static final String NAME = "EBook";
    private static SharedPreferences sp = null;


    // 存Strings
    public static void putString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAME,
                    Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }


    // 取String
    public static String getString(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAME,
                    Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }


    //存Int值
    public static void putInt(Context context, String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAME,
                    Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }


    //取int值
    public static int getInt(Context context, String key, int defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAME,
                    Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }


}