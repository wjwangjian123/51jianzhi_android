package com.part.jianzhiyi.mvp.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.corecommon.constants.IntentConstant;
import com.part.jianzhiyi.customview.ProgressWebView;
import com.umeng.analytics.MobclickAgent;

@Route(path = "/app/activity/html")
public class HtmlActivity extends BaseActivity {

    private ProgressWebView mWebView;
    private String url;
    private String title;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_html;
    }

    @Override
    protected void initView() {
        mWebView = (ProgressWebView) findViewById(R.id.webView);
        url = getIntent().getStringExtra(IntentConstant.HTML_URL);
        title = getIntent().getStringExtra("title");
        initToolbar(title);
    }

    @Override
    protected void initData() {
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
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
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WebView.setWebContentsDebuggingEnabled(true);
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.loadUrl(url);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
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
        MobclickAgent.onPageStart("用户协议");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("用户协议");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.stopLoading();
        mWebView.removeAllViews();
        mWebView.destroy();
        mWebView = null;
    }
}
