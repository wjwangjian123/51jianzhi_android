package com.part.jianzhiyi.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.AppUtil;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.mvp.contract.AboutContract;
import com.part.jianzhiyi.mvp.presenter.AboutPresenter;
import com.umeng.analytics.MobclickAgent;

@Route(path = "/app/activity/about")
public class MineAboutActivity extends BaseActivity<AboutPresenter> implements AboutContract.IAboutView {

    private TextView mMineAboutVersion;
    private WebView mWebView;
    private int i = 0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getConfig();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mine_about;
    }

    @Override
    protected void initView() {
        initToolbar("关于我们");
        mMineAboutVersion = findViewById(R.id.mine_about_version);
        mWebView = findViewById(R.id.webView);
        String versionName = AppUtil.getVersionName(MineAboutActivity.this);
        mMineAboutVersion.setText("version " + versionName);
        mMineAboutVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i == 5) {
                    showToast("appid:" + Constants.APPID + "," + Constants.UMENG_NAME);
                    i = 0;
                }
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected AboutPresenter createPresenter() {
        return new AboutPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("关于我们");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("关于我们");
        MobclickAgent.onPause(this);
    }

    @Override
    public void updategetConfig(ConfigEntity configEntity) {
        if (configEntity != null && configEntity.getData() != null && configEntity.getData().getAbout_us() != null) {
            mWebView.loadData(configEntity.getData().getAbout_us(), "text/html; charset=UTF-8", null);
        }
    }
}
