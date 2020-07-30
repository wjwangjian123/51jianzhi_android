package com.part.jianzhiyi.app;

import android.content.Context;
import android.util.Log;

import com.bun.miitmdid.core.JLibrary;
import com.part.jianzhiyi.ad.TTAdManagerHolder;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.part.jianzhiyi.utils.MiitHelper;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

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
        //友盟统计 String appkey, String channel, int deviceType, String pushSecret
        UMConfigure.init(this, "5eb65a45978eea078b7e9ac8", Constants.UMENG_NAME, UMConfigure.DEVICE_TYPE_PHONE, "");
        //是否打印日志
        UMConfigure.setLogEnabled(true);
        //打开调试模式
//        MobclickAgent.setDebugMode(true);
        MobclickAgent.setCatchUncaughtExceptions(true);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
//        getTestDeviceInfo(this);
        //fresco图片框架初始化
        FrescoUtil.initialize(this);
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
        try {//初始化
            JLibrary.InitEntry(base);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    public static String[] getTestDeviceInfo(Context context) {
        String[] deviceInfo = new String[2];
        try {
            if (context != null) {
                deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
                deviceInfo[1] = DeviceConfig.getMac(context);
            }
        } catch (Exception e) {
        }
        return deviceInfo;
    }

}
