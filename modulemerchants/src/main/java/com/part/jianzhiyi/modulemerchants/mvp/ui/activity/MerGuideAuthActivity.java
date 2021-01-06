package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.umeng.analytics.MobclickAgent;

public class MerGuideAuthActivity extends BaseActivity {

    private TextView mTvNext;
    private int guide_type = 0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_guide_auth;
    }

    @Override
    protected void initView() {
        mTvNext = (TextView) findViewById(R.id.tv_next);
        setToolBarVisible(false);
    }

    private long clickTime = 0;

    @Override
    protected void initData() {
        guide_type = getIntent().getIntExtra("guide_type", 0);
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    if (guide_type == 0 || guide_type == 2) {
                        Intent intent = new Intent(MerGuideAuthActivity.this, MerAuthActivity.class);
                        intent.putExtra("urlType", 0);
                        startActivity(intent);
                    } else if (guide_type == 1) {
                        //前去企业认证
                        Intent intent = new Intent(MerGuideAuthActivity.this, MerUploadInfoActivity.class);
                        intent.putExtra("urlType", 0);
                        startActivity(intent);
                    }
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
                //销毁当前activity
                MerGuideAuthActivity.this.finish();
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
            if (guide_type == 0) {
                //跳转到商户主页，我的
                Intent intent = new Intent(MerGuideAuthActivity.this, MerMainActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                finish();
            } else if (guide_type == 1 || guide_type == 2) {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端认证教程引导页");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端认证教程引导页");
        MobclickAgent.onPause(this);
    }

}
