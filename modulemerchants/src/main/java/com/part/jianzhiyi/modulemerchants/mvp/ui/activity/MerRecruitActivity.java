package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MMineContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MMinePresenter;
import com.umeng.analytics.MobclickAgent;

/**
 * 招聘推广
 */
public class MerRecruitActivity extends BaseActivity<MMinePresenter> implements MMineContract.IMMineView {

    private TextView mTvPublishSurplus;
    private RelativeLayout mRlPublish;
    private RelativeLayout mRlRefresh;
    private RelativeLayout mRlEnjoy;
    private RelativeLayout mRlRank;
    private RelativeLayout mRlIntellect;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端招聘推广页面");
        MobclickAgent.onResume(this);
        mPresenter.getMerUserinfo();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_recruit;
    }

    @Override
    protected void initView() {
        mTvPublishSurplus = (TextView) findViewById(R.id.tv_publish_surplus);
        mRlPublish = (RelativeLayout) findViewById(R.id.rl_publish);
        mRlRefresh = (RelativeLayout) findViewById(R.id.rl_refresh);
        mRlEnjoy = (RelativeLayout) findViewById(R.id.rl_enjoy);
        mRlRank = (RelativeLayout) findViewById(R.id.rl_rank);
        mRlIntellect = (RelativeLayout) findViewById(R.id.rl_intellect);
        initToolbar("招聘推广");
    }

    private long clickTime = 0;
    private long clickTime1 = 0;
    private long clickTime2 = 0;
    private long clickTime3 = 0;
    private long clickTime4 = 0;

    @Override
    protected void initData() {
        mRlPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    //跳转到发布
                    Intent intent = new Intent(MerRecruitActivity.this, MerRecruitTypeActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("content", mTvPublishSurplus.getText().toString());
                    startActivity(intent);
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mRlRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime1 > 3000) {
                    clickTime1 = System.currentTimeMillis();
                    //跳转到刷新
                    Intent intent = new Intent(MerRecruitActivity.this, MerRecruitTypeActivity.class);
                    intent.putExtra("type", 2);
                    intent.putExtra("content", "");
                    startActivity(intent);
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mRlEnjoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime2 > 3000) {
                    clickTime2 = System.currentTimeMillis();
                    //跳转到尊享
                    Intent intent = new Intent(MerRecruitActivity.this, MerRecruitTypeActivity.class);
                    intent.putExtra("type", 3);
                    intent.putExtra("content", "");
                    startActivity(intent);
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mRlRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime3 > 3000) {
                    clickTime3 = System.currentTimeMillis();
                    //跳转到排名
                    Intent intent = new Intent(MerRecruitActivity.this, MerRecruitTypeActivity.class);
                    intent.putExtra("type", 4);
                    intent.putExtra("content", "");
                    startActivity(intent);
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mRlIntellect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime4 > 3000) {
                    clickTime4 = System.currentTimeMillis();
                    //跳转到智能优聘
                    Intent intent = new Intent(MerRecruitActivity.this, MerRecruitTypeActivity.class);
                    intent.putExtra("type", 5);
                    intent.putExtra("content", "");
                    startActivity(intent);
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    @Override
    protected MMinePresenter createPresenter() {
        return new MMinePresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updategetMerUserinfo(MUserInfoEntity mUserInfoEntity) {
        if (mUserInfoEntity != null) {
            mTvPublishSurplus.setText(mUserInfoEntity.getUserinfo().getJob_msg());
        }
    }

    @Override
    public void updategetAvatar(ResponseData responseData) {

    }

    @Override
    public void updategetOpinion(ResponseData responseData) {

    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端招聘推广页面");
        MobclickAgent.onPause(this);
    }
}
