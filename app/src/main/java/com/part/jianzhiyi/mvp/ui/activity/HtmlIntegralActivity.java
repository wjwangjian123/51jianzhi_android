package com.part.jianzhiyi.mvp.ui.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.base.BasePresenter;

public class HtmlIntegralActivity extends BaseActivity {

    private AgentWeb mWebView;
    private LinearLayout mLlWebview;
    private String url;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_html_integral;
    }

    @Override
    protected void initView() {
        mLlWebview = (LinearLayout) findViewById(R.id.ll_webview);
        url = getIntent().getStringExtra("url");
        initToolbar("积分商城");
    }

    @Override
    protected void initData() {
        //设置网页加载
        mWebView = AgentWeb.with(HtmlIntegralActivity.this)
                .setAgentWebParent(mLlWebview, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void startIntent() {

    }
}
