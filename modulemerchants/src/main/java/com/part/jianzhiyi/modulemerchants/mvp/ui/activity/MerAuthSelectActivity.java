package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.part.jianzhiyi.modulemerchants.R;
import com.umeng.analytics.MobclickAgent;

public class MerAuthSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mer_auth_select);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("选择认证方式页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("选择认证方式页面");
        MobclickAgent.onPause(this);
    }
}
