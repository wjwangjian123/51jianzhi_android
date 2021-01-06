package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MerMyCompanyActivity extends BaseActivity {

    private ImageView mIvReturn;
    private TextView mTvTitle;
    private TextView mTvService;
    private TextView mTvName;
    private TextView mTvTip;
    private TextView mTvEdit;
    private LinearLayout mLlTip;
    private TextView mTvSeeGuide;
    private SimpleDraweeView mIvImg;
    private TextView mTvTipDesc;
    private ImageView mIvHomepage;
    private TextView mTvHomepage;
    private ImageView mIvPosition;
    private TextView mTvPosition;
    private ImageView mIvPublish;
    private TextView mTvPublish;
    private ImageView mIvMore;
    private TextView mTvMore;
    private TextView mTvNext;
    private String url;
    private String company;
    private String company_desc;
    private int isSing;
    private int type = 0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        MobclickAgent.onEvent(MerMyCompanyActivity.this, "mer_mycompany_in");
        int showService = PreferenceUUID.getInstence().getShowService();
        if (showService == 0) {
            mTvService.setVisibility(View.GONE);
        } else if (showService == 1) {
            mTvService.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_my_company;
    }

    @Override
    protected void initView() {
        mIvReturn = (ImageView) findViewById(R.id.iv_return);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvService = (TextView) findViewById(R.id.tv_service);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvTip = (TextView) findViewById(R.id.tv_tip);
        mTvEdit = (TextView) findViewById(R.id.tv_edit);
        mLlTip = (LinearLayout) findViewById(R.id.ll_tip);
        mTvSeeGuide = (TextView) findViewById(R.id.tv_see_guide);
        mIvImg = (SimpleDraweeView) findViewById(R.id.iv_img);
        mTvNext = (TextView) findViewById(R.id.tv_next);
        mTvTipDesc = (TextView) findViewById(R.id.tv_tip_desc);
        mIvHomepage = (ImageView) findViewById(R.id.iv_homepage);
        mTvHomepage = (TextView) findViewById(R.id.tv_homepage);
        mIvPosition = (ImageView) findViewById(R.id.iv_position);
        mTvPosition = (TextView) findViewById(R.id.tv_position);
        mIvPublish = (ImageView) findViewById(R.id.iv_publish);
        mTvPublish = (TextView) findViewById(R.id.tv_publish);
        mIvMore = (ImageView) findViewById(R.id.iv_more);
        mTvMore = (TextView) findViewById(R.id.tv_more);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.rl_title));
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 0);
        url = getIntent().getStringExtra("url");
        company = getIntent().getStringExtra("company");
        company_desc = getIntent().getStringExtra("company_desc");
        isSing = getIntent().getIntExtra("isSing", 0);
        if (url.equals("") || url.equals(null)) {
            FrescoUtil.setResPic(R.drawable.icon_ch_enterprises, mIvImg);
        } else {
            FrescoUtil.setHttpPic(url, mIvImg);
        }
        if (type == 2) {
            mTvTitle.setText("实名认证");
            mTvName.setText("未认证");
            mTvTipDesc.setVisibility(View.VISIBLE);
            mTvTipDesc.setText("认证个人资质后可享入驻商户特权");
            mTvTipDesc.setTextColor(Color.parseColor("#A1A7B7"));
            mTvTip.setVisibility(View.GONE);
            mTvEdit.setVisibility(View.GONE);
            mLlTip.setVisibility(View.GONE);
            mIvHomepage.setImageResource(R.drawable.icon_mer_per_homepage);
            mTvHomepage.setText("发布职位");
            mIvPosition.setImageResource(R.drawable.icon_mer_per_position);
            mTvPosition.setText("查看效果");
            mIvPublish.setImageResource(R.drawable.icon_mer_per_publish);
            mTvPublish.setText("可免费发布1个线下职位");
            mIvMore.setImageResource(R.drawable.icon_mer_per_more);
            mTvMore.setText("更多特权即将上线");
            mTvSeeGuide.setVisibility(View.VISIBLE);
            mTvNext.setVisibility(View.VISIBLE);
        }
        if (type == 0) {
            mTvTitle.setText("实名认证");
            mTvName.setText(company);
            mTvTipDesc.setVisibility(View.VISIBLE);
            mTvTipDesc.setText("个人已认证，商户待认证");
            mTvTipDesc.setTextColor(Color.parseColor("#666B78"));
            mTvTip.setVisibility(View.VISIBLE);
            mTvEdit.setVisibility(View.VISIBLE);
            mLlTip.setVisibility(View.VISIBLE);
            if (company_desc.equals("") || company_desc.equals(null)) {
                mTvTip.setText("暂未填写个人简介，");
                mTvEdit.setText("点击填写");
            } else {
                mTvTip.setText("个人简介：" + company_desc);
                mTvEdit.setText("点击编辑");
            }
            mIvHomepage.setImageResource(R.drawable.icon_mer_per_homepage);
            mTvHomepage.setText("发布职位");
            mIvPosition.setImageResource(R.drawable.icon_mer_per_position);
            mTvPosition.setText("查看效果");
            mIvPublish.setImageResource(R.drawable.icon_mer_per_publish);
            mTvPublish.setText("可免费发布1个线下职位");
            mIvMore.setImageResource(R.drawable.icon_mer_per_more);
            mTvMore.setText("更多特权即将上线");
            mTvSeeGuide.setVisibility(View.VISIBLE);
            mTvNext.setVisibility(View.VISIBLE);
        }
        if (type == 1) {
            mTvTitle.setText("我的公司");
            mTvName.setText(company);
            mTvTipDesc.setVisibility(View.GONE);
            mTvTip.setVisibility(View.VISIBLE);
            mTvEdit.setVisibility(View.VISIBLE);
            mLlTip.setVisibility(View.VISIBLE);
            if (company_desc.equals("") || company_desc.equals(null)) {
                mTvTip.setText("暂未填写企业简介，");
                mTvEdit.setText("点击填写");
            } else {
                mTvTip.setText("公司简介：" + company_desc);
                mTvEdit.setText("点击编辑");
            }
            mIvHomepage.setImageResource(R.drawable.icon_mer_con_homepage);
            mTvHomepage.setText("公司品牌主页(即将上线)");
            mIvPosition.setImageResource(R.drawable.icon_mer_con_position);
            mTvPosition.setText("职位更具权威性");
            mIvPublish.setImageResource(R.drawable.icon_mer_con_publish);
            mTvPublish.setText("可免费发布3个线下职位");
            mIvMore.setImageResource(R.drawable.icon_mer_con_more);
            mTvMore.setText("更多特权即将上线");
            mTvSeeGuide.setVisibility(View.GONE);
            mTvNext.setVisibility(View.GONE);
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
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mLlTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerMyCompanyActivity.this, MerCompanyDescActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("company_desc", company_desc);
                startActivityForResult(intent, 1000);
            }
        });
        mTvSeeGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0 || type == 3) {
                    //个人认证成功--去企业认证
                    Intent intent = new Intent(MerMyCompanyActivity.this, MerGuideAuthActivity.class);
                    intent.putExtra("guide_type", 1);
                    startActivity(intent);
                    finish();
                }
                if (type == 2) {
                    Intent intent = new Intent(MerMyCompanyActivity.this, MerGuideAuthActivity.class);
                    intent.putExtra("guide_type", 2);
                    startActivity(intent);
                    finish();
                }
            }
        });
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    type = 3;
                    mTvTitle.setText("我的公司");
                    mTvName.setText("未绑定");
                    mTvTipDesc.setVisibility(View.VISIBLE);
                    mTvTipDesc.setText("请您尽快绑定公司，绑定后可享受更多特权");
                    mTvTipDesc.setTextColor(Color.parseColor("#A1A7B7"));
                    mTvTip.setVisibility(View.GONE);
                    mTvEdit.setVisibility(View.GONE);
                    mLlTip.setVisibility(View.GONE);
                    mIvHomepage.setImageResource(R.drawable.icon_mer_con_homepage);
                    mTvHomepage.setText("公司品牌主页(即将上线)");
                    mIvPosition.setImageResource(R.drawable.icon_mer_con_position);
                    mTvPosition.setText("职位更具权威性");
                    mIvPublish.setImageResource(R.drawable.icon_mer_con_publish);
                    mTvPublish.setText("可免费发布3个线下职位");
                    mIvMore.setImageResource(R.drawable.icon_mer_con_more);
                    mTvMore.setText("更多特权即将上线");
                    mTvNext.setVisibility(View.VISIBLE);
                    mTvSeeGuide.setVisibility(View.VISIBLE);
                }
                if (type == 2) {
                    if (System.currentTimeMillis() - clickTime1 > 3000) {
                        clickTime1 = System.currentTimeMillis();
                        //前去个人认证
                        if (isSing == 0) {
                            //未签署进入协议
                            Intent intent = new Intent(MerMyCompanyActivity.this, MerAuthHtmlActivity.class);
                            startActivity(intent);
                        } else if (isSing == 1) {
                            Intent intent = new Intent(MerMyCompanyActivity.this, MerAuthActivity.class);
                            intent.putExtra("urlType", 0);
                            startActivity(intent);
                        }
                        finish();
                    } else {
                        showToast("点击过于频繁请稍后再试");
                    }
                }
                if (type == 3) {
                    if (System.currentTimeMillis() - clickTime1 > 3000) {
                        clickTime1 = System.currentTimeMillis();
                        //前去企业认证
                        Intent intent = new Intent(MerMyCompanyActivity.this, MerUploadInfoActivity.class);
                        intent.putExtra("urlType", 0);
                        startActivity(intent);
                        finish();
                    } else {
                        showToast("点击过于频繁请稍后再试");
                    }
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
            HashMap<String, String> clientInfo = new HashMap<>();
            clientInfo.put("name", PreferenceUUID.getInstence().getMerName());
            clientInfo.put("avatar", PreferenceUUID.getInstence().getMerAvatar());
            clientInfo.put("tel", PreferenceUUID.getInstence().getUserPhone());
            clientInfo.put("userId", PreferenceUUID.getInstence().getUserId());
            clientInfo.put("身份", "商户");
            HashMap<String, String> updateInfo = new HashMap<>();
            updateInfo.put("name", PreferenceUUID.getInstence().getMerName());
            updateInfo.put("avatar", PreferenceUUID.getInstence().getMerAvatar());
            updateInfo.put("tel", PreferenceUUID.getInstence().getUserPhone());
            updateInfo.put("userId", PreferenceUUID.getInstence().getUserId());
            updateInfo.put("身份", "商户");
            Intent intent = new MQIntentBuilder(MerMyCompanyActivity.this)
                    .setClientInfo(clientInfo)
                    .updateClientInfo(updateInfo)
                    .setCustomizedId(PreferenceUUID.getInstence().getUserId())
                    .build();
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
            HashMap<String, String> clientInfo = new HashMap<>();
            clientInfo.put("name", PreferenceUUID.getInstence().getMerName());
            clientInfo.put("avatar", PreferenceUUID.getInstence().getMerAvatar());
            clientInfo.put("tel", PreferenceUUID.getInstence().getUserPhone());
            clientInfo.put("userId", PreferenceUUID.getInstence().getUserId());
            clientInfo.put("身份", "商户");
            HashMap<String, String> updateInfo = new HashMap<>();
            updateInfo.put("name", PreferenceUUID.getInstence().getMerName());
            updateInfo.put("avatar", PreferenceUUID.getInstence().getMerAvatar());
            updateInfo.put("tel", PreferenceUUID.getInstence().getUserPhone());
            updateInfo.put("userId", PreferenceUUID.getInstence().getUserId());
            updateInfo.put("身份", "商户");
            Intent intent = new MQIntentBuilder(MerMyCompanyActivity.this)
                    .setClientInfo(clientInfo)
                    .updateClientInfo(updateInfo)
                    .setCustomizedId(PreferenceUUID.getInstence().getUserId())
                    .build();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == 100 && data != null) {
                String desc = data.getStringExtra("desc");
                if (type == 0) {
                    mTvTip.setText("个人简介：" + desc);
                }
                if (type == 1) {
                    mTvTip.setText("公司简介：" + desc);
                }
                company_desc = desc;
            }
        }
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
