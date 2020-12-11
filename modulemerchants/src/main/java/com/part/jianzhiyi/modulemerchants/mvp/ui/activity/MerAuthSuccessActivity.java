package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.umeng.analytics.MobclickAgent;

public class MerAuthSuccessActivity extends BaseActivity {

    private ImageView mIvReturn;
    private TextView mTvNext;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_auth_success;
    }

    @Override
    protected void initView() {
        mIvReturn = (ImageView) findViewById(R.id.iv_return);
        mTvNext = (TextView) findViewById(R.id.tv_next);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.rl_title));
    }

    private long clickTime = 0;
    private long clickTime1 = 0;
    @Override
    protected void initData() {
        mIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    //跳转到商户主页，我的
                    Intent intent = new Intent(MerAuthSuccessActivity.this, MerMainActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                    MerAuthSuccessActivity.this.finish();
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime1 > 3000) {
                    clickTime1 = System.currentTimeMillis();
                    //跳转到商户主页，我的
                    Intent intent = new Intent(MerAuthSuccessActivity.this, MerMainActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                    MerAuthSuccessActivity.this.finish();
                }else {
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //跳转到商户主页，我的
            Intent intent = new Intent(MerAuthSuccessActivity.this, MerMainActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
            MerAuthSuccessActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("个人认证提交成功页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("个人认证提交成功页面");
        MobclickAgent.onPause(this);
    }
}
