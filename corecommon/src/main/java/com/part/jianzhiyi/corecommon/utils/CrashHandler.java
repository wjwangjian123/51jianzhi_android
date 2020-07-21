package com.part.jianzhiyi.corecommon.utils;

import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private final static CrashHandler sInstance = new CrashHandler();
    private static Context mContext;

    public static CrashHandler getInstance(){
        return sInstance;
    }

    public void init(Context context) {
        //获取系统默认的异常处理器
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //获取Context，方便内部使用
        mContext = context.getApplicationContext();
        LogUtils.init(context);

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        final String msg = sw.toString();
        LogUtils.i("CrashLog",msg);
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                /*if (MainActivity.isMatRegister) {
                    StatService.reportException(ODApplication.context(), e);
                }*/
            }
            android.os.Process.killProcess(android.os.Process.myPid());

        }
    }
}
