package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.ActivityUtils;
import com.part.jianzhiyi.corecommon.utils.AppUtil;
import com.part.jianzhiyi.corecommon.utils.HProgressDialogUtils;
import com.part.jianzhiyi.corecommon.utils.UpdateAppHttpUtil;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.dialog.DialogVersionUpdate;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MCheckContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MCheckPresenter;
import com.umeng.analytics.MobclickAgent;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.service.DownloadService;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

public class MerSettingActivity extends BaseActivity<MCheckPresenter> implements MCheckContract.IMCheckView {

    private RelativeLayout mRlClear;
    private RelativeLayout mRlAbout;
    private TextView mTvLogout;
    private TextView mTvVersion;
    private RelativeLayout mRlVersion;
    private RelativeLayout mRlModify;
    private RelativeLayout mRlReset;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_setting;
    }

    @Override
    protected void initView() {
        mRlClear = (RelativeLayout) findViewById(R.id.rl_clear);
        mRlAbout = (RelativeLayout) findViewById(R.id.rl_about);
        mTvLogout = (TextView) findViewById(R.id.tv_logout);
        mTvVersion = (TextView) findViewById(R.id.tv_version);
        mRlVersion = (RelativeLayout) findViewById(R.id.rl_version);
        mRlModify = (RelativeLayout) findViewById(R.id.rl_modify);
        mRlReset = (RelativeLayout) findViewById(R.id.rl_reset);
        initToolbar("设置");
    }

    private long clickTime = 0;
    private long clickTime1 = 0;

    @Override
    protected void initData() {
        String versionName = AppUtil.getVersionName(MerSettingActivity.this);
        mTvVersion.setText("当前版本" + versionName);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mRlClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("清理缓存成功");
            }
        });
        mRlAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    ARouter.getInstance().build("/app/activity/about").navigation();
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mTvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime1 > 3000) {
                    clickTime1 = System.currentTimeMillis();
                    //退出登录
                    PreferenceUUID.getInstence().loginOut();
                    ActivityUtils.removeAllActivity();
                    ARouter.getInstance().build("/app/activity/login").withInt("ToLogin", 1).navigation();
                    finish();
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mRlVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCheck();
            }
        });
        mRlModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerSettingActivity.this, MerModifyPasswordActivity.class);
                startActivity(intent);
            }
        });
        mRlReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerSettingActivity.this, MerResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected MCheckPresenter createPresenter() {
        return new MCheckPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端设置页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端设置页面");
        MobclickAgent.onPause(this);
    }

    @Override
    public void updategetCheck(MCheckVersionEntity mCheckVersionEntity) {
        if (mCheckVersionEntity != null && mCheckVersionEntity.getData() != null) {
            int versionCode = AppUtil.getVersionCode(MerSettingActivity.this);
            int androidVersion = mCheckVersionEntity.getData().getAndroid_version();
            String androidTitle = mCheckVersionEntity.getData().getTitle();
            List<String> content = mCheckVersionEntity.getData().getContent();
            int is_forced = mCheckVersionEntity.getData().getIs_forced();
            String app_url = mCheckVersionEntity.getData().getApp_url();
            if (androidVersion > versionCode) {
                if (app_url != null && app_url != "") {
                    DialogVersionUpdate versionUpdate = new DialogVersionUpdate(MerSettingActivity.this, androidTitle, content, is_forced, new DialogVersionUpdate.OnJoinedClickListener() {
                        @Override
                        public void onJoinedClick() {
                            initPermission(app_url);
                        }
                    });
                    versionUpdate.show();
                    versionUpdate.setCanceledOnTouchOutside(false);
                    versionUpdate.setCancelable(false);
                }
            } else {
                showToast("当前已是最新版本");
            }
        } else {
            showToast("当前已是最新版本");
        }
    }

    private void initPermission(String app_url) {
        //需要存储权限，做6.0权限适配
        if (Build.VERSION.SDK_INT < 23) {
            initDown(app_url);
        } else {
            //判断是否有权限
            if (ContextCompat.checkSelfPermission(MerSettingActivity.this, Manifest.permission_group.STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //在这里申请
                AndPermission.with(MerSettingActivity.this)
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
                                initDown(app_url);
                            }
                        })
                        .start();
            } else {
                initDown(app_url);
            }
        }
    }

    private String path;
    private File mFile;
    private String TAG = "MerSettingActivity";

    private void initDown(String app_url) {
        UpdateAppBean updateAppBean = new UpdateAppBean();
        //设置 apk 的下载地址
        updateAppBean.setApkFileUrl(app_url);
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
        File dir = new File(path, "51jianzhi.apk");
        if (!dir.exists()) {
            dir.mkdir();
        }
        //设置apk 的保存路径
        updateAppBean.setTargetPath(path);
        //实现网络接口，只实现下载就可以
        updateAppBean.setHttpManager(new UpdateAppHttpUtil());
        UpdateAppManager.download(MerSettingActivity.this, updateAppBean, new DownloadService.DownloadCallback() {
            @Override
            public void onStart() {
                HProgressDialogUtils.showHorizontalProgressDialog(MerSettingActivity.this, "下载进度", false);
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
        if (!file.exists()) {
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
                    Uri contentUri = FileProvider.getUriForFile(MerSettingActivity.this, AppUtil.getAppProcessName(MerSettingActivity.this) + ".fileProvider", file);
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
        Uri packageUri = Uri.parse("package:" + AppUtil.getAppProcessName(MerSettingActivity.this));
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
    }
}
