package com.part.jianzhiyi.mvp.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.greendao.gen.LoginResponseEntityDao;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.ad.PositionId;
import com.part.jianzhiyi.ad.TTAdManagerHolder;
import com.part.jianzhiyi.ad.UIUtils;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.dbmodel.GreenDaoManager;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.MainThread;
import androidx.annotation.RequiresApi;

@Route(path = "/app/activity/splash")
public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";
    //是否强制跳转到主页面
    private boolean mForceGoMain;
    //开屏广告加载超时时间,建议大于3000,这里为了冷启动第一次加载到广告并且展示,示例设置了3000ms
    private static final int AD_TIME_OUT = 3000;
    private String mCodeId = PositionId.SPLASH_POS_ID;
    //是否请求模板广告
    private boolean mIsExpress = false;
    public boolean canJump = false;
    private TTAdNative mTTAdNative;
    private ImageView mSplashHolder;
    private FrameLayout mSplashContainer;
    private Handler handler = new Handler();

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void initView() {
        mSplashHolder = (ImageView) findViewById(R.id.splash_holder);
        mSplashContainer = (FrameLayout) findViewById(R.id.splash_container);
        setToolBarVisible(false);
        //step2:创建TTAdNative对象
        mTTAdNative = TTAdManagerHolder.get().createAdNative(this);
        getExtraInfo();
        if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestPermission();
        } else {
            // 如果是Android6.0以下的机器，建议在manifest中配置相关权限，这里可以直接调用SDK
            //加载开屏广告
            loadSplashAd();
        }
    }

    private void getExtraInfo() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        String codeId = intent.getStringExtra("splash_rit");
        if (!TextUtils.isEmpty(codeId)) {
            mCodeId = codeId;
        }
        mIsExpress = intent.getBooleanExtra("is_express", false);
    }

    /**
     * 加载开屏广告
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void loadSplashAd() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        int widthPixel = outMetrics.widthPixels;
        int heightPixel = outMetrics.heightPixels;
        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = null;
        if (mIsExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，请传入实际需要的大小，
            //比如：广告下方拼接logo、适配刘海屏等，需要考虑实际广告大小
            float expressViewWidth = UIUtils.getScreenWidthDp(this);
            float expressViewHeight = UIUtils.getHeight(this);
            adSlot = new AdSlot.Builder()
                    .setCodeId(mCodeId)
                    .setSupportDeepLink(true)
                    .setImageAcceptedSize(widthPixel, heightPixel)
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
                    .setExpressViewAcceptedSize(expressViewWidth, expressViewHeight)
                    .build();
        } else {
            adSlot = new AdSlot.Builder()
                    .setCodeId(mCodeId)
                    .setSupportDeepLink(true)
                    .setImageAcceptedSize(widthPixel, heightPixel)
                    .build();
        }

        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {
                Log.d(TAG, String.valueOf(message));
                Log.d(TAG,message);
                goToMainActivity();
            }

            @Override
            @MainThread
            public void onTimeout() {
                Log.d(TAG,"开屏广告加载超时");
                goToMainActivity();
            }

            @Override
            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                Log.d(TAG, "开屏广告请求成功");
                if (ad == null) {
                    return;
                }
                //获取SplashView
                View view = ad.getSplashView();
                if (view != null && mSplashContainer != null && !SplashActivity.this.isFinishing()) {
                    mSplashContainer.removeAllViews();
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
                    mSplashContainer.addView(view);
                    //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                    //ad.setNotAllowSdkCountdown();
                } else {
                    goToMainActivity();
                }

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        Log.d(TAG, "onAdClicked");
                        Log.d(TAG,"开屏广告点击");
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        Log.d(TAG, "onAdShow");
                        Log.d(TAG,"开屏广告展示");
                    }

                    @Override
                    public void onAdSkip() {
                        Log.d(TAG, "onAdSkip");
                        Log.d(TAG,"开屏广告跳过");
                        goToMainActivity();

                    }

                    @Override
                    public void onAdTimeOver() {
                        Log.d(TAG, "onAdTimeOver");
                        Log.d(TAG,"开屏广告倒计时结束");
                        goToMainActivity();
                    }
                });
                if (ad.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    ad.setDownloadListener(new TTAppDownloadListener() {
                        boolean hasShow = false;

                        @Override
                        public void onIdle() {
                        }

                        @Override
                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                            if (!hasShow) {
                                Log.d(TAG,"下载中...");
                                hasShow = true;
                            }
                        }

                        @Override
                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                            Log.d(TAG,"下载暂停...");

                        }

                        @Override
                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                            Log.d(TAG,"下载失败...");

                        }

                        @Override
                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                            Log.d(TAG,"下载完成...");

                        }

                        @Override
                        public void onInstalled(String fileName, String appName) {
                            Log.d(TAG,"安装完成...");

                        }
                    });
                }
            }
        }, AD_TIME_OUT);

    }

    /**
     * 跳转到主页面
     */
    private void goToMainActivity() {
        toActivity();
    }

    /**
     * ----------非常重要----------
     * <p>
     * Android6.0以上的权限适配简单示例：
     * <p>
     * 如果targetSDKVersion >= 23，那么建议动态申请相关权限，再调用广点通SDK
     * <p>
     * SDK不强制校验下列权限（即:无下面权限sdk也可正常工作），但建议开发者申请下面权限，尤其是READ_PHONE_STATE权限
     * <p>
     * READ_PHONE_STATE权限用于允许SDK获取用户标识,
     * 针对单媒体的用户，允许获取权限的，投放定向广告；不允许获取权限的用户，投放通投广告，媒体可以选择是否把用户标识数据提供给优量汇，并承担相应广告填充和eCPM单价下降损失的结果。
     * <p>
     * Demo代码里是一个基本的权限申请示例，请开发者根据自己的场景合理地编写这部分代码来实现权限申请。
     * 注意：下面的`checkSelfPermission`和`requestPermissions`方法都是在Android6.0的SDK中增加的API，如果您的App还没有适配到Android6.0以上，则不需要调用这些方法，直接调用广点通SDK即可。
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }

        // 快手SDK所需相关权限，存储权限，此处配置作用于流量分配功能，关于流量分配，详情请咨询商务;如果您的APP不需要快手SDK的流量分配功能，则无需申请SD卡权限
        if (!(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        // 如果需要的权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
            loadSplashAd();
        } else {
            // 否则，建议请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {
            loadSplashAd();
        } else {
            loadSplashAd();
        }
    }

    private long getDayTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long timeInMillis = calendar.getTimeInMillis();
        return timeInMillis;
    }

    private void toActivity(){
        //判断是否是第一次登陆
        boolean userLogin = PreferenceUUID.getInstence().isUserLogin();
        //判断是否需要填写简历
        boolean showResume = PreferenceUUID.getInstence().getShowResume();
        long currentTime = PreferenceUUID.getInstence().getCurrentTime();
        long millis = System.currentTimeMillis();
        //获取当天0点的时间
        long dayTime = getDayTime();
        if (loadUserInfo() == null) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ToLogin", 1);
            intent.putExtras(bundle);
            startActivity(intent);
        }else {
            if (!userLogin){
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ToLogin", 1);
                intent.putExtras(bundle);
                startActivity(intent);
            }else {
                if (showResume){
                    //判断是否是当天首次进入App
                    //如果是跳转到简历模板
                    if (currentTime<dayTime){
                        PreferenceUUID.getInstence().putCurrentTime(millis);
                        Intent intent = new Intent(SplashActivity.this, ResumeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("ToResume", 1);
                        bundle.putInt("errorType", 1);
                        intent.putExtras(bundle);
                        SplashActivity.this.startActivity(intent);
                    }else {
                        Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                        intent.putExtra("type", 1);
                        startActivity(intent);
                    }
                }else {
                    Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                }
            }
        }
        mSplashContainer.removeAllViews();
        SplashActivity.this.finish();
    }

    private LoginResponseEntity loadUserInfo() {
        LoginResponseEntityDao loginResponseEntityDao = GreenDaoManager.getInstance().getDaoSession().getLoginResponseEntityDao();
        if (loginResponseEntityDao == null) {
            return null;
        }
        return loginResponseEntityDao.queryBuilder().where(LoginResponseEntityDao.Properties.Id.eq(PreferenceUUID.getInstence().getUserId())).unique();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void startIntent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断是否该跳转到主页面
        if (mForceGoMain) {
            goToMainActivity();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        canJump = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mForceGoMain = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            if (keyCode == KeyEvent.KEYCODE_BACK && mSplashContainer.getVisibility() == View.VISIBLE) {
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
