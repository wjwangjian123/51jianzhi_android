package com.part.jianzhiyi.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddSignEntity;
import com.part.jianzhiyi.model.entity.DaySignEntity;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.mvp.contract.user.MineContract;
import com.part.jianzhiyi.mvp.presenter.mine.MinePresenter;
import com.part.jianzhiyi.utils.NotificationUtil;
import com.umeng.analytics.MobclickAgent;

public class MineSettingActivity extends BaseActivity<MinePresenter> implements MineContract.IMineView {

    private TextView tvExit;
    private Switch mSetSwitch;
    private RelativeLayout mSetRlUser;
    private RelativeLayout mSetRlPrivacy;
    private TextView mTvCancellation;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mine_setting;
    }

    @Override
    protected void initView() {
        initToolbar("设置");
        tvExit = mView.findViewById(R.id.tv_exit);
        mSetSwitch = mView.findViewById(R.id.set_switch);
        mSetRlUser = mView.findViewById(R.id.set_rl_user);
        mSetRlPrivacy = mView.findViewById(R.id.set_rl_privacy);
        mTvCancellation = mView.findViewById(R.id.tv_cancellation);
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
}
