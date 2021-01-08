package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.part.jianzhiyi.corecommon.ui.InputFilteEditText;
import com.part.jianzhiyi.corecommon.utils.RegularUtils;
import com.part.jianzhiyi.corecommon.utils.SoftKeyboardUtils;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MSetContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MSetPresenter;

public class MerModifyPasswordActivity extends BaseActivity<MSetPresenter> implements MSetContract.ISetView {

    private InputFilteEditText mEtOldPassword;
    private InputFilteEditText mEtNewPassword;
    private InputFilteEditText mEtConfirmPassword;
    private TextView mTvError;
    private TextView mTvComplete;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_modify_password;
    }

    @Override
    protected void initView() {
        mEtOldPassword = (InputFilteEditText) findViewById(R.id.et_old_password);
        mEtNewPassword = (InputFilteEditText) findViewById(R.id.et_new_password);
        mEtConfirmPassword = (InputFilteEditText) findViewById(R.id.et_confirm_password);
        mTvError = (TextView) findViewById(R.id.tv_error);
        mTvComplete = (TextView) findViewById(R.id.tv_complete);
        initToolbar("修改密码");
    }

    @Override
    protected void initData() {

    }

    private long clickTime = 0;

    @Override
    protected void setListener() {
        super.setListener();
        mTvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SoftKeyboardUtils.isSoftShowing(MerModifyPasswordActivity.this)) {
                    SoftKeyboardUtils.hideSoftKeyboard(MerModifyPasswordActivity.this);
                }
                mTvError.setVisibility(View.GONE);
                // validate
                String oldPassword = mEtOldPassword.getText().toString().trim();
                if (TextUtils.isEmpty(oldPassword)) {
                    showToast("请输入原密码");
                    return;
                }
                String newPassword = mEtNewPassword.getText().toString().trim();
                if (TextUtils.isEmpty(newPassword)) {
                    showToast("请输入新密码");
                    return;
                }
                if (!RegularUtils.isPassword(newPassword)) {
                    showToast("密码位数必须大于6位小于20位，切不可包含特殊符号");
                    return;
                }
                String password = mEtConfirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    showToast("请再次输入密码");
                    return;
                }
                if (!password.equals(newPassword)) {
                    showToast("您两次输入密码不一致");
                    return;
                }
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    //设置密码
                    mPresenter.getPassword("2", mEtNewPassword.getText().toString().trim(), mEtConfirmPassword.getText().toString().trim(), mEtOldPassword.getText().toString().trim(), "", "");
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

    }
}
