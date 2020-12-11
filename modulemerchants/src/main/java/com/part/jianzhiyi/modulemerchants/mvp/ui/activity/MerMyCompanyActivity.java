package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class MerMyCompanyActivity extends BaseActivity {

    private ImageView mIvReturn;
    private TextView mTvService;
    private TextView mTvName;
    private TextView mTvTip;
    private SimpleDraweeView mIvImg;
    private TextView mTvNext;
    private String url;
    private String company;
    private int type = 0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        MobclickAgent.onEvent(MerMyCompanyActivity.this, "mer_mycompany_in");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_my_company;
    }

    @Override
    protected void initView() {
        mIvReturn = (ImageView) findViewById(R.id.iv_return);
        mTvService = (TextView) findViewById(R.id.tv_service);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvTip = (TextView) findViewById(R.id.tv_tip);
        mIvImg = (SimpleDraweeView) findViewById(R.id.iv_img);
        mTvNext = (TextView) findViewById(R.id.tv_next);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.rl_title));
    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra("url");
        type = getIntent().getIntExtra("type", 0);
        company = getIntent().getStringExtra("company");
        if (type == 0) {
            mTvName.setText("未绑定");
            mTvTip.setVisibility(View.VISIBLE);
            mTvNext.setVisibility(View.VISIBLE);
        } else if (type == 1) {
            mTvName.setText(company);
            mTvTip.setVisibility(View.GONE);
            mTvNext.setVisibility(View.GONE);
        }
        if (url.equals("") || url.equals(null)) {
            FrescoUtil.setResPic(R.drawable.icon_ch_enterprises, mIvImg);
        } else {
            FrescoUtil.setHttpPic(url, mIvImg);
        }
    }

    private long clickTime = 0;
    private long clickTime1 = 0;
    @Override
    protected void setListener() {
        super.setListener();
        mIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    //联系客服
                    checkAndRequestPermission();
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
                    //前去企业认证
                    Intent intent = new Intent(MerMyCompanyActivity.this, MerUploadInfoActivity.class);
                    intent.putExtra("urlType", 0);
                    startActivity(intent);
                    finish();
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        // 快手SDK所需相关权限，存储权限，此处配置作用于流量分配功能，关于流量分配，详情请咨询商务;如果您的APP不需要快手SDK的流量分配功能，则无需申请SD卡权限
        if (!(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        // 如果需要的权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
            Intent intent = new MQIntentBuilder(MerMyCompanyActivity.this).build();
            startActivity(intent);
        } else {
            // 否则，建议请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {
            Intent intent = new MQIntentBuilder(MerMyCompanyActivity.this).build();
            startActivity(intent);
        }
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
        MobclickAgent.onPageStart("商户端我的公司页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端我的公司页面");
        MobclickAgent.onPause(this);
    }
}
