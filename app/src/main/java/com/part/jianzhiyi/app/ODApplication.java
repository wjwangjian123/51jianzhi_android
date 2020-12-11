package com.part.jianzhiyi.app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.bun.miitmdid.core.JLibrary;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.part.jianzhiyi.ad.TTAdManagerHolder;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;
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

        // 这两行必须写在init之前，否则这些配置在init过程中将无效
//        if (true) {
//            ARouter.openLog();//打印日志
//            ARouter.openDebug();//开启调试模式（如果在instantrun模式下运行，必须开启调试模式，线上版本需要关闭，否则会有安全风险）
//        }
        ARouter.init(this);
        setRxJavaErrorHandler();
        //友盟统计 String appkey, String channel, int deviceType, String pushSecret
        UMConfigure.init(this, "5eb65a45978eea078b7e9ac8", Constants.UMENG_NAME, UMConfigure.DEVICE_TYPE_PHONE, "");
        //是否打印日志
        UMConfigure.setLogEnabled(false);
        //打开调试模式
//        MobclickAgent.setDebugMode(true);
        MobclickAgent.setCatchUncaughtExceptions(true);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        //通过集成代码方式获取获取手机中的设备识别信息
//        getTestDeviceInfo(this);
        //fresco图片框架初始化
        FrescoUtil.initialize(this);
        //穿山甲初始化
        initTTAdSdk();
        initMeiqia();
        MiitHelper miitHelper = new MiitHelper(appIdsUpdater);
        miitHelper.getDeviceIds(this);
        initCloudChannel(this);
//        // 注册方法会自动判断是否支持小米系统推送，如不支持会跳过注册。
//        MiPushRegister.register(applicationContext, "2882303761518753186", "5221875346186");
//        // 注册方法会自动判断是否支持华为系统推送，如不支持会跳过注册。
//        HuaWeiRegister.register(this);
//        // OPPO通道注册
//        OppoRegister.register(applicationContext, "6c5aca3233554e28ae1a822a4ffcdb0a", "e4d6c48123f34d859971622e6f782f1d");
//        // 魅族通道注册
//        MeizuRegister.register(applicationContext, "3290513", "ceef586eb967432dab431044c1ff4fef");
//        // VIVO通道注册
//        VivoRegister.register(applicationContext);
    }

    private void initTTAdSdk() {
        //穿山甲SDK初始化
        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        TTAdManagerHolder.init(this);
    }

    private void initMeiqia() {
        //初始化
        MQConfig.init(this, Constants.MEIQIA_KEY, new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {
//                Toast.makeText(mContext, "init success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String message) {
//                Toast.makeText(mContext, "int failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            JLibrary.InitEntry(base);
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

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        // 创建notificaiton channel
        this.createNotificationChannel();
        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                String deviceId = pushService.getDeviceId();
                PreferenceUUID.getInstence().putPush_id(deviceId);
                Log.d(TAG, deviceId);
                Log.d(TAG, "init cloudchannel success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知渠道的id
            String id = "1";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = "51兼职";
            // 用户可以看到的通知渠道的描述
            String description = "51兼职";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(false);
            mChannel.setVibrationPattern(null);
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

}
