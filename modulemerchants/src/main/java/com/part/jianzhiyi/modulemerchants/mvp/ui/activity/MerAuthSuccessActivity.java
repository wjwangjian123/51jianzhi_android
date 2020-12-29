package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MMineContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MMinePresenter;
import com.umeng.analytics.MobclickAgent;

public class MerAuthSuccessActivity extends BaseActivity<MMinePresenter> implements MMineContract.IMMineView {

    private ImageView mIvReturn;
    private TextView mTvNext;
    private ImageView mIvStatus;
    private TextView mTvStatus;
    private TextView mTvContent;
    private int type = 0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_auth_success;
    }

    @Override
    protected void initView() {
        mIvReturn = (ImageView) findViewById(R.id.iv_return);
        mTvNext = (TextView) findViewById(R.id.tv_next);
        mIvStatus = (ImageView) findViewById(R.id.iv_status);
        mTvStatus = (TextView) findViewById(R.id.tv_status);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.rl_title));
    }

    private long clickTime = 0;
    private long clickTime1 = 0;

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            //个人认证审核中
            mIvStatus.setImageResource(R.drawable.icon_mer_auth_success);
            mTvStatus.setText("认证审核中");
            mTvContent.setText("审核将在30分钟-1个工作日内完成审核" + "\n" + "届时将通过短信通知您审核情况；" + "\n" + "审核通过后即可发布兼职，请耐心等待");
            mTvNext.setText("好的");
            mTvNext.setBackgroundResource(R.drawable.icon_mer_btn_bg);
        }
        if (type == 1) {
            //个人认证审核通过
            mIvStatus.setImageResource(R.drawable.icon_mer_auth_successed);
            mTvStatus.setText("个人身份认证成功");
            mTvContent.setText("绑定企业资质将获得更多曝光及专属权限");
            mTvNext.setText("去绑定");
            mTvNext.setBackgroundResource(R.drawable.icon_mer_btn_purple);
        }
        if (type == 2) {
            //企业认证审核中
            mIvStatus.setImageResource(R.drawable.icon_mer_company_success);
            mTvStatus.setText("认证审核中");
            mTvContent.setText("审核将在30分钟-1个工作日内完成审核" + "\n" + "届时将通过短信通知您审核情况；" + "\n" + "审核通过后即可发布兼职，请耐心等待");
            mTvNext.setText("好的");
            mTvNext.setBackgroundResource(R.drawable.icon_mer_btn_bg);
        }
        if (type == 3) {
            //企业认证审核通过
            mIvStatus.setImageResource(R.drawable.icon_mer_company_successed);
            mTvStatus.setText("企业资质认证成功");
            mTvContent.setText("您现在可发布职位并即时查看招聘效果！");
            mTvNext.setText("去发布职位");
            mTvNext.setBackgroundResource(R.drawable.icon_mer_btn_purple);
        }
        mIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    //跳转到商户主页，我的
                    Intent intent = new Intent(MerAuthSuccessActivity.this, MerMainActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                    MerAuthSuccessActivity.this.finish();
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime1 > 3000) {
                    clickTime1 = System.currentTimeMillis();
                    if (type == 0 || type == 2) {
                        //跳转到商户主页，我的
                        Intent intent = new Intent(MerAuthSuccessActivity.this, MerMainActivity.class);
                        intent.putExtra("type", 1);
                        startActivity(intent);
                        MerAuthSuccessActivity.this.finish();
                    }
                    if (type == 1) {
                        //前去企业认证
                        Intent intent = new Intent(MerAuthSuccessActivity.this, MerUploadInfoActivity.class);
                        intent.putExtra("urlType", 0);
                        startActivity(intent);
                        MerAuthSuccessActivity.this.finish();
                    }
                    if (type == 3) {
                        //判断是否可发布
                        mPresenter.getMerUserinfo();
                    }
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    @Override
    protected MMinePresenter createPresenter() {
        return new MMinePresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //跳转到商户主页，我的
            Intent intent = new Intent(MerAuthSuccessActivity.this, MerMainActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
            MerAuthSuccessActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("个人认证提交成功页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("个人认证提交成功页面");
        MobclickAgent.onPause(this);
    }

    @Override
    public void updategetMerUserinfo(MUserInfoEntity mUserInfoEntity) {
        if (mUserInfoEntity != null) {
            if (mUserInfoEntity.getUserinfo().getJob_add() == 0) {
                showToast(mUserInfoEntity.getUserinfo().getAdd_msg());
            } else if (mUserInfoEntity.getUserinfo().getJob_add() == 1) {
                initDialogAuthTip(mUserInfoEntity.getUserinfo().getAdd_msg(), mUserInfoEntity.getUserinfo().getIs_sing(), mUserInfoEntity.getUserinfo().getCert_status());
            } else if (mUserInfoEntity.getUserinfo().getJob_add() == 2) {
                Intent intent = new Intent(MerAuthSuccessActivity.this, MerSelectPositionActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("mType", 1);
                startActivity(intent);
                finish();
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

    private void initDialogAuthTip(String mtip, int isSing, int cer_status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MerAuthSuccessActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(MerAuthSuccessActivity.this).inflate(R.layout.dialog_tip_publish_auth, null, false);
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
                    Intent intent = new Intent(MerAuthSuccessActivity.this, MerAuthHtmlActivity.class);
                    startActivity(intent);
                    finish();
                } else if (isSing == 1) {
                    Intent intent = new Intent(MerAuthSuccessActivity.this, MerAuthActivity.class);
                    if (cer_status == 3) {
                        intent.putExtra("urlType", 1);
                    } else {
                        intent.putExtra("urlType", 0);
                    }
                    startActivity(intent);
                    finish();
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
}
