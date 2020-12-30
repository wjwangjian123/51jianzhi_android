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
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.constants.IntentConstant;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.ActivityUtils;
import com.part.jianzhiyi.corecommon.utils.AppUtil;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.modulemerchants.dialog.DialogVersionUpdate;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddSignEntity;
import com.part.jianzhiyi.model.entity.DaySignEntity;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
import com.part.jianzhiyi.mvp.contract.user.MineContract;
import com.part.jianzhiyi.mvp.presenter.mine.MinePresenter;
import com.part.jianzhiyi.corecommon.utils.HProgressDialogUtils;
import com.part.jianzhiyi.utils.NotificationUtil;
import com.part.jianzhiyi.corecommon.utils.UpdateAppHttpUtil;
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

public class MineSettingActivity extends BaseActivity<MinePresenter> implements MineContract.IMineView {

    private TextView tvExit;
    private Switch mSetSwitch;
    private RelativeLayout mSetRlUser;
    private RelativeLayout mSetRlPrivacy;
    private TextView mTvCancellation;
    private TextView mTvVersion;
    private RelativeLayout mSetRlVersion;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mine_setting;
    }

    @Override
    protected void initView() {
        tvExit = mView.findViewById(R.id.tv_exit);
        mSetSwitch = mView.findViewById(R.id.set_switch);
        mSetRlUser = mView.findViewById(R.id.set_rl_user);
        mSetRlPrivacy = mView.findViewById(R.id.set_rl_privacy);
        mTvCancellation = mView.findViewById(R.id.tv_cancellation);
        mTvVersion = mView.findViewById(R.id.tv_version);
        mSetRlVersion = mView.findViewById(R.id.set_rl_version);
        initToolbar("设置");
    }

    @Override
    protected void initData() {
        if (!NotificationUtil.isNotifyEnabled(MineSettingActivity.this)) {
            //权限没开
            mSetSwitch.setChecked(false);
        } else {
            mSetSwitch.setChecked(true);
            MobclickAgent.onEvent(MineSettingActivity.this, "set_Notification_open");
        }
        String versionName = AppUtil.getVersionName(MineSettingActivity.this);
        mTvVersion.setText("当前版本" + versionName);
    }

    @Override
    protected void setListener() {
        super.setListener();
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(MineSettingActivity.this, "set_logout");
                PreferenceUUID.getInstence().loginOut();
                ActivityUtils.removeAllActivity();
                Intent intent = new Intent(MineSettingActivity.this, LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ToLogin", 1);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        mSetSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!NotificationUtil.isNotifyEnabled(MineSettingActivity.this)) {
                        initDialogNotification();
                    }
                }
            }
        });
        mSetRlUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineSettingActivity.this, HtmlActivity.class);
                intent.putExtra(IntentConstant.HTML_URL, Constants.HTML_USER_URL + Constants.APPID + "&status=" + Constants.STATUS);
                intent.putExtra("title", "");
                startActivity(intent);
            }
        });
        mSetRlPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineSettingActivity.this, HtmlActivity.class);
                intent.putExtra(IntentConstant.HTML_URL, Constants.HTML_PRIVACY_URL + Constants.APPID + "&status=" + Constants.STATUS);
                intent.putExtra("title", "");
                startActivity(intent);
            }
        });
        mTvCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(MineSettingActivity.this, "set_cancellation");
                initDialogCancellation();
            }
        });
        mSetRlVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCheck();
            }
        });
    }

    private void initDialogNotification() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MineSettingActivity.this);
        AlertDialog alertDialog1 = alertDialog.create();
        View view = View.inflate(MineSettingActivity.this, R.layout.dialog_notification_tip, null);
        alertDialog1.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog1.setCanceledOnTouchOutside(false);
        alertDialog1.setCancelable(false);
        alertDialog1.setView(view);
        //显示
        alertDialog1.show();
        TextView cancel = view.findViewById(R.id.re_tv_cancel);
        TextView set = view.findViewById(R.id.re_tv_set);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
                if (!NotificationUtil.isNotifyEnabled(MineSettingActivity.this)) {
                    //权限没开
                    mSetSwitch.setChecked(false);
                }
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
                ToNotification();
            }
        });
    }

    private void initDialogCancellation() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MineSettingActivity.this);
        AlertDialog alertDialog1 = alertDialog.create();
        View view = View.inflate(MineSettingActivity.this, R.layout.dialog_cancellation_tip, null);
        TextView confirm = view.findViewById(R.id.tv_confirm);
        TextView cancel = view.findViewById(R.id.tv_cancel);
        alertDialog1.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog1.setCanceledOnTouchOutside(true);
        alertDialog1.setCancelable(true);
        alertDialog1.setView(view);
        //显示
        alertDialog1.show();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
                if (!PreferenceUUID.getInstence().getUserId().equals(null) && !PreferenceUUID.getInstence().getUserId().equals("")) {
                    mPresenter.getDelUser(PreferenceUUID.getInstence().getUserId());
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });
    }

    private void ToNotification() {
        Intent localIntent = new Intent();
        //直接跳转到应用通知设置的代码：
        //8.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上到8.0以下
            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            localIntent.putExtra("app_package", getPackageName());
            localIntent.putExtra("app_uid", getApplicationInfo().uid);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            //4.4
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.addCategory(Intent.CATEGORY_DEFAULT);
            localIntent.setData(Uri.parse("package:" + getPackageName()));
        } else {
            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
            }
        }
        startActivity(localIntent);
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter(this);
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void startIntent() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!NotificationUtil.isNotifyEnabled(MineSettingActivity.this)) {
            //权限没开
            mSetSwitch.setChecked(false);
        } else {
            mSetSwitch.setChecked(true);
            MobclickAgent.onEvent(MineSettingActivity.this, "set_Notification_open");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("设置页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("设置页面");
        MobclickAgent.onPause(this);
    }

    @Override
    public void updateUserInfo(LoginResponseEntity entity) {

    }

    @Override
    public void updateUserInfoPer(UserInfoEntity userInfoEntity) {

    }

    @Override
    public void updategetDaySign(DaySignEntity daySignEntity) {

    }

    @Override
    public void updateaddDaySign(AddSignEntity responseData) {

    }

    @Override
    public void updategetDelUser(ResponseData responseData) {
        if (responseData != null) {
            showToast(responseData.getMsg());
            if (responseData.getCode().equals("200")) {
                PreferenceUUID.getInstence().loginOut();
                ActivityUtils.removeAllActivity();
                Intent intent = new Intent(MineSettingActivity.this, LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ToLogin", 1);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }

    @Override
    public void updategetAddInteg(SignInfoEntity responseData) {

    }

    @Override
    public void updategetCheck(MCheckVersionEntity mCheckVersionEntity) {
        if (mCheckVersionEntity != null && mCheckVersionEntity.getData() != null) {
            int versionCode = AppUtil.getVersionCode(MineSettingActivity.this);
            int androidVersion = mCheckVersionEntity.getData().getAndroid_version();
            String androidTitle = mCheckVersionEntity.getData().getTitle();
            List<String> content = mCheckVersionEntity.getData().getContent();
            int is_forced = mCheckVersionEntity.getData().getIs_forced();
            String app_url = mCheckVersionEntity.getData().getApp_url();
            if (androidVersion > versionCode) {
                if (app_url != null && app_url != "") {
                    DialogVersionUpdate versionUpdate = new DialogVersionUpdate(MineSettingActivity.this, androidTitle, content, is_forced, new DialogVersionUpdate.OnJoinedClickListener() {
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

    @Override
    public void updategetConfig(ConfigEntity configEntity) {

    }

    private void initPermission(String app_url) {
        //需要存储权限，做6.0权限适配
        if (Build.VERSION.SDK_INT < 23) {
            initDown(app_url);
        } else {
            //判断是否有权限
            if (ContextCompat.checkSelfPermission(MineSettingActivity.this, Manifest.permission_group.STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //在这里申请
                AndPermission.with(MineSettingActivity.this)
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
    private String TAG = "MineSettingActivity";

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
        UpdateAppManager.download(MineSettingActivity.this, updateAppBean, new DownloadService.DownloadCallback() {
            @Override
            public void onStart() {
                HProgressDialogUtils.showHorizontalProgressDialog(MineSettingActivity.this, "下载进度", false);
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
                    Uri contentUri = FileProvider.getUriForFile(MineSettingActivity.this, AppUtil.getAppProcessName(MineSettingActivity.this) + ".fileProvider", file);
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
        Uri packageUri = Uri.parse("package:" + AppUtil.getAppProcessName(MineSettingActivity.this));
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
}
