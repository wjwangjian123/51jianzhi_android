package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.base.BasePresenter;

@Route(path = "/app/activity/splash")
public class SplashActivity extends BaseActivity {

    private ImageView mSplashHolder;
    private FrameLayout mSplashContainer;
    private Handler handler = new Handler();

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mSplashHolder = (ImageView) findViewById(R.id.splash_holder);
        mSplashContainer = (FrameLayout) findViewById(R.id.splash_container);
        setToolBarVisible(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                enterHome();
            }
        }, 3000);
    }

    private void enterHome() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        mSplashContainer.removeAllViews();
        SplashActivity.this.finish();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void startIntent() {

    }
}
