package com.part.jianzhiyi.mvp.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.greendao.gen.MessageResponseEntityDao;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.IntentConstant;
import com.part.jianzhiyi.corecommon.utils.AppUtil;
import com.part.jianzhiyi.corecommon.utils.CrashHandler;
import com.part.jianzhiyi.customview.NoScrollViewPager;
import com.part.jianzhiyi.dbmodel.GreenDaoManager;
import com.part.jianzhiyi.dialog.DialogVersionUpdate;
import com.part.jianzhiyi.local.LocationService;
import com.part.jianzhiyi.model.entity.ActJobListEntity;
import com.part.jianzhiyi.model.entity.ActivityEntity;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.model.entity.MessageResponseEntity;
import com.part.jianzhiyi.mvp.contract.MainContract;
import com.part.jianzhiyi.mvp.presenter.MainPresenter;
import com.part.jianzhiyi.mvp.ui.fragment.ChoiceFragment;
import com.part.jianzhiyi.mvp.ui.fragment.HomeFragment;
import com.part.jianzhiyi.mvp.ui.fragment.InformationFragment;
import com.part.jianzhiyi.mvp.ui.fragment.MineFragment;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.part.jianzhiyi.utils.HProgressDialogUtils;
import com.part.jianzhiyi.utils.UpdateAppHttpUtil;
import com.umeng.analytics.MobclickAgent;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.service.DownloadService;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@Route(path = "/app/activity/main")
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.IMainView, View.OnClickListener {

    private NoScrollViewPager mMainViewpager;
    private ImageView mMainIvHome;
    private TextView mMainTvHome;
    private LinearLayout mMainLinearHome;
    private ImageView mMainIvChoice;
    private TextView mMainTvChoice;
    private LinearLayout mMainLinearChoice;
    private ImageView mMainIvMessage;
    private TextView mMainTvMessage;
    private RelativeLayout mMainLinearMessage;
    private ImageView mMainIvMine;
    private TextView mMainTvMine;
    private LinearLayout mMainLinearMine;
    private ImageView mViewIvRed;

    private String TAG = "MainActivity";
    private View[] mViews;
    private View[] mViewImgs;
    private int type = 1;
    //    private boolean isUIFirstShow = true;
    private LocationService locationService = null;

    @Override
    protected void init() {
        super.init();
        new Thread(new Runnable() {
            @Override
            public void run() {
                openLocationService();
            }
        }).start();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        CrashHandler.getInstance().init(getApplicationContext());
    }

    private HomeFragment homeFragment = null;
    private Boolean openLocationService() {
        if (locationService != null) {
            return true;
        }
        locationService = new LocationService(ODApplication.context(), false);
        locationService.start();
        locationService.getSubject().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String city) {
                Log.i("MainActivity", city);
//                mPresenter.androidInfo(this@MainActivity)
                if (homeFragment != null) {
                    homeFragment.locationCity(city);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i("MainActivity", "错误信息:" + e.getMessage());
                if (homeFragment != null) {
                    String city = homeFragment.getCity();
                    ODApplication.city = city;
                }
            }

            @Override
            public void onComplete() {

            }
        });
        return true;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            type = intent.getIntExtra("type", 0);
            if (type == 2) {
                setTabSelected(0);
                mMainViewpager.setCurrentItem(0);
            }
            if (intent.getData()!=null){
                String type=intent.getData().getQueryParameter("type");
                if (!type.equals(null)){
                    if (type.equals("4")){
                        setTabSelected(0);
                        mMainViewpager.setCurrentItem(0);
                    }else if (type.equals("5")){
                        setTabSelected(2);
                        mMainViewpager.setCurrentItem(2);
                    }
                }
//                Log.d("aaaaaaaaaaaaa","2"+intent.getData().toString()+","+type);
            }
        }
    }

    private int isShowType = 1;
    @Override
    protected void initView() {
        mMainViewpager = (NoScrollViewPager) findViewById(R.id.main_viewpager);
        mMainIvHome = (ImageView) findViewById(R.id.main_iv_home);
        mMainTvHome = (TextView) findViewById(R.id.main_tv_home);
        mMainLinearHome = (LinearLayout) findViewById(R.id.main_linear_home);
        mMainIvChoice = (ImageView) findViewById(R.id.main_iv_choice);
        mMainTvChoice = (TextView) findViewById(R.id.main_tv_choice);
        mMainLinearChoice = (LinearLayout) findViewById(R.id.main_linear_choice);
        mMainIvMessage = (ImageView) findViewById(R.id.main_iv_message);
        mMainTvMessage = (TextView) findViewById(R.id.main_tv_message);
        mMainLinearMessage = (RelativeLayout) findViewById(R.id.main_linear_message);
        mViewIvRed = (ImageView) findViewById(R.id.view_iv_red);
        mMainIvMine = (ImageView) findViewById(R.id.main_iv_mine);
        mMainTvMine = (TextView) findViewById(R.id.main_tv_mine);
        mMainLinearMine = (LinearLayout) findViewById(R.id.main_linear_mine);
        mMainLinearHome.setOnClickListener(this);
        mMainLinearChoice.setOnClickListener(this);
        mMainLinearMessage.setOnClickListener(this);
        mMainLinearMine.setOnClickListener(this);
        setToolBarVisible(false);
        mViews = new View[]{mMainTvHome, mMainTvChoice, mMainTvMessage, mMainTvMine};
        mViewImgs = new View[]{mMainIvHome, mMainIvChoice, mMainIvMessage, mMainIvMine};
        setTabSelected(0);
        initViewPager();
        type = getIntent().getIntExtra("type", 0);
        mPresenter.androidInfo(MainActivity.this);
        if (getIntent().getData()!=null){
            String type=getIntent().getData().getQueryParameter("type");
            if (!type.equals(null)){
                if (type.equals("4")){
                    setTabSelected(0);
                    mMainViewpager.setCurrentItem(0);
                }else if (type.equals("5")){
                    setTabSelected(2);
                    mMainViewpager.setCurrentItem(2);
                }
            }
//            Log.d("aaaaaaaaaaaaa","1"+getIntent().getData().toString()+","+type);
        }
    }

    private void initViewPager() {
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ChoiceFragment());
        fragments.add(new InformationFragment());
        fragments.add(new MineFragment());
        //设置viewpager的缓存
        mMainViewpager.setOffscreenPageLimit(3);
        //禁止viewpager左右滑动
        mMainViewpager.setNoScroll(true);
        mMainViewpager.setCurrentItem(0);
        mMainViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_linear_home) {
            mMainViewpager.setCurrentItem(0);
            setTabSelected(0);
            MobclickAgent.onEvent(this, "main_home");
        } else if (v.getId() == R.id.main_linear_choice) {
            mMainViewpager.setCurrentItem(1);
            setTabSelected(1);
            MobclickAgent.onEvent(this, "main_choice");
        } else if (v.getId() == R.id.main_linear_message) {
            mMainViewpager.setCurrentItem(2);
            setTabSelected(2);
            MobclickAgent.onEvent(this, "main_information");
        } else if (v.getId() == R.id.main_linear_mine) {
            mMainViewpager.setCurrentItem(3);
            setTabSelected(3);
            MobclickAgent.onEvent(this, "main_mine");
        }
    }

    private void setTabSelected(int position) {
        int count = mViews.length;
        for (int i = 0; i < count; i++) {
            if (i == position) {
                mViews[i].setSelected(true);
                mViewImgs[i].setSelected(true);
            } else {
                mViews[i].setSelected(false);
                mViewImgs[i].setSelected(false);
            }
        }
    }

    @Override
    protected void initData() {
        mPresenter.getIp();
        mPresenter.getConfig();
//        boolean privacyState = PreferenceUUID.getInstence().getPrivacyState();
//        if (privacyState) {
//            mPresenter.getConfig();
//        }
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    private Long getDayTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    private void finishApp() {
        showToast("不同意将无法使用app");
    }

    @Override
    public void updategetConfig(ConfigEntity configEntity) {
        int versionCode = AppUtil.getVersionCode(MainActivity.this);
        if (configEntity.getData() != null) {
            PreferenceUUID.getInstence().putShowWx(configEntity.getData().getShow_wx());
            int androidVersion = configEntity.getData().getAndroid_version();
            String androidTitle = configEntity.getData().getAndroid_title();
            String androidDesc = configEntity.getData().getAndroid_desc();
            if (androidVersion > versionCode) {
                DialogVersionUpdate versionUpdate = new DialogVersionUpdate(MainActivity.this, androidTitle, androidDesc, new DialogVersionUpdate.OnJoinedClickListener() {
                    @Override
                    public void onJoinedClick() {
                        initPermission(configEntity);
                    }
                });
                versionUpdate.show();
            } else {
                long currentTime = PreferenceUUID.getInstence().getActionTime();
                long millis = System.currentTimeMillis();
                //获取当天0点的时间
                Long dayTime = getDayTime();
                //如果是展示活动
                if (currentTime < dayTime) {
                    PreferenceUUID.getInstence().putActionTime(millis);
                    mPresenter.getAction();
                }
            }
        }
    }

    private void initPermission(ConfigEntity entity) {
        //需要存储权限，做6.0权限适配
        if (Build.VERSION.SDK_INT < 23) {
            initDown(entity);
        } else {
            //判断是否有权限
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission_group.STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //在这里申请
                AndPermission.with(MainActivity.this)
                        .runtime().permission(Permission.Group.STORAGE)
                        .onDenied(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                showToast("未获得权限");
                            }
                        })
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                initDown(entity);
                            }
                        })
                        .start();
            } else {
                initDown(entity);
            }
        }
    }

    private String path;
    private File mFile;

    private void initDown(ConfigEntity entity) {
        UpdateAppBean updateAppBean = new UpdateAppBean();
        //设置 apk 的下载地址
        updateAppBean.setApkFileUrl(entity.getData().getAndroid_url());
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED || !Environment.isExternalStorageRemovable()) {
            try {
                path = getExternalCacheDir().getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(path)) {
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            }
        } else {
            path = getCacheDir().getAbsolutePath();
        }
        File dir = new File(path, "jianzhi.apk");
        if (!dir.exists()) {
            dir.mkdir();
        }
        //设置apk 的保存路径
        updateAppBean.setTargetPath(path);
        //实现网络接口，只实现下载就可以
        updateAppBean.setHttpManager(new UpdateAppHttpUtil());
        UpdateAppManager.download(MainActivity.this, updateAppBean, new DownloadService.DownloadCallback() {
            @Override
            public void onStart() {
                HProgressDialogUtils.showHorizontalProgressDialog(MainActivity.this, "下载进度", false);
                Log.d(TAG, "onStart() called");
            }

            @Override
            public void onProgress(float progress, long totalSize) {
                HProgressDialogUtils.setProgress(Math.round(progress * 100));
                Log.d(TAG, "onProgress() called with: progress = [$progress], totalSize = [$totalSize]");
            }

            @Override
            public void setMax(long totalSize) {
                Log.d(TAG, "setMax() called with: totalSize = [$totalSize]");
            }

            @Override
            public boolean onFinish(File file) {
                HProgressDialogUtils.cancel();
                if (file != null) {
                    mFile = file;
                    installAPK(file);
                }
                Log.d(TAG, "onFinish() called with: file = [" + file.getAbsolutePath() + "]");
                return true;
            }

            @Override
            public void onError(String msg) {
                HProgressDialogUtils.cancel();
                Log.e(TAG, "onError() called with: msg = [$msg]");
            }

            @Override
            public boolean onInstallAppAndAppOnForeground(File file) {
                Log.d(TAG, "onInstallAppAndAppOnForeground() called with: file = [$file]");
                return false;
            }
        });
    }

    /**
     * 安装Apk
     */
    private void installAPK(File file) {
        if (file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //安装完成后，启动app（源码中少了这句话）
        if (null != file) {
            try {
                //兼容7.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(MainActivity.this, AppUtil.getAppProcessName(MainActivity.this) + ".fileProvider", file);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    //兼容8.0
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        boolean hasInstallPermission = getPackageManager().canRequestPackageInstalls();
                        if (!hasInstallPermission) {
                            startInstallPermissionSettingActivity();
                            return;
                        }
                    }
                } else {
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                if (getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                    startActivity(intent);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Uri packageUri = Uri.parse("package:" + AppUtil.getAppProcessName(MainActivity.this));
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, 10086);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086) {
            installAPK(mFile);
        }
    }

    @Override
    public void updategetAction(ActivityEntity activityEntity) {
        //活动弹框
        if (activityEntity.getData().getId() == null || activityEntity.getData().getId() == "") {
            return;
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        AlertDialog alertDialog1 = alertDialog.create();
        View view = View.inflate(MainActivity.this, R.layout.dialog_main_action, null);
        ImageView cancel = view.findViewById(R.id.dialog_iv_cancel);
        ImageView img = view.findViewById(R.id.dialog_iv_bg);
        Glide.with(MainActivity.this).load(activityEntity.getData().getImage()).into(img);
        alertDialog1.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog1.setCanceledOnTouchOutside(false);
        alertDialog1.setCancelable(false);
        alertDialog1.setView(view);
        //显示
        alertDialog1.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(MainActivity.this, "home_action_close");
                alertDialog1.dismiss();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(MainActivity.this, "home_action");
                alertDialog1.dismiss();
                if (activityEntity.getData().getType().equals("1")) {
                    Intent intent = new Intent(MainActivity.this, ActionListActivity.class);
                    intent.putExtra("id", activityEntity.getData().getId());
                    startActivity(intent);
                } else if (activityEntity.getData().getType().equals("2")) {
                    String urls = activityEntity.getData().getUrl();
                    if (!TextUtils.isEmpty(urls)) {
                        if (activityEntity.getData().getUrl_redirect() == 1) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(urls);
                            intent.setData(content_url);
                            startActivity(intent);
                        } else if (activityEntity.getData().getUrl_redirect() == 0) {
                            Intent intent = new Intent(MainActivity.this, HtmlActivity.class);
                            intent.putExtra(IntentConstant.HTML_URL, urls);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void updategetActJobList(ActJobListEntity actJobListEntity) {

    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        boolean firstPrivacy = PreferenceUUID.getInstence().getPrivacyState();
//        if (isUIFirstShow && !firstPrivacy) {
//            initDialog();
//            PrivacyPolicyDialog policyDialog=new PrivacyPolicyDialog(MainActivity.this, new PrivacyPolicyDialog.DialogRefuseListener() {
//                @Override
//                public void onClickRefuse() {
//                    finishApp();
//                }
//            });
//            policyDialog.setCanceledOnTouchOutside(false);
//            policyDialog.setCancelable(false);
//            policyDialog.show();
//            isUIFirstShow = false;
//        }
//    }

    /**
     * 记录用户首次点击返回键的时间
     */
//    private long firstTime = 0;
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if (System.currentTimeMillis() - firstTime > 2000) {
//                showToast("再按一次退出应用");
//                firstTime = System.currentTimeMillis();
//            } else {
//                finish();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            initDialogExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initDialogExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_exit_tip, null, false);
        TextView exit = inflate.findViewById(R.id.tv_exit);
        TextView goon = inflate.findViewById(R.id.tv_goon);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog.setView(inflate);
        //显示
        alertDialog.show();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        goon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("主页面");
        MobclickAgent.onResume(this);
        isShowType = 1;
        if (GreenDaoManager.getInstance().getDaoSession().getMessageResponseEntityDao() != null) {
            MessageResponseEntityDao messageResponseEntityDao = GreenDaoManager.getInstance().getDaoSession().getMessageResponseEntityDao();
            if (messageResponseEntityDao != null) {
                List<MessageResponseEntity> list1 = messageResponseEntityDao.queryBuilder().list();
                if (list1.size() > 0) {
                    for (int i = 0; i < list1.size(); i++) {
                        if (list1.get(i).getIsRed() == 2) {
                            isShowType = 2;
                        }
                    }
                }
            }
            if (isShowType == 1) {
                mViewIvRed.setVisibility(View.GONE);
            } else if (isShowType == 2) {
                mViewIvRed.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("主页面");
        MobclickAgent.onPause(this);
    }
}
