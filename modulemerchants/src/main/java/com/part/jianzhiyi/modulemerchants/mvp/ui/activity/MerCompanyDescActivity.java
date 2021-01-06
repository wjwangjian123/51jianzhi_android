package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MCompanyInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MCompanyContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MCompanyPresenter;

public class MerCompanyDescActivity extends BaseActivity<MCompanyPresenter> implements MCompanyContract.IMCompanyView {

    private EditText mEtIntro;
    private TextView mTvNext;
    private TextView mTvIntro;
    private int type = 0;
    private String company_desc;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_company_desc;
    }

    @Override
    protected void initView() {
        mEtIntro = (EditText) findViewById(R.id.et_intro);
        mTvNext = (TextView) findViewById(R.id.tv_next);
        mTvIntro = (TextView) findViewById(R.id.tv_intro);
        initToolbar("编辑简介");
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 0);
        company_desc = getIntent().getStringExtra("company_desc");
        if (type == 0) {
            mTvIntro.setText("个人简介");
            mEtIntro.setHint("请输入个人简介");
        }
        if (type == 1) {
            mTvIntro.setText("公司简介");
            mEtIntro.setHint("请输入公司简介");
        }
        mEtIntro.setText(company_desc);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String intro = mEtIntro.getText().toString().trim();
                if (TextUtils.isEmpty(intro)) {
                    showToast("请输入公司简介");
                    return;
                }
                mPresenter.getIntroduced(mEtIntro.getText().toString().trim());
            }
        });
    }

    @Override
    protected MCompanyPresenter createPresenter() {
        return new MCompanyPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updategetCompanyInfo(MCompanyInfoEntity mCompanyInfoEntity) {

    }

    @Override
    public void updategetIntroduced(ResponseData responseData) {
        if (responseData != null) {
            showToast(responseData.getMsg());
            if (responseData.getCode().equals("200")) {
                Intent intent = new Intent();
                intent.putExtra("desc", mEtIntro.getText().toString().trim());
                setResult(100, intent);
                finish();
            }
        }
    }
}
