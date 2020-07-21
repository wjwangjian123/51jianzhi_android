package com.part.jianzhiyi.corecommon.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.part.jianzhiyi.corecommon.enums.MARGIN;

import java.lang.reflect.Method;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/9/23
 * @modified by:shixinxin
 **/
public class UiUtils {
    /**
     * 设置View的宽高
     *
     * @param view
     * @param width
     * @param height
     */
    public static void setViewWH(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }


    /**
     * 设置View的宽高
     *
     * @param view
     * @param width
     */
    public static void setViewWidth(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        view.setLayoutParams(layoutParams);
    }

    /**
     * 设置View的宽高
     *
     * @param view
     * @param height
     */
    public static void setViewHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    public static boolean isMaxVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 共用设置margin方法
     *
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public static void margin(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * 共用设置margin方法
     *
     * @param v
     * @param margin 边距类型
     * @param value  边距的数值
     */
    public static void margin(View v, MARGIN margin, int value) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            switch (margin) {
                case LEFT:
                    p.leftMargin = value;
                    break;
                case TOP:
                    p.topMargin = value;
                    break;
                case RIGHT:
                    p.rightMargin = value;
                    break;
                case BOTTOM:
                    p.bottomMargin = value;
                    break;
                default:
                    break;
            }
            v.requestLayout();
        }
    }

    /**
     * 所有padding 数值不同
     *
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setPadding(View view, int left, int top, int right, int bottom) {
        view.setPadding(left, top, right, bottom);
    }

    /**
     * 所有的padding值相同
     *
     * @param view
     * @param padding
     */
    public static void setPadding(View view, int padding) {
        view.setPadding(padding, padding, padding, padding);
    }


    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(float dipValue) {
        final float scale = Tools.getApplicationByReflection().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    @SuppressWarnings("ResourceType")
    public static int makeDropDownMeasureSpec(int measureSpec) {
        int mode;
        if (measureSpec == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mode = View.MeasureSpec.UNSPECIFIED;
        } else {
            mode = View.MeasureSpec.EXACTLY;
        }
        return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), mode);
    }


    public static int getVirtualBarHeight(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - display.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    public static int getImageResId(Context context, String name) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        return context.getResources().getIdentifier(name, "drawable", applicationInfo.packageName);
    }

    public static SpannableStringBuilder parsePrice(String price) {
        if (TextUtils.isEmpty(price)) {
            price = "0";
        }
        if (!price.contains("￥")) {
            price = "￥" + price;
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(price);
        ForegroundColorSpan fc = new ForegroundColorSpan(Color.parseColor("#E9525F"));
        int index = price.contains("元") ? price.indexOf("元") : price.contains("/") ? price.indexOf("/") : price.length();

        ssb.setSpan(fc, 0, index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
        AbsoluteSizeSpan abs = new AbsoluteSizeSpan(12, true);
        ssb.setSpan(abs, 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        AbsoluteSizeSpan abs2 = new AbsoluteSizeSpan(18, true);
        ssb.setSpan(abs2, 1, index, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return ssb;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;

    }

}
