package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.customview.ProgressWebView;

import java.util.HashMap;
import java.util.Map;

public class HtmlIntegralActivity extends BaseActivity {

    private ProgressWebView mWebView;
    private String murl;
    private String title;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_html_integral;
    }

    @Override
    protected void initView() {
        mWebView = (ProgressWebView) findViewById(R.id.webView);
        murl = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        initToolbar(title);
    }

    @Override
    protected void initData() {
        LinearLayout.LayoutParams mWebViewLP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);
        mWebView.setLayoutParams(mWebViewLP);
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
        mWebView.loadUrl(murl);
        mWebView.setWebViewClient(new WebViewClient() {
            String referer = "http://jfwechat.chengquan.cn";

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    if (url.startsWith("weixin://") || url.startsWith("alipays://")) {
                        //打开支付
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }
                if (url.contains("https://wx.tenpay.com")) {
                    //微信支付要用，不然说"商家参数格式有误"
                    Map<String, String> extraHeaders = new HashMap<>();
                    extraHeaders.put("Referer", referer);
                    view.loadUrl(url, extraHeaders);
                    referer = url;
                    return true;
                }
                view.loadUrl(url);
                return true;
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
    protected void onDestroy() {
        super.onDestroy();
        mWebView.stopLoading();
        mWebView.removeAllViews();
        mWebView.destroy();
        mWebView = null;
    }
}
