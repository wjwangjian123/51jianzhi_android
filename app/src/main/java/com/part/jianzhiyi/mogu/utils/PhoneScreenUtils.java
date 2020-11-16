package com.part.jianzhiyi.mogu.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 手机分辨率适配工具类
 */

public class PhoneScreenUtils {
    private Context context;
    private final static String TAG = PhoneScreenUtils.class.getSimpleName();
    private static PhoneScreenUtils mPhoneUtils;
    private DisplayMetrics displayMetrics;

    public static int screenScale = 1080;

    public static int titleTextSize = 54;
    public static int bigTextSize = 50;
    public static int normalTextSize = 46;
    public static int middleTextSize = 42;
    public static int smallTextSize = 38;
    public static int tipsTextSize = 32;
    public static int minTextSize = 22;

    private PhoneScreenUtils(Context context) {
        this.context = context;
    }

    public static PhoneScreenUtils getInstance(Context context) {
        if (mPhoneUtils == null) {
            mPhoneUtils = new PhoneScreenUtils(context);
        }
        return mPhoneUtils;
    }

    public DisplayMetrics getPhoneScreen() {
        if (displayMetrics == null) {
            displayMetrics = new DisplayMetrics();
            WindowManager WM = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            WM.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics;
    }

    public int getWidthPixels() {
        return getPhoneScreen().widthPixels;
    }

    public int getHeightPixels() {
        return getPhoneScreen().heightPixels;
    }

    public int getDensityDpi() {
        return getPhoneScreen().densityDpi;
    }

    public int getScale(float num) {
        return (int) (num * getWScale(screenScale));
    }

    public int getAppointResolutionScale(float num, float resolution) {
        return (int) (num * getWScale((int) resolution));
    }

    public int getScaleTextSize(int size) {
        return px2sp(getScale(size));
    }

    public int getTitleTextSize() {
        return px2sp(getScale(titleTextSize));
    }

    public int getBigTextSize() {
        return px2sp(getScale(bigTextSize));
    }

    public int getNormalTextSize() {
        return px2sp(getScale(normalTextSize));
    }

    public int getMiddleTextSize() {
        return px2sp(getScale(middleTextSize));
    }

    public int getSmallTextSize() {
        return px2sp(getScale(smallTextSize));
    }

    public int getTipsTextSize() {
        return px2sp(getScale(tipsTextSize));
    }

    public int getMinTextSize() {
        return px2sp(getScale(minTextSize));
    }

    private float getWScale(int w) {
        float mscale_width = 0;
        mscale_width = getWidthPixels() * 1.0f / w * 1.0f;
        return mscale_width;
    }

    private float getHScale(int h) {
        float mscale_height = 0;
        mscale_height = getHeightPixels() * 1.0f / h * 1.0f;
        return mscale_height;
    }

    private int px2sp(float pxValue) {
        final float scale = getPhoneScreen().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }
}
