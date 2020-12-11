package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.ActivityUtils;
import com.part.jianzhiyi.corecommon.utils.CopyTextLibrary;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MSwitchMerchantsEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.ChooseContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.ChoosePresenter;
import com.umeng.analytics.MobclickAgent;

@Route(path = "/merchants/activity/choose")
public class ChooseIdentityActivity extends BaseActivity<ChoosePresenter> implements ChooseContract.IChooseView, View.OnClickListener {

    private TextView mTvCandidates;
    private RelativeLayout mRlCandidates;
    private TextView mTvEnterprises;
    private RelativeLayout mRlEnterprises;
    private Button mBtnSwich;
    private ImageView mIvCandidatesBg;
    private ImageView mIvEnterprisesBg;
    private int type = 0;
    private int selectType = 0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_choose_identity;
    }

    @Override
    protected void initView() {
        mTvCandidates = (TextView) findViewById(R.id.tv_candidates);
        mRlCandidates = (RelativeLayout) findViewById(R.id.rl_candidates);
        mTvEnterprises = (TextView) findViewById(R.id.tv_enterprises);
        mRlEnterprises = (RelativeLayout) findViewById(R.id.rl_enterprises);
        mBtnSwich = (Button) findViewById(R.id.btn_swich);
        mIvCandidatesBg = (ImageView) findViewById(R.id.iv_candidates_bg);
        mIvEnterprisesBg = (ImageView) findViewById(R.id.iv_enterprises_bg);
        initToolbar("");
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            selectType = 0;
            mRlCandidates.setSelected(true);
            mTvCandidates.setSelected(true);
            mIvCandidatesBg.setVisibility(View.VISIBLE);
        } else if (type == 1) {
            selectType = 1;
            mRlEnterprises.setSelected(true);
            mTvEnterprises.setSelected(true);
            mIvEnterprisesBg.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void initData() {
        mBtnSwich.setOnClickListener(this);
        mRlCandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType = 0;
                mRlCandidates.setSelected(true);
                mTvCandidates.setSelected(true);
                mRlEnterprises.setSelected(false);
                mTvEnterprises.setSelected(false);
                mIvCandidatesBg.setVisibility(View.VISIBLE);
                mIvEnterprisesBg.setVisibility(View.GONE);
            }
        });
        mRlEnterprises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType = 1;
                mRlCandidates.setSelected(false);
                mTvCandidates.setSelected(false);
                mRlEnterprises.setSelected(true);
                mTvEnterprises.setSelected(true);
                mIvCandidatesBg.setVisibility(View.GONE);
                mIvEnterprisesBg.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected ChoosePresenter createPresenter() {
        return new ChoosePresenter(this);
    }

    @Override
    public void startIntent() {

    }

    private long clickTime = 0;
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_swich) {
            if (System.currentTimeMillis() - clickTime > 3000) {
                clickTime = System.currentTimeMillis();
                //切换身份
                if (type == 0 && selectType == 0) {
                    //客户端切客户端
                    ChooseIdentityActivity.this.finish();
                }
                if (type == 1 && selectType == 1) {
                    //商户端切商户端
                    ChooseIdentityActivity.this.finish();
                }
                if (type == 0 && selectType == 1) {
                    //客户端切换商户端
                    if (PreferenceUUID.getInstence().getUserPhone().equals("") || PreferenceUUID.getInstence().getUserPhone().equals(null)) {
                        showToast("手机号为空，请重新登录");
                        return;
                    }
                    mPresenter.getUserChabge(PreferenceUUID.getInstence().getUserPhone());
                }
                if (type == 1 && selectType == 0) {
                    //商户端切换客户端
                    ActivityUtils.removeAllActivity();
                    PreferenceUUID.getInstence().putStatus(0);
                    ARouter.getInstance().build("/app/activity/main").withInt("type", 1).navigation();
                    ChooseIdentityActivity.this.finish();
                }
            }else {
                showToast("点击过于频繁请稍后再试");
            }
        }
    }

    @Override
    public void updategetUserChabge(MSwitchMerchantsEntity mSwitchMerchantsEntity) {
        if (mSwitchMerchantsEntity != null) {
            if (mSwitchMerchantsEntity.getCode().equals("200")) {
                PreferenceUUID.getInstence().putStatus(1);
                showToast(mSwitchMerchantsEntity.getMsg());
                //判断是否实名认证
                if (mSwitchMerchantsEntity.getData().getBus_info().getIs_auth() == 1) {
                    PreferenceUUID.getInstence().putMerName(mSwitchMerchantsEntity.getData().getBus_info().getName());
                    PreferenceUUID.getInstence().putIsEnterprise(mSwitchMerchantsEntity.getData().getBus_info().getIs_enterprise());
                }
                //切换成功,跳转到商户端引导页
                ActivityUtils.removeAllActivity();
                if (!PreferenceUUID.getInstence().getisMerGuide()) {
                    //第一次进入引导页
                    Intent intent = new Intent(ChooseIdentityActivity.this, MerGuideActivity.class);
                    intent.putExtra("is_sing", mSwitchMerchantsEntity.getData().getBus_info().getIs_sing());
                    startActivity(intent);
                } else {
                    //判断是否需要进入协议
                    if (mSwitchMerchantsEntity.getData().getBus_info().getIs_sing() == 0) {
                        //未签署进入协议
                        Intent intent = new Intent(ChooseIdentityActivity.this, MerAuthHtmlActivity.class);
                        startActivity(intent);
                    } else if (mSwitchMerchantsEntity.getData().getBus_info().getIs_sing() == 1) {
                        Intent intent = new Intent(ChooseIdentityActivity.this, MerMainActivity.class);
                        intent.putExtra("type", 0);
                        startActivity(intent);
                    }
                }
                //销毁当前activity
                ChooseIdentityActivity.this.finish();
            } else if (mSwitchMerchantsEntity.getCode().equals("202")) {
                //代理/被代理,选择切换时的弹框
                initDialogCopy(mSwitchMerchantsEntity.getMsg(), mSwitchMerchantsEntity.getData().getUrl());
            } else if (mSwitchMerchantsEntity.getCode().equals("203")) {
                //账户封停提示
                initDialogClosure(mSwitchMerchantsEntity.getMsg(), mSwitchMerchantsEntity.getData().getStart_time(), mSwitchMerchantsEntity.getData().getEnd_time());
            }
        }
    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    private void initDialogClosure(String mtip, String mstarttime, String mendtime) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChooseIdentityActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(ChooseIdentityActivity.this).inflate(R.layout.dialog_tip_closure, null, false);
        TextView tip = inflate.findViewById(R.id.dialog_tv_tip);
        TextView starttime = inflate.findViewById(R.id.dialog_tv_starttime);
        TextView endtime = inflate.findViewById(R.id.dialog_tv_endtime);
        ImageView cancel = inflate.findViewById(R.id.dialog_iv_cancel);
        alertDialog.setView(inflate);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        //显示
        alertDialog.show();
        tip.setText(mtip);
        starttime.setText(mstarttime);
        endtime.setText(mendtime);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void initDialogCopy(String mtip, String mcontent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChooseIdentityActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(ChooseIdentityActivity.this).inflate(R.layout.dialog_tip_logincomputer, null, false);
        TextView tip = inflate.findViewById(R.id.dialog_tv_tip);
        TextView content = inflate.findViewById(R.id.dialog_tv_content);
        TextView copy = inflate.findViewById(R.id.dialog_tv_copy);
        ImageView cancel = inflate.findViewById(R.id.dialog_iv_cancel);
        alertDialog.setView(inflate);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        //显示
        alertDialog.show();
        tip.setText(mtip);
        content.setText(mcontent);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //复制链接地址
                if (!mcontent.equals("") && !mcontent.equals(null)) {
                    CopyTextLibrary copyButtonLibrary = new CopyTextLibrary(ChooseIdentityActivity.this, mcontent);
                    copyButtonLibrary.init();
                    alertDialog.dismiss();
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
        MobclickAgent.onPageStart("切换身份页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("切换身份页面");
        MobclickAgent.onPause(this);
    }
}
