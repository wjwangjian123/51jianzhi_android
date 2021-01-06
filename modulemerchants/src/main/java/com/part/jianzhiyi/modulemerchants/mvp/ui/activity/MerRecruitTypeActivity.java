package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MConfigEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MMineContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MMinePresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MerRecruitTypeActivity extends BaseActivity<MMinePresenter> implements MMineContract.IMMineView {

    private ImageView mIvTop;
    private ImageView mIvReturn;
    private TextView mTvTitle;
    private ImageView mIvBottom;
    private TextView mTvContent;
    private TextView mTvDesc;
    private ImageView mIvImg;
    private TextView mTvTip;
    private RelativeLayout mRlContent;
    private ImageView mIvTab;
    private TextView mTvTab;
    private TextView mTvIntro;
    private ImageView mIvBg;
    private ImageView mIvContent;
    private TextView mTvGo;
    private LinearLayout mLlNext;
    private int type = 0;
    private String content;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_recruit_type;
    }

    @Override
    protected void initView() {
        mIvTop = (ImageView) findViewById(R.id.iv_top);
        mIvReturn = (ImageView) findViewById(R.id.iv_return);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvBottom = (ImageView) findViewById(R.id.iv_bottom);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mTvDesc = (TextView) findViewById(R.id.tv_desc);
        mIvImg = (ImageView) findViewById(R.id.iv_img);
        mTvTip = (TextView) findViewById(R.id.tv_tip);
        mRlContent = (RelativeLayout) findViewById(R.id.rl_content);
        mIvTab = (ImageView) findViewById(R.id.iv_tab);
        mTvTab = (TextView) findViewById(R.id.tv_tab);
        mTvIntro = (TextView) findViewById(R.id.tv_intro);
        mIvBg = (ImageView) findViewById(R.id.iv_bg);
        mIvContent = (ImageView) findViewById(R.id.iv_content);
        mTvGo = (TextView) findViewById(R.id.tv_go);
        mLlNext = (LinearLayout) findViewById(R.id.ll_next);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.rl_title));
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 0);
        content = getIntent().getStringExtra("content");
        if (type == 1) {
            //发布
            mTvTitle.setText("发布岗位");
            mIvTop.setImageResource(R.drawable.icon_mer_publish_top);
            mIvBottom.setImageResource(R.drawable.icon_mer_publish_bottom);
            mRlContent.setBackgroundResource(R.drawable.icon_mer_publish_bg);
            mTvContent.setText("发布岗位");
            mTvDesc.setText("个人商户可发布1个线下兼职，企业商户可发布3个线下兼职");
            mTvTip.setText(content);
            mTvTip.setBackgroundResource(R.drawable.icon_mer_publish_text);
            mIvImg.setImageResource(R.drawable.icon_mer_publish_img);
            mTvTab.setText("发布线下兼职");
            mIvTab.setImageResource(R.drawable.icon_mer_publish_tab);
            mTvIntro.setText("线下兼职限时免费，个人商户可发布1个线下兼职，企业商户可发布3个线下兼职。");
            mIvBg.setImageResource(R.drawable.icon_mer_publish_yin);
            mIvContent.setImageResource(R.drawable.icon_mer_publish_content);
            mTvGo.setBackgroundResource(R.drawable.icon_mer_publish_btn);
        }
        if (type == 2) {
            //刷新
            mTvTitle.setText("刷新岗位");
            mIvTop.setImageResource(R.drawable.icon_mer_refresh_top);
            mIvBottom.setImageResource(R.drawable.icon_mer_refresh_bottom);
            mRlContent.setBackgroundResource(R.drawable.icon_mer_refresh_bg);
            mTvContent.setText("刷新岗位");
            mTvDesc.setText("职位每天可刷新一次，刷新将提高职位展示排名获得更多曝光");
            mTvTip.setBackgroundResource(R.drawable.icon_mer_refresh_text);
            mIvImg.setImageResource(R.drawable.icon_mer_refresh_img);
            mTvTab.setText("首页-展位");
            mIvTab.setImageResource(R.drawable.icon_mer_refresh_tab);
            mTvIntro.setText("线下职位，每天可手动刷新一次，刷新后将提高展示权重并随时间衰减。");
            mIvBg.setImageResource(R.drawable.icon_mer_refresh_yin);
            mIvContent.setImageResource(R.drawable.icon_mer_refresh_content);
            mTvGo.setBackgroundResource(R.drawable.icon_mer_refresh_btn);
        }
        if (type == 3) {
            //尊享
            mTvTitle.setText("尊享展位");
            mIvTop.setImageResource(R.drawable.icon_mer_enjoy_top);
            mIvBottom.setImageResource(R.drawable.icon_mer_enjoy_bottom);
            mRlContent.setBackgroundResource(R.drawable.icon_mer_enjoy_bg);
            mTvContent.setText("尊享展位");
            mTvDesc.setSingleLine();
            mTvDesc.setText("APP-主屏首要位置展示，品效合一");
            mTvTip.setBackgroundResource(R.drawable.icon_mer_enjoy_text);
            mIvImg.setImageResource(R.drawable.icon_mer_enjoy_img);
            mTvTab.setText("首页-展位");
            mIvTab.setImageResource(R.drawable.icon_mer_enjoy_tab);
            mTvIntro.setText("APP-主屏首要位置，尊享展示位。");
            mIvBg.setImageResource(R.drawable.icon_mer_enjoy_yin);
            mIvContent.setImageResource(R.drawable.icon_mer_enjoy_content);
            mTvGo.setBackgroundResource(R.drawable.icon_mer_enjoy_btn);
            int showService = PreferenceUUID.getInstence().getShowService();
            if (showService == 0) {
                mLlNext.setVisibility(View.GONE);
            } else if (showService == 1) {
                mLlNext.setVisibility(View.VISIBLE);
            }
        }
        if (type == 4) {
            //排名
            mTvTitle.setText("排名优先");
            mIvTop.setImageResource(R.drawable.icon_mer_rank_top);
            mIvBottom.setImageResource(R.drawable.icon_mer_rank_bottom);
            mRlContent.setBackgroundResource(R.drawable.icon_mer_rank_bg);
            mTvContent.setText("排名优先");
            mTvDesc.setSingleLine();
            mTvDesc.setText("职位更靠前，带来更多意向求职者");
            mTvTip.setBackgroundResource(R.drawable.icon_mer_rank_text);
            mIvImg.setImageResource(R.drawable.icon_mer_rank_img);
            mTvTab.setText("排名提升");
            mIvTab.setImageResource(R.drawable.icon_mer_rank_tab);
            mTvIntro.setText("职位排序更靠前，带来更多意向求职者。");
            mIvBg.setImageResource(R.drawable.icon_mer_rank_yin);
            mIvContent.setImageResource(R.drawable.icon_mer_rank_content);
            mTvGo.setBackgroundResource(R.drawable.icon_mer_rank_btn);
            int showService = PreferenceUUID.getInstence().getShowService();
            if (showService == 0) {
                mLlNext.setVisibility(View.GONE);
            } else if (showService == 1) {
                mLlNext.setVisibility(View.VISIBLE);
            }
        }
        if (type == 5) {
            //智能优聘
            mTvTitle.setText("智能优聘");
            mIvTop.setImageResource(R.drawable.icon_mer_intellect_top);
            mIvBottom.setImageResource(R.drawable.icon_mer_intellect_bottom);
            mRlContent.setBackgroundResource(R.drawable.icon_mer_intellect_bg);
            mTvContent.setText("智能优聘");
            mTvDesc.setSingleLine();
            mTvDesc.setText("智能算法，保障招聘质量和成本");
            mTvTip.setBackgroundResource(R.drawable.icon_mer_intellect_text);
            mIvImg.setImageResource(R.drawable.icon_mer_intellect_img);
            mTvTab.setText("智能算法");
            mIvTab.setImageResource(R.drawable.icon_mer_intellect_tab);
            mTvIntro.setText("同时核算访客数与报名数，根据转化效果收费，转化更稳定，成本更可控。");
            mIvBg.setImageResource(R.drawable.icon_mer_intellect_yin);
            mIvContent.setImageResource(R.drawable.icon_mer_intellect_content);
            mTvGo.setBackgroundResource(R.drawable.icon_mer_intellect_btn);
            int showService = PreferenceUUID.getInstence().getShowService();
            if (showService == 0) {
                mLlNext.setVisibility(View.GONE);
            } else if (showService == 1) {
                mLlNext.setVisibility(View.VISIBLE);
            }
        }
    }

    private long clickTime = 0;

    @Override
    protected void setListener() {
        super.setListener();
        mIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    if (type == 1) {
                        //跳转至发布页面
                        //判断是否可发布
                        mPresenter.getMerUserinfo();
                    }
                    if (type == 2) {
                        //跳转至已发布列表
                        Intent intent = new Intent(MerRecruitTypeActivity.this, MerMainActivity.class);
                        intent.putExtra("type", 2);
                        startActivity(intent);
                    }
                    if (type == 3) {
                        //美洽
                        //联系客服
                        checkAndRequestPermission();
                    }
                    if (type == 4) {
                        //美洽
                        //联系客服
                        checkAndRequestPermission();
                    }
                    if (type == 5) {
                        //美洽
                        //联系客服
                        checkAndRequestPermission();
                    }
                } else {
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
            Intent intent = new MQIntentBuilder(MerRecruitTypeActivity.this)
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
            Intent intent = new MQIntentBuilder(MerRecruitTypeActivity.this)
                    .setClientInfo(clientInfo)
                    .updateClientInfo(updateInfo)
                    .setCustomizedId(PreferenceUUID.getInstence().getUserId())
                    .build();
            startActivity(intent);
        }
    }

    @Override
    protected MMinePresenter createPresenter() {
        return new MMinePresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updategetMerUserinfo(MUserInfoEntity mUserInfoEntity) {
        if (mUserInfoEntity != null) {
            if (mUserInfoEntity.getUserinfo().getJob_add() == 0) {
                showToast(mUserInfoEntity.getUserinfo().getAdd_msg());
            } else if (mUserInfoEntity.getUserinfo().getJob_add() == 1) {
                initDialogAuthTip(mUserInfoEntity.getUserinfo().getAdd_msg(), mUserInfoEntity.getUserinfo().getIs_sing(), mUserInfoEntity.getUserinfo().getCert_status());
            } else if (mUserInfoEntity.getUserinfo().getJob_add() == 2) {
                Intent intent = new Intent(MerRecruitTypeActivity.this, MerSelectPositionActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("mType", 0);
                startActivity(intent);
            }
        }
    }

    @Override
    public void updategetAvatar(ResponseData responseData) {

    }

    @Override
    public void updategetOpinion(ResponseData responseData) {

    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    public void updategetCheck(MCheckVersionEntity mCheckVersionEntity) {

    }

    @Override
    public void updategetConfig(MConfigEntity mConfigEntity) {

    }

    private void initDialogAuthTip(String mtip, int isSing, int cer_status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MerRecruitTypeActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(MerRecruitTypeActivity.this).inflate(R.layout.dialog_tip_publish_auth, null, false);
        TextView text = inflate.findViewById(R.id.tv_text);
        TextView tip = inflate.findViewById(R.id.tv_tip);
        TextView auth = inflate.findViewById(R.id.tv_auth);
        ImageView cancel = inflate.findViewById(R.id.iv_cancel);
        alertDialog.setView(inflate);
        text.setText("温馨提示");
        auth.setText("去认证");
        tip.setText(mtip);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        //显示
        alertDialog.show();
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //跳转个人认证
                if (isSing == 0) {
                    //未签署进入协议
                    Intent intent = new Intent(MerRecruitTypeActivity.this, MerAuthHtmlActivity.class);
                    startActivity(intent);
                } else if (isSing == 1) {
                    Intent intent = new Intent(MerRecruitTypeActivity.this, MerAuthActivity.class);
                    if (cer_status == 3) {
                        intent.putExtra("urlType", 1);
                    } else {
                        intent.putExtra("urlType", 0);
                    }
                    startActivity(intent);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端招聘推广二级页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端招聘推广二级页面");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
    }
}
