package com.part.jianzhiyi.mvp.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.constants.IntentConstant;
import com.part.jianzhiyi.corecommon.ui.SendCodeView;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.model.entity.UMEntity;
import com.part.jianzhiyi.mvp.contract.user.LoginContract;
import com.part.jianzhiyi.mvp.presenter.user.LoginPresenter;
import com.part.jianzhiyi.utils.AppUtils;
import com.umeng.umverify.UMVerifyHelper;
import com.umeng.umverify.listener.UMAuthUIControlClickListener;
import com.umeng.umverify.listener.UMCustomInterface;
import com.umeng.umverify.listener.UMTokenResultListener;
import com.umeng.umverify.model.UMTokenRet;
import com.umeng.umverify.view.UMAbstractPnsViewDelegate;
import com.umeng.umverify.view.UMAuthRegisterViewConfig;
import com.umeng.umverify.view.UMAuthRegisterXmlConfig;
import com.umeng.umverify.view.UMAuthUIConfig;

@Route(path = "/app/activity/login")
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.ILoginView {

    private LinearLayout mLoginLinear;
    private TextView mLoginTvOther;
    private TextView mLoginTvAgree;
    private SendCodeView mLoginSendCode;
    private int a = 1;
    private String AppSecret;
    private String SMcode;


    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.androidInfo(LoginActivity.this);
        mPresenter.getConfig();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {
        mLoginLinear = (LinearLayout) findViewById(R.id.login_linear);
        mLoginTvOther = (TextView) findViewById(R.id.login_tv_other);
        mLoginTvAgree = (TextView) findViewById(R.id.login_tv_agree);
        mLoginSendCode = (SendCodeView) findViewById(R.id.login_send_code);
        setToolBarVisible(false);
        if (!getIntent().getExtras().isEmpty() && getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            a = extras.getInt("ToLogin");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        super.setListener();
        mLoginSendCode.setOnSendCodeClickListener(new SendCodeView.OnSendCodeClickListener() {
            @Override
            public void onSendCodeClick() {
                boolean smsCode = mPresenter.sendSmsCode(mLoginSendCode.getPhone());
                if (smsCode) {
                    mLoginSendCode.startTimer();
                }
            }
        });
        mLoginTvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HtmlActivity.class);
                intent.putExtra(IntentConstant.HTML_URL, Constants.HTML_USER_URL + Constants.APPID + "&status=" + Constants.STATUS);
                startActivity(intent);
            }
        });
        mLoginLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login(mLoginSendCode.getPhone(), mLoginSendCode.getSmsCode());
            }
        });
        mLoginTvOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUMVerify(AppSecret);
            }
        });
    }

    //友盟一键登录
    private UMVerifyHelper umVerifyHelper;
    private UMTokenResultListener mTokenListener;
    private String token;
    private boolean checkRet = false;
    private TextView switchTV;
    private Dialog mLoadingDialog;

    private void initUMVerify(String string) {
        showLoadingDialog("");
        //1.初始化获取token实例
        //友盟一键登录
        mTokenListener = new UMTokenResultListener() {
            @Override
            public void onTokenSuccess(String ret) {
                Log.e("xxxxxx", "onTokenSuccess:$ret"+ret);
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingDialog();
                        UMTokenRet tokenRet = null;
                        try {
                            tokenRet = JSON.parseObject(ret, UMTokenRet.class);
                            Log.e("xxxxxx", "tokenRet:$tokenRet");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (tokenRet != null && "600001" != tokenRet.getCode()) {
                            token = tokenRet.getToken();
                            umVerifyHelper.quitLoginPage();
                            mPresenter.verifys(token);
                            Log.e("xxxxxx", "token:$token");
                        }
                        hideLoadingDialog();
                        mPresenter.getSussOrErrLog("1", "1");
                    }
                });
            }

            @Override
            public void onTokenFailed(String ret) {
                Log.e("xxxxxx", "onTokenFailed:$ret"+ret);
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        umVerifyHelper.hideLoginLoading();
                        umVerifyHelper.quitLoginPage();
                        hideLoadingDialog();
                        mPresenter.getSussOrErrLog("1", "2");
                    }
                });

            }
        };
        // 2.初始化SDK实例
        umVerifyHelper = UMVerifyHelper.getInstance(LoginActivity.this, mTokenListener);
        // 3.设置SDK秘钥
        umVerifyHelper.setAuthSDKInfo(string);
        // 4.设置token监听
        umVerifyHelper.setAuthListener(mTokenListener);
        // 5.检测终端网络环境是否支持一键登录或者号码认证
        checkRet = umVerifyHelper.checkEnvAvailable();
        if (!checkRet) {
            Log.d("xxxxxx", "当前网络不支持，请检测蜂窝网络后重试");
        }
        umVerifyHelper.getVerifyId(LoginActivity.this);
        Log.e("xxxxxx", "VerifyId:" + umVerifyHelper.getVerifyId(LoginActivity.this));
        //自定义UI
        configLoginTokenLand();
        //6.若步骤4支持，则根据业务情况，调用预取号或者一键登录接口 详见demo接入工程
        umVerifyHelper.getLoginToken(this, 10000);
        umVerifyHelper.setUIClickListener(new UMAuthUIControlClickListener() {
            @Override
            public void onClick(String s, Context context, String s1) {
                hideLoadingDialog();
            }
        });
    }

    private void showLoadingDialog(String hint) {
        if (mLoadingDialog == null) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.loading_popup, null);// 得到加载view
            View layout = v.findViewById(R.id.dialog_view);
            // 创建自定义样式dialog
            mLoadingDialog = new Dialog(this, R.style.LoadingDialogTheme);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mLoadingDialog.setContentView(layout, layoutParams);
            mLoadingDialog.setCanceledOnTouchOutside(true);
        }
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.show();
    }

    private void hideLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    private void configLoginTokenLand() {
        umVerifyHelper.removeAuthRegisterXmlConfig();
        umVerifyHelper.removeAuthRegisterViewConfig();
        umVerifyHelper.addAuthRegisterXmlConfig(new UMAuthRegisterXmlConfig.Builder()
                .setLayout(R.layout.layout_um_bg, new UMAbstractPnsViewDelegate() {
                    @Override
                    public void onViewCreated(View view) {
                    }
                })
                .build());
        int authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
        if (Build.VERSION.SDK_INT == 26) {
            authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_BEHIND;
        }
        umVerifyHelper.setAuthUIConfig(new UMAuthUIConfig.Builder()
                .setStatusBarHidden(false)
                .setStatusBarColor(Color.WHITE)
                .setStatusBarUIFlag(View.SYSTEM_UI_FLAG_LOW_PROFILE)
                .setLightColor(true)
                .setNavHidden(false)
                .setNavColor(Color.WHITE)
                .setNavText("")
                .setLogoHidden(true)
                .setSloganTextColor(Color.parseColor("#565656"))
                .setSloganTextSize(15)
                .setSloganOffsetY(220)
                .setNumberColor(Color.parseColor("#333333"))
                .setNumberSize(19)
                .setNumFieldOffsetY(189)
                .setNumberLayoutGravity(Gravity.CENTER_HORIZONTAL)
                .setLogBtnText("本机号码一键登录")
                .setLogBtnTextColor(Color.parseColor("#ffffff"))
                .setLogBtnTextSize(15)
                .setLogBtnHeight(44)
                .setLogBtnMarginLeftAndRight(46)
                .setLogBtnBackgroundPath("shape_dialog_agree")
                .setLogBtnOffsetY(287)
                .setLogBtnLayoutGravity(Gravity.CENTER_HORIZONTAL)
                .setLogBtnToastHidden(true)
                .setSwitchAccText("其他方式登录/注册")
                .setSwitchAccTextSize(12)
                .setSwitchAccTextColor(Color.parseColor("#333333"))
                .setSwitchOffsetY(357)
                .setCheckboxHidden(true)
                .setProtocolLayoutGravity(Gravity.CENTER_HORIZONTAL)
                .setPrivacyOffsetY_B(45)
                .setAppPrivacyOne("《用户协议》", Constants.HTML_USER_URL + Constants.APPID + "&status=" + Constants.STATUS)
                .setAppPrivacyColor(Color.parseColor("#666666"), Color.parseColor("#FFC308"))
                .setPrivacyTextSize(12)
                .setPrivacyBefore("点击登录/注册即表示同意")
                .setVendorPrivacyPrefix("《")
                .setVendorPrivacySuffix("》")
                .setAuthPageActIn("in_right","out_left")
                .setAuthPageActOut("in_left","out_right")
                .setScreenOrientation(authPageOrientation)
                .create());
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updategetConfig(ConfigEntity configEntity) {
        if (configEntity.getData() != null) {
            AppSecret = configEntity.getData().getAppsecret();
            SMcode = configEntity.getData().getCode();
            //调用一键登录
            initUMVerify(configEntity.getData().getAppsecret());
        }
    }

    @Override
    public void updateverifys(UMEntity umEntity) {
        if (umEntity.getData() != null) {
            String phone = umEntity.getData().getPhone();
            mPresenter.login(phone, SMcode);
        }
    }

    @Override
    public void updatelogin(boolean showResume) {
        if (a == 2) {
            Intent intent = new Intent(LoginActivity.this, VocationActivity.class);
            startActivity(intent);
        } else {
            if (showResume) {
                Intent intent = new Intent(LoginActivity.this, ResumeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ToResume", 1);
                bundle.putInt("errorType", 1);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        }
        finish();
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_logout_tip, null, false);
        TextView exit = inflate.findViewById(R.id.logout_tv_exit);
        TextView goon = inflate.findViewById(R.id.logout_tv_goon);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            initDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
