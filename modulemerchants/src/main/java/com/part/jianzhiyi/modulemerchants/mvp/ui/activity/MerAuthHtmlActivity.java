package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.TextView;

import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.constants.Constants;
import com.part.jianzhiyi.modulemerchants.customview.ProgressWebView;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableContactEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableSalaryEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MPublishContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MPublishPresenter;
import com.umeng.analytics.MobclickAgent;

public class MerAuthHtmlActivity extends BaseActivity<MPublishPresenter> implements MPublishContract.IMPublishView {

    private ProgressWebView mWebView;
    private TextView mTvDisagree;
    private TextView mTvAgree;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_auth_html;
    }

    @Override
    protected void initView() {
        mWebView = (ProgressWebView) findViewById(R.id.webView);
        mTvDisagree = (TextView) findViewById(R.id.tv_disagree);
        mTvAgree = (TextView) findViewById(R.id.tv_agree);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.webView));
    }


    @Override
    protected void initData() {
        WebSettings settings = mWebView.getSettings();
        //支持Javascript 与js交互
        settings.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持缩放
        settings.setSupportZoom(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCacheMaxSize(Long.MAX_VALUE);
        //缓存模式
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //解决图片不显示
        settings.setBlockNetworkImage(false);
        //自适应屏幕
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //设置编码格式
        settings.setDefaultTextEncodingName("utf-8");
        //设置可以访问文件
        settings.setAllowFileAccess(true);
        //设置出现内置的缩放控件
        settings.setBuiltInZoomControls(false);
        //多窗口
        settings.setSupportMultipleWindows(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.loadUrl(Constants.HTML_AGREEMENT_URL);
        mWebView.setWebChromeClient(new WebChromeClient());
    }

    private long clickTime = 0;
    private long clickTime1 = 0;
    @Override
    protected void setListener() {
        super.setListener();
        mTvDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    MobclickAgent.onEvent(MerAuthHtmlActivity.this, "mer_authhtml_disagree");
                    Intent intent = new Intent(MerAuthHtmlActivity.this, MerMainActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                    finish();
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mTvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime1 > 3000) {
                    clickTime1 = System.currentTimeMillis();
                    MobclickAgent.onEvent(MerAuthHtmlActivity.this, "mer_authhtml_agree");
                    //前去个人认证
                    mPresenter.getIsSing();
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    @Override
    protected MPublishPresenter createPresenter() {
        return new MPublishPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //跳转到商户主页，我的
            Intent intent = new Intent(MerAuthHtmlActivity.this, MerMainActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void updategetMLabel(MLableEntity mLableEntity) {

    }

    @Override
    public void updategetMLabelMethod(MLableSalaryEntity mLableSalaryEntity) {

    }

    @Override
    public void updategetMLabelSalary(MLableSalaryEntity mLableSalaryEntity) {

    }

    @Override
    public void updategetMLabelContact(MLableContactEntity mLableContactEntity) {

    }

    @Override
    public void updategetIsSing(ResponseData responseData) {
        if (responseData.getCode().equals("200")) {
            Intent intent = new Intent(MerAuthHtmlActivity.this, MerAuthActivity.class);
            intent.putExtra("urlType", 0);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void updategetCheckJob(ResponseData responseData) {

    }

    @Override
    public void updategetAddJob(ResponseData responseData) {

    }

    @Override
    public void updategetMerUserinfo(MUserInfoEntity mUserInfoEntity) {

    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("认证协议页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("认证协议页面");
        MobclickAgent.onPause(this);
    }
}
