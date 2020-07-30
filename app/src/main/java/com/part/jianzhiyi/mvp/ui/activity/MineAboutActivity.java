package com.part.jianzhiyi.mvp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.AppUtil;
import com.umeng.analytics.MobclickAgent;

public class MineAboutActivity extends BaseActivity {

    private TextView mMineAboutVersion;
    private int i=0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mine_about;
    }

    @Override
    protected void initView() {
        initToolbar("关于我们");
        mMineAboutVersion = findViewById(R.id.mine_about_version);
        String versionName = AppUtil.getVersionName(MineAboutActivity.this);
        mMineAboutVersion.setText("version " + versionName);
        mMineAboutVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i==5){
                    showToast("appid:"+ Constants.APPID+","+Constants.UMENG_NAME);
                    i=0;
                }
            }
        });

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
}
