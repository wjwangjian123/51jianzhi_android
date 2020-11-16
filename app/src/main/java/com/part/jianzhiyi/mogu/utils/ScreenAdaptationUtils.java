package com.part.jianzhiyi.mogu.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ScreenAdaptationUtils {

    public static void setHeight(Context context, View view, Integer height){
        setSize(context,view,null,height);
    }

    public static void setWidth(Context context, View view, Integer width){
        setSize(context,view,width,null);
    }

    public static void setSize(Context context, View view, Integer width, Integer height) {
        setLayoutParams(context,view, width, height, null, null, null, null);
    }

    public static void setMarginTop(Context context, View view, Integer top){
        setMarginTopAndBottom(context,view,top,null);
    }

    public static void setMarginRight(Context context, View view, Integer right){
        setMarginLeftAndRight(context,view,null,right);
    }

    public static void setMarginBottom(Context context, View view, Integer bottom){
        setMarginTopAndBottom(context,view,null,bottom);
    }

    public static void setMarginLeft(Context context, View view, Integer left){
        setMarginLeftAndRight(context,view,left,null);
    }

    public static void setMarginTopAndBottom(Context context, View view, Integer margin){
        setMargin(context,view,margin,null,margin,null);
    }

    public static void setMarginTopAndBottom(Context context, View view, Integer top, Integer bottom){
        setMargin(context,view,top,null,bottom,null);
    }

    public static void setMarginLeftAndRight(Context context, View view, Integer margin){
        setMargin(context,view,null,margin,null,margin);
    }

    public static void setMarginLeftAndRight(Context context, View view, Integer left, Integer right){
        setMargin(context,view,null,right,null,left);
    }

    public static void setMargin(Context context, View view, Integer top, Integer right, Integer bottom, Integer left){
        setLayoutParams(context,view, null, null, top, right, bottom, left);
    }

    public static void setLayoutParams(Context context , View view, Integer width, Integer height, Integer top, Integer right, Integer bottom, Integer left) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        PhoneScreenUtils phoneScreenUtils=PhoneScreenUtils.getInstance(context);
        if (width != null) {
            layoutParams.width = phoneScreenUtils.getScale(width);
        }
        if (height != null) {
            layoutParams.height = phoneScreenUtils.getScale(height);
        }
        if (top != null) {
            layoutParams.topMargin = phoneScreenUtils.getScale(top);
        }
        if (right != null) {
            layoutParams.rightMargin = phoneScreenUtils.getScale(right);
        }
        if (bottom != null) {
            layoutParams.bottomMargin = phoneScreenUtils.getScale(bottom);
        }
        if (left != null) {
            layoutParams.leftMargin = phoneScreenUtils.getScale(left);
        }
    }
}
