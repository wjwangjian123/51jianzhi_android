package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.ui.InputFilteEditText;
import com.part.jianzhiyi.corecommon.utils.ActivityUtils;
import com.part.jianzhiyi.corecommon.utils.RegularUtils;
import com.part.jianzhiyi.corecommon.utils.SoftKeyboardUtils;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MSetContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MSetPresenter;

public class MerSetPasswordActivity extends BaseActivity<MSetPresenter> implements MSetContract.ISetView {

    private InputFilteEditText mEtPassword;
    private InputFilteEditText mEtPasswordAgain;
    private InputFilteEditText mEtName;
    private TextView mTvError;
    private TextView mTvComplete;
    private int is_sing = 0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_set_password;
    }

    @Override
    protected void initView() {
        mEtPassword = (InputFilteEditText) findViewById(R.id.et_password);
        mEtPasswordAgain = (InputFilteEditText) findViewById(R.id.et_password_again);
        mEtName = (InputFilteEditText) findViewById(R.id.et_name);
        mTvError = (TextView) findViewById(R.id.tv_error);
        mTvComplete = (TextView) findViewById(R.id.tv_complete);
        initToolbar("设置密码");
    }

    @Override
    protected void initData() {
        is_sing = getIntent().getIntExtra("is_sing", 0);
    }

    private long clickTime = 0;

    @Override
    protected void setListener() {
        super.setListener();
        mTvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SoftKeyboardUtils.isSoftShowing(MerSetPasswordActivity.this)) {
                    SoftKeyboardUtils.hideSoftKeyboard(MerSetPasswordActivity.this);
                }
                mTvError.setVisibility(View.GONE);
                // validate
                String name = mEtName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    showToast("请输入用户名");
                    return;
                }
                String password = mEtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    showToast("请输入新密码");
                    return;
                }
                if (!RegularUtils.isPassword(password)) {
                    showToast("密码位数必须大于6位小于20位，切不可包含特殊符号");
                    return;
                }
                String again = mEtPasswordAgain.getText().toString().trim();
                if (TextUtils.isEmpty(again)) {
                    showToast("请再次输入密码");
                    return;
                }
                if (!again.equals(password)) {
                    showToast("您两次输入密码不一致");
                    return;
                }
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    //设置密码
                    mPresenter.getPassword("1", mEtPassword.getText().toString().trim(), mEtPasswordAgain.getText().toString().trim(), "", "", mEtName.getText().toString().trim());
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
                PreferenceUUID.getInstence().putStatus(1);
                mTvError.setVisibility(View.GONE);
                showToast(responseData.getMsg());
                //切换成功,跳转到商户端引导页
                ActivityUtils.removeAllActivity();
                if (!PreferenceUUID.getInstence().getisMerGuide()) {
                    //第一次进入引导页
                    Intent intent = new Intent(MerSetPasswordActivity.this, MerGuideActivity.class);
                    intent.putExtra("is_sing", is_sing);
                    startActivity(intent);
                } else {
                    //判断是否需要进入协议
                    if (is_sing == 0) {
                        //未签署进入协议
                        Intent intent = new Intent(MerSetPasswordActivity.this, MerAuthHtmlActivity.class);
                        startActivity(intent);
                    } else if (is_sing == 1) {
                        Intent intent = new Intent(MerSetPasswordActivity.this, MerMainActivity.class);
                        intent.putExtra("type", 0);
                        startActivity(intent);
                    }
                }
                //销毁当前activity
                MerSetPasswordActivity.this.finish();
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
