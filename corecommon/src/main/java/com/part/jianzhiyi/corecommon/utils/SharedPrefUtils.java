package com.part.jianzhiyi.corecommon.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences封装
 */
public class SharedPrefUtils {
    private static final String SP_NAME = "config";
    private static final String CODE_COUNT = "CodeCount";
    private static SharedPreferences sp;
    private static SharedPreferences codeSp;

    public static void putBoolean(Context ctx, String key, boolean value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存字符串
     */
    public static void putString(Context ctx, String key, String value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).apply();
    }

    /**
     * 获取字符串
     */
    public static String getString(Context ctx, String key, String defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    public static void putInt(Context ctx, String key, int value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(Context ctx, String key, int defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    public static void putLong(Context ctx, String key, long value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putLong(key, value).apply();
    }

    public static long getLong(Context ctx, String key, long defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getLong(key, defValue);
    }

    // 以下是统计存储获取验证码的次数
    public static void saveCodeCount(Context ctx, String key, int value) {
        if (codeSp == null) {
            codeSp = ctx.getSharedPreferences(CODE_COUNT, Context.MODE_PRIVATE);
        }
        codeSp.edit().putInt(key, value).apply();
    }

    public static int getCodeCount(Context ctx, String key, int defValue) {
        if (codeSp == null) {
            codeSp = ctx.getSharedPreferences(CODE_COUNT, Context.MODE_PRIVATE);
        }
        return codeSp.getInt(key, defValue);
    }

    public static void clearCodeCount(Context ctx) {
        if (codeSp == null) {
            codeSp = ctx.getSharedPreferences(CODE_COUNT, Context.MODE_PRIVATE);
        }
        codeSp.edit().clear().apply();
    }

    public static void remove(String key) {
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.commit();
    }

    public static void clear() {
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.commit();
    }
}