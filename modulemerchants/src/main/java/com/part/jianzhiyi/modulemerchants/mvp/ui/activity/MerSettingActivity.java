package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.ActivityUtils;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.umeng.analytics.MobclickAgent;

public class MerSettingActivity extends BaseActivity {

    private RelativeLayout mRlClear;
    private RelativeLayout mRlAbout;
    private TextView mTvLogout;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_setting;
    }

    @Override
    protected void initView() {
        mRlClear = (RelativeLayout) findViewById(R.id.rl_clear);
        mRlAbout = (RelativeLayout) findViewById(R.id.rl_about);
        mTvLogout = (TextView) findViewById(R.id.tv_logout);
        initToolbar("设置");
    }

    private long clickTime = 0;
    private long clickTime1 = 0;

    @Override
    protected void initData() {
        mRlClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("清理缓存成功");
            }
        });
        mRlAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    ARouter.getInstance().build("/app/activity/about").navigation();
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mTvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime1 > 3000) {
                    clickTime1 = System.currentTimeMillis();
                    //退出登录
                    PreferenceUUID.getInstence().loginOut();
                    ActivityUtils.removeAllActivity();
                    ARouter.getInstance().build("/app/activity/login").withInt("ToLogin", 1).navigation();
                    finish();
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void startIntent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端设置页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端设置页面");
        MobclickAgent.onPause(this);
    }
}
