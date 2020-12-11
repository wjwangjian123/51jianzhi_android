package com.part.jianzhiyi.modulemerchants.app;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

/**
 * Created by jyx on 2020/11/19
 *
 * @author jyx
 * @describe
 */
public class MyApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context context() {
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
