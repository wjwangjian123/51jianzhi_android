package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.ui.InputFilteEditText;
import com.part.jianzhiyi.corecommon.utils.RegularUtils;
import com.part.jianzhiyi.corecommon.utils.SoftKeyboardUtils;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MSetContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MSetPresenter;

public class MerResetPasswordActivity extends BaseActivity<MSetPresenter> implements MSetContract.ISetView {

    private TextView mTvPhone;
    private InputFilteEditText mEtCode;
    private TextView mTvCode;
    private InputFilteEditText mEtPassword;
    private InputFilteEditText mEtConfirmPassword;
    private TextView mTvError;
    private TextView mTvComplete;
    private CountDownTimer timer;
    private long sendSmsCodeTime = 60000;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_reset_password;
    }

    @Override
    protected void initView() {
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mEtCode = (InputFilteEditText) findViewById(R.id.et_code);
        mTvCode = (TextView) findViewById(R.id.tv_code);
        mEtPassword = (InputFilteEditText) findViewById(R.id.et_password);
        mEtConfirmPassword = (InputFilteEditText) findViewById(R.id.et_confirm_password);
        mTvError = (TextView) findViewById(R.id.tv_error);
        mTvComplete = (TextView) findViewById(R.id.tv_complete);
        initToolbar("重置密码");
    }

    @Override
    protected void initData() {
        mTvPhone.setText(PreferenceUUID.getInstence().getUserPhone());
    }

    private long clickTime = 0;
    private long clickTime1 = 0;

    @Override
    protected void setListener() {
        super.setListener();
        mTvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SoftKeyboardUtils.isSoftShowing(MerResetPasswordActivity.this)) {
                    SoftKeyboardUtils.hideSoftKeyboard(MerResetPasswordActivity.this);
                }
                mTvError.setVisibility(View.GONE);
                if (TextUtils.isEmpty(mTvPhone.getText().toString().trim())) {
                    showToast("手机号为空");
                    return;
                }
                // validate
                String code = mEtCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    showToast("请输入验证码");
                    return;
                }
                String password = mEtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    showToast("请输入密码");
                    return;
                }
                if (!RegularUtils.isPassword(password)) {
                    showToast("密码位数必须大于6位小于20位，切不可包含特殊符号");
                    return;
                }
                String confirmPassword = mEtConfirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(confirmPassword)) {
                    showToast("请再次输入密码");
                    return;
                }
                if (!confirmPassword.equals(password)) {
                    showToast("您两次输入密码不一致");
                    return;
                }
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    //设置密码
                    mPresenter.getPassword("3", mEtPassword.getText().toString().trim(), mEtConfirmPassword.getText().toString().trim(), "", mEtCode.getText().toString().trim());
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mTvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    return;
                }
                if (System.currentTimeMillis() - clickTime1 > 3000) {
                    clickTime1 = System.currentTimeMillis();
                    mPresenter.getCode();
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    @Override
    protected MSetPresenter createPresenter() {
        return new MSetPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    private void startTimer() {
        timer = new CountDownTimer(sendSmsCodeTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvCode.setText((millisUntilFinished / 1000) + " 秒后重试");
                mTvCode.setEnabled(false);
            }

            @Override
            public void onFinish() {
                if (timer != null) {
                    mTvCode.setText("获取验证码");
                    mTvCode.setEnabled(true);
                    timer.cancel();
                    timer = null;
                }
            }
        };
        timer.start();
    }

    @Override
    public void updategetPassword(ResponseData responseData) {
        if (responseData != null) {
            if (responseData.getCode().equals("200")) {
                mTvError.setVisibility(View.GONE);
                showToast(responseData.getMsg());
                finish();
            } else {
                mTvError.setVisibility(View.VISIBLE);
                mTvError.setText(responseData.getMsg());
            }
        }
    }

    @Override
    public void updategetCode(ResponseData responseData) {
        if (responseData != null) {
            showToast(responseData.getMsg());
            if (responseData.getCode().equals("200")) {
                startTimer();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
