package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.part.jianzhiyi.corecommon.ui.InputFilteEditText;
import com.part.jianzhiyi.corecommon.utils.RegularUtils;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MMineContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MMinePresenter;
import com.umeng.analytics.MobclickAgent;

public class MerFeedbackActivity extends BaseActivity<MMinePresenter> implements MMineContract.IMMineView {

    private TextView mTvOne;
    private ImageView mIvOne;
    private TextView mTvTwo;
    private ImageView mIvTwo;
    private TextView mTvThree;
    private ImageView mIvThree;
    private TextView mTvFour;
    private ImageView mIvFour;
    private TextView mTvFive;
    private ImageView mIvFive;
    private RelativeLayout mRlOne;
    private RelativeLayout mRlTwo;
    private RelativeLayout mRlThree;
    private RelativeLayout mRlFour;
    private RelativeLayout mRlFive;
    private EditText mEtDesc;
    private InputFilteEditText mEtContact;
    private TextView mTvSubmit;
    private View[] mViews;
    private View[] mViewImgs;
    private String content;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_feedback;
    }

    @Override
    protected void initView() {
        mTvOne = (TextView) findViewById(R.id.tv_one);
        mIvOne = (ImageView) findViewById(R.id.iv_one);
        mTvTwo = (TextView) findViewById(R.id.tv_two);
        mIvTwo = (ImageView) findViewById(R.id.iv_two);
        mTvThree = (TextView) findViewById(R.id.tv_three);
        mIvThree = (ImageView) findViewById(R.id.iv_three);
        mTvFour = (TextView) findViewById(R.id.tv_four);
        mIvFour = (ImageView) findViewById(R.id.iv_four);
        mTvFive = (TextView) findViewById(R.id.tv_five);
        mIvFive = (ImageView) findViewById(R.id.iv_five);
        mRlOne = (RelativeLayout) findViewById(R.id.rl_one);
        mRlTwo = (RelativeLayout) findViewById(R.id.rl_two);
        mRlThree = (RelativeLayout) findViewById(R.id.rl_three);
        mRlFour = (RelativeLayout) findViewById(R.id.rl_four);
        mRlFive = (RelativeLayout) findViewById(R.id.rl_five);
        mEtDesc = (EditText) findViewById(R.id.et_desc);
        mEtContact = (InputFilteEditText) findViewById(R.id.et_contact);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        initToolbar("意见反馈");
    }

    @Override
    protected void initData() {
        mTvOne.setSelected(true);
        mIvOne.setVisibility(View.VISIBLE);
        content = mTvOne.getText().toString().trim();
        mViews = new View[]{mTvOne, mTvTwo, mTvThree, mTvFour, mTvFive};
        mViewImgs = new View[]{mIvOne, mIvTwo, mIvThree, mIvFour, mIvFive};
    }

    private long clickTime = 0;

    @Override
    protected void setListener() {
        super.setListener();
        mRlOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = mTvOne.getText().toString().trim();
                setTabSelected(0);
            }
        });
        mRlTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = mTvTwo.getText().toString().trim();
                setTabSelected(1);
            }
        });
        mRlThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = mTvThree.getText().toString().trim();
                setTabSelected(2);
            }
        });
        mRlFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = mTvFour.getText().toString().trim();
                setTabSelected(3);
            }
        });
        mRlFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = mTvFive.getText().toString().trim();
                setTabSelected(4);
            }
        });
        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content.equals("") || content.equals(null)) {
                    showToast("请选择吐槽点");
                    return;
                }
                if (TextUtils.isEmpty(mEtDesc.getText().toString().trim())) {
                    showToast("请填写描述");
                    return;
                }
                if (!TextUtils.isEmpty(mEtContact.getText().toString().trim())) {
                    if (!RegularUtils.isMobileNumber(mEtContact.getText().toString().trim()) && !RegularUtils.isEmail(mEtContact.getText().toString().trim())) {
                        showToast("请填写正确的手机号或邮箱");
                        return;
                    }
                }
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    mPresenter.getOpinion(content, mEtDesc.getText().toString().trim(), mEtContact.getText().toString().trim());
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    private void setTabSelected(int position) {
        int count = mViews.length;
        for (int i = 0; i < count; i++) {
            if (i == position) {
                mViews[i].setSelected(true);
                mViewImgs[i].setVisibility(View.VISIBLE);
            } else {
                mViews[i].setSelected(false);
                mViewImgs[i].setVisibility(View.GONE);
            }
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

    }

    @Override
    public void updategetAvatar(ResponseData responseData) {

    }

    @Override
    public void updategetOpinion(ResponseData responseData) {
        if (responseData != null) {
            showToast(responseData.getMsg());
            if (responseData.getCode().equals("200")) {
                finish();
            }
        }
    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端意见反馈页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端意见反馈页面");
        MobclickAgent.onPause(this);
    }
}
