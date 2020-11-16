package com.part.jianzhiyi.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.mvp.contract.mine.MineFeekbackContract;
import com.part.jianzhiyi.mvp.presenter.mine.MineFeekbackPresenter;
import com.umeng.analytics.MobclickAgent;

public class MineFeekbackActivity extends BaseActivity<MineFeekbackPresenter> implements MineFeekbackContract.IMineFeekbackView {

    private EditText mEtSuggest;
    private EditText mEtEmail;
    private TextView mTvSend;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mine_feekback;
    }

    @Override
    protected void initView() {
        mEtSuggest = (EditText) findViewById(R.id.et_suggest);
        mEtEmail = (EditText) findViewById(R.id.et_email);
        mTvSend = (TextView) findViewById(R.id.tv_send);
        initToolbar("意见反馈");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        super.setListener();
        mTvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.jobfeedback(mEtSuggest.getText().toString(), mEtEmail.getText().toString());
            }
        });
    }

    @Override
    protected MineFeekbackPresenter createPresenter() {
        return new MineFeekbackPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updateSuccess() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("意见反馈");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("意见反馈");
        MobclickAgent.onPause(this);
    }
}
