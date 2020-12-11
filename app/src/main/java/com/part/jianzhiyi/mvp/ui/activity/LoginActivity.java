package com.part.jianzhiyi.mvp.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.constants.IntentConstant;
import com.part.jianzhiyi.corecommon.ui.SendCodeView;
import com.part.jianzhiyi.corecommon.utils.RegularUtils;
import com.part.jianzhiyi.customview.MyClickableSpan;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.UMEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.mvp.contract.user.LoginContract;
import com.part.jianzhiyi.mvp.presenter.user.LoginPresenter;
import com.umeng.analytics.MobclickAgent;
import com.umeng.umverify.UMVerifyHelper;
import com.umeng.umverify.listener.UMAuthUIControlClickListener;
import com.umeng.umverify.listener.UMTokenResultListener;
import com.umeng.umverify.model.UMTokenRet;
import com.umeng.umverify.view.UMAbstractPnsViewDelegate;
import com.umeng.umverify.view.UMAuthRegisterXmlConfig;
import com.umeng.umverify.view.UMAuthUIConfig;

@Route(path = "/app/activity/login")
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.ILoginView {

    private LinearLayout mLoginLinear;
    private TextView mLoginTvOther;
    private TextView mLoginTvAgree;
    private SendCodeView mLoginSendCode;
    private CheckBox mLoginCk;
    private int a = 1;
    private String AppSecret;
    private String SMcode;
    private int type = 0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.androidInfo(LoginActivity.this);
        mPresenter.getConfig();
        mPresenter.getaddMd("3");
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
        mLoginCk = (CheckBox) findViewById(R.id.login_ck);
        setToolBarVisible(false);
        MobclickAgent.onEvent(this, "login_in");
        if (!getIntent().getExtras().isEmpty() && getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            a = extras.getInt("ToLogin");
        }
        mLoginCk.setChecked(true);
    }

    @Override
    protected void initData() {
        String contextString = "点击登录/注册即表示同意《用户协议》\n和《隐私政策》";
        SpannableString spannableString = new SpannableString(contextString);
        MyClickableSpan user = new MyClickableSpan() {
            @Override
            public void onClick(View widget) {
                MobclickAgent.onEvent(LoginActivity.this, "login_agreement");
                Intent intent = new Intent(LoginActivity.this, HtmlActivity.class);
                intent.putExtra(IntentConstant.HTML_URL, Constants.HTML_USER_URL + Constants.APPID + "&status=" + Constants.STATUS);
                intent.putExtra("title", "");
                startActivity(intent);
            }
        };
        MyClickableSpan privacy = new MyClickableSpan() {
            @Override
            public void onClick(View widget) {
                MobclickAgent.onEvent(LoginActivity.this, "login_agreement");
                Intent intent = new Intent(LoginActivity.this, HtmlActivity.class);
                intent.putExtra(IntentConstant.HTML_URL, Constants.HTML_PRIVACY_URL + Constants.APPID + "&status=" + Constants.STATUS);
                intent.putExtra("title", "");
                startActivity(intent);
            }
        };
        spannableString.setSpan(user, 12, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(privacy, 20, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mLoginTvAgree.setMovementMethod(LinkMovementMethod.getInstance());
        mLoginTvAgree.setText(spannableString);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mLoginSendCode.setOnSendCodeClickListener(new SendCodeView.OnSendCodeClickListener() {
            @Override
            public void onSendCodeClick() {
                mPresenter.sendSmsCode(mLoginSendCode.getPhone());
                MobclickAgent.onEvent(LoginActivity.this, "login_code");
            }
        });
        mLoginLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mLoginSendCode.getPhone()) || !RegularUtils.isMobileNumber(mLoginSendCode.getPhone())) {
                    showToast("请填写正确的手机号");
                    return;
                }
                if (TextUtils.isEmpty(mLoginSendCode.getSmsCode())) {
                    showToast("请填写正确的验证码");
                    return;
                }
                if (!mLoginCk.isChecked()) {
                    showToast("请阅读并同意协议");
                    return;
                }
                type = 0;
                mPresenter.login(mLoginSendCode.getPhone(), mLoginSendCode.getSmsCode());
            }
        });
        mLoginTvOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(LoginActivity.this, "login_one_click");
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
                Log.e("xxxxxx", "onTokenSuccess:$ret" + ret);
                MobclickAgent.onEvent(LoginActivity.this, "um_show");
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
                        mPresenter.getaddMd("1");
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
                Log.e("xxxxxx", "onTokenFailed:$ret" + ret);
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
                .setLogBtnToastHidden(false)
                .setSwitchAccText("其他方式登录/注册")
                .setSwitchAccTextSize(12)
                .setSwitchAccTextColor(Color.parseColor("#333333"))
                .setSwitchOffsetY(357)
                .setCheckboxHidden(false)
                .setUncheckedImgPath("icon_login_unselect")
                .setCheckedImgPath("icon_login_selected")
                .setPrivacyState(true)
                .setProtocolLayoutGravity(Gravity.CENTER_HORIZONTAL)
                .setPrivacyOffsetY_B(45)
                .setAppPrivacyOne("《用户协议》", Constants.HTML_USER_URL + Constants.APPID + "&status=" + Constants.STATUS)
                .setAppPrivacyColor(Color.parseColor("#666666"), Color.parseColor("#FD8347"))
                .setPrivacyTextSize(12)
                .setPrivacyBefore("点击登录/注册即表示同意")
                .setVendorPrivacyPrefix("《")
                .setVendorPrivacySuffix("》")
                .setAuthPageActIn("in_right", "out_left")
                .setAuthPageActOut("in_left", "out_right")
                .setScreenOrientation(authPageOrientation)
                .create());
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updatesendSms(ResponseData<String> responseData) {
        if (responseData != null) {
            if (responseData.getCode().equals("200")) {
                mLoginSendCode.startTimer();
                showToast(responseData.getMsg());
            } else {
                showToast(responseData.getMsg());
            }
        }
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
            type = 1;
            mPresenter.login(phone, SMcode);
            MobclickAgent.onEvent(LoginActivity.this, "um_login_success");
        }
    }

    @Override
    public void updatelogin(LoginResponseEntity entity) {
        if (type == 0) {
            mPresenter.getaddMd("4");
            MobclickAgent.onEvent(LoginActivity.this, "login_success");
        } else if (type == 1) {
            mPresenter.getaddMd("2");
        }
        mPresenter.userInfo(String.valueOf(entity.getId()));
    }

    @Override
    public void updateUserInfoPer(UserInfoEntity userInfoEntity) {
        if (a == 2) {
            Intent intent = new Intent(LoginActivity.this, VocationActivity.class);
            startActivity(intent);
        } else {
            if (userInfoEntity.getData().getAge() == null || userInfoEntity.getData().getAge() == "" ||
                    userInfoEntity.getData().getSex() == null || userInfoEntity.getData().getSex() == "" ||
                    userInfoEntity.getData().getProfession() == null || userInfoEntity.getData().getProfession() == "") {
                Intent intent = new Intent(LoginActivity.this, ResumeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ToResume", 1);
                bundle.putInt("errorType", 1);
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (userInfoEntity.getData().getJob_status() == null || userInfoEntity.getData().getJob_status() == "" ||
                    userInfoEntity.getData().getJob_type() == null || userInfoEntity.getData().getJob_type() == "") {
                Intent intent = new Intent(LoginActivity.this, MyStatusActivity.class);
                startActivity(intent);
            } else if (userInfoEntity.getData().getMyitem().size() == 0) {
                Intent intent = new Intent(LoginActivity.this, AboutMineActivity.class);
                startActivity(intent);
            } else if (userInfoEntity.getData().getExpect().size() == 0) {
                Intent intent = new Intent(LoginActivity.this, ExpectPositionActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        }
        finish();
    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

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
                alertDialog.dismiss();
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
            MobclickAgent.onEvent(this, "login_back");
            initDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("登录页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("登录页面");
        MobclickAgent.onPause(this);
    }
}
