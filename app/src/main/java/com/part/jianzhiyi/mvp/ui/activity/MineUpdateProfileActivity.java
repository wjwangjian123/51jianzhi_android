package com.part.jianzhiyi.mvp.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.corecommon.constants.IntentConstant;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.mvp.contract.mine.MineUpdateProfileContract;
import com.part.jianzhiyi.mvp.presenter.mine.MineUpdateProfilePresenter;
import com.umeng.analytics.MobclickAgent;

public class MineUpdateProfileActivity extends BaseActivity<MineUpdateProfilePresenter> implements MineUpdateProfileContract.IMineUpdateProfileView {

    private EditText mEtNickname;
    private EditText mEtSignature;
    private EditText mEtPhone;
    private EditText mEtEmail;
    private TextView mTvSave;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mine_update_profile;
    }

    @Override
    protected void initView() {
        mEtNickname = (EditText) findViewById(R.id.et_nickname);
        mEtSignature = (EditText) findViewById(R.id.et_signature);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtEmail = (EditText) findViewById(R.id.et_email);
        mTvSave = (TextView) findViewById(R.id.tv_save);
        initToolbar("我的信息");
    }

    @Override
    protected void initData() {
        mPresenter.loadUserInfo();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    @Override
    protected MineUpdateProfilePresenter createPresenter() {
        return new MineUpdateProfilePresenter(this);
    }

    private void submit() {
        // validate
        String nickname = mEtNickname.getText().toString().trim();
        if (TextUtils.isEmpty(nickname)) {
            Toast.makeText(this, "请填写或修改您的昵称", Toast.LENGTH_SHORT).show();
            return;
        }

        String signature = mEtSignature.getText().toString().trim();
        if (TextUtils.isEmpty(signature)) {
            Toast.makeText(this, "请填写或修改您的个性签名", Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "phone不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = mEtEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "email不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.updateProfile(mEtNickname.getText().toString().trim(), mEtSignature.getText().toString().trim(), mEtPhone.getText().toString().trim(), mEtEmail.getText().toString().trim());
    }

    @Override
    public void updateSuccess() {
        showToast("保存成功");
        setResult(IntentConstant.RESULT_CODE);
        finish();
    }

    @Override
    public void updateUserInfo(LoginResponseEntity entity) {
        mEtNickname.setText(entity.getUsername());
        mEtSignature.setText(entity.getSignature());
        mEtPhone.setText(entity.getPhone());
        mEtEmail.setText(entity.getEmail());
    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }

    @Override
    public void startIntent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("我的信息");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("我的信息");
        MobclickAgent.onPause(this);
    }
}
