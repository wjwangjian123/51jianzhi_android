package com.part.jianzhiyi.app;

import android.content.Context;
import android.util.Log;

import com.bun.miitmdid.core.JLibrary;
import com.part.jianzhiyi.ad.TTAdManagerHolder;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.part.jianzhiyi.utils.MiitHelper;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import io.reactivex.annotations.NonNull;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * @author:
 * @content：
 * @vision:V1.0.1
 **/
public class ODApplication extends MultiDexApplication {

    private static Context mContext;
    private static final String apiKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALvSHDXhOSMD17Bg0Hu4GtM9EgioguwMVZSKUaLe210KQ9C35KmrMYFbc7CpJFQJ3k5T5GCnr4u8CiM5xl5OK/MCAwEAAQ==";
    public static String city = "北京市";
    //获取手机oaid
    private String oaid;
    private static final String TAG = "ODApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        MultiDex.install(this);
        Context applicationContext = getApplicationContext();
        setRxJavaErrorHandler();
        //穿山甲初始化
        initTTAdSdk();
        MiitHelper miitHelper = new MiitHelper(appIdsUpdater);
        miitHelper.getDeviceIds(this);
    }

    private void initTTAdSdk() {
        //穿山甲SDK初始化
        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        TTAdManagerHolder.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            JLibrary.InitEntry(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//初始化
    }

    private MiitHelper.AppIdsUpdater appIdsUpdater = new MiitHelper.AppIdsUpdater() {
        @Override
        public void OnIdsAvalid(@NonNull String ids) {
            Log.d("Oaid", ids);
            if (ids != null) {
                oaid = ids;
            }
            PreferenceUUID.getInstence().putOaid(oaid);
        }
    };

    public static Context context() {

        return mContext;
    }

    private void setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(throwable -> {
            throwable.printStackTrace();
            Log.i("MyApplication", "MyApplication setRxJavaErrorHandler " + throwable.getMessage());
        });
    }

}
