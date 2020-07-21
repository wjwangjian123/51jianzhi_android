package com.part.jianzhiyi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by jyx on 2019/4/22
 *
 * @author jyx
 * @describe
 */
public class IntentUtils {
    public static IntentUtils instence;

    public static IntentUtils getInstence() {
        if (null == instence) {
            instence = new IntentUtils();
        }
        return instence;
    }

    private IntentUtils() {

    }

    /**
     * 不带参数的跳转
     */
    public void intent(Context fromContext, Class<?> cls) {
        Intent intent = new Intent(fromContext, cls);
        fromContext.startActivity(intent);
    }

    /**
     * 带参数的跳转
     */
    public void intent(Context fromContext, Class<?> cls, Bundle bb) {
        Intent intent = new Intent(fromContext, cls);
        intent.putExtras(bb);
        fromContext.startActivity(intent);
    }

    /**
     * 封装 startActivityForResult 无带参数传�? </br>
     * Same as calling
     */
    public void startActivityForResult(Activity fromClass, Class<?> toClass, int requestCode) {
        startActivityForResult(fromClass, toClass, requestCode, null);
    }

    /**
     * 封装 startActivityForResult 带参数传
     */
    public void startActivityForResult(Activity fromClass, Class<?> toClass, int requestCode, Bundle data) {
        Intent intent = new Intent();
        intent.setClass(fromClass, toClass);
        if (null != data) {
            intent.putExtras(data);
        }
        fromClass.startActivityForResult(intent, requestCode);
    }

    /**
     * 接收参数然后在返回数值
     */
    public void setResult(Activity fromContext, Bundle bb, int RESULT_OK) {
        Intent intent = new Intent();
        intent.putExtras(bb);
        fromContext.setResult(RESULT_OK, intent);
        fromContext.finish();
    }

    /**
     * 带参数的跳转
     */
//    public void intent(Context fromContext, Class<?> cls, IntentData data) {
//        Intent intent = new Intent(fromContext, cls);
//        intent.putExtra(Constant.INTENT_KEY, data);
//        fromContext.startActivity(intent);
//    }

//    public IntentData getIntentData(Activity activity) {
//        Intent intent = activity.getIntent();
//        IntentData intentData = (IntentData) intent.getSerializableExtra(Constant.INTENT_KEY);
//        return intentData;
//    }
}
