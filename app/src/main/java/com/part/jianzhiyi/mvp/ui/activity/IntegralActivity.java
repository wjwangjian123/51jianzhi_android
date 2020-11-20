package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.IntegralBannerAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.IntentConstant;
import com.part.jianzhiyi.dialog.SignIntegralDialog;
import com.part.jianzhiyi.model.entity.integral.ExcitationInfoEntity;
import com.part.jianzhiyi.model.entity.integral.MyIntegralEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.mvp.contract.integral.IntegralContract;
import com.part.jianzhiyi.mvp.presenter.integral.IntegralPresenter;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.ArrayList;
import java.util.List;

import recycler.coverflow.RecyclerCoverFlow;

public class IntegralActivity extends BaseActivity<IntegralPresenter> implements IntegralContract.IntegralView {

    private ImageView mIvReturn;
    private TextView mTvSignDays;
    private TextView mTvContent;
    private TextView mTvScore;
    private LinearLayout mLlScore;
    private TextView mTvRecord;
    private TextView mTvBrowseScore;
    private TextView mTvSignup;
    private TextView mTvSignupScore;
    private TextView mTvOptimization;
    private TextView mTvOptimizationScore;
    private RecyclerCoverFlow mRecyclerflow;
    private TextView mTvExchange;
    private RelativeLayout mRlBrowse;
    private RelativeLayout mRlSignup;
    private RelativeLayout mRlOptimization;
    private String url;
    private IntegralBannerAdapter mBannerAdapter;
    private List<String> mList;
    private SignInfoEntity.DataBean mSignInfoEntity;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_integral;
    }

    @Override
    protected void initView() {
        mIvReturn = (ImageView) findViewById(R.id.iv_return);
        mTvSignDays = (TextView) findViewById(R.id.tv_sign_days);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mTvScore = (TextView) findViewById(R.id.tv_score);
        mLlScore = (LinearLayout) findViewById(R.id.ll_score);
        mTvRecord = (TextView) findViewById(R.id.tv_record);
        mTvBrowseScore = (TextView) findViewById(R.id.tv_browse_score);
        mTvSignup = (TextView) findViewById(R.id.tv_signup);
        mTvSignupScore = (TextView) findViewById(R.id.tv_signup_score);
        mTvOptimization = (TextView) findViewById(R.id.tv_optimization);
        mTvOptimizationScore = (TextView) findViewById(R.id.tv_optimization_score);
        mRecyclerflow = (RecyclerCoverFlow) findViewById(R.id.recyclerflow);
        mTvExchange = (TextView) findViewById(R.id.tv_exchange);
        mRlBrowse = (RelativeLayout) findViewById(R.id.rl_browse);
        mRlSignup = (RelativeLayout) findViewById(R.id.rl_signup);
        mRlOptimization = (RelativeLayout) findViewById(R.id.relative_optimization);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.rl_title));
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        Intent intent = getIntent();
        mSignInfoEntity = (SignInfoEntity.DataBean) intent.getSerializableExtra("SignInfoEntity");
        String code = intent.getStringExtra("code");
        mBannerAdapter = new IntegralBannerAdapter(IntegralActivity.this);
        mRecyclerflow.setAdapter(mBannerAdapter);
        if (code.equals("200")) {
            SignIntegralDialog signDialog = new SignIntegralDialog(IntegralActivity.this, mSignInfoEntity);
            signDialog.show();
            Window window = signDialog.getWindow();
            window.setGravity(Gravity.CENTER);
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        mIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLlScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntegralActivity.this, IntegralListActivity.class);
                startActivity(intent);
            }
        });
        mRlBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntegralActivity.this, ActionListActivity.class);
                intent.putExtra("id", "");
                intent.putExtra("type", "1");
                startActivity(intent);
            }
        });
        mRlSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntegralActivity.this, ActionListActivity.class);
                intent.putExtra("id", "");
                intent.putExtra("type", "1");
                startActivity(intent);
            }
        });
        mRlOptimization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntegralActivity.this, ActionListActivity.class);
                intent.putExtra("id", "");
                intent.putExtra("type", "2");
                startActivity(intent);
            }
        });
        mTvExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntegralActivity.this, HtmlIntegralActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }

    @Override
    protected IntegralPresenter createPresenter() {
        return new IntegralPresenter(this);
    }

    @Override
    public void updategetMyIntegInfo(MyIntegralEntity entity) {

    }

    @Override
    public void updategetExcitationInfo(ExcitationInfoEntity entity) {
        if (entity != null) {
            this.mList.clear();
            mTvRecord.setText(entity.getData().getLl());
            mTvBrowseScore.setText("+" + entity.getData().getLl1());
            mTvSignup.setText(entity.getData().getJz());
            mTvSignupScore.setText("+" + entity.getData().getJz1());
            mTvOptimization.setText(entity.getData().getYxjz());
            mTvOptimizationScore.setText("+" + entity.getData().getYxjz1());
            mTvScore.setText(entity.getData().getIntegral() + "");
            url = entity.getData().getWebUrl();
            mTvSignDays.setText("已连续签到" + entity.getData().getQd().getDay() + "天");
            mTvContent.setText(entity.getData().getQd().getCon());
            mList.addAll(entity.getData().getBanner());
            if (mList.size() > 1) {
                mRecyclerflow.smoothScrollToPosition(1);
            }
            mBannerAdapter.setList(mList);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PreferenceUUID.getInstence().getUserId().equals(null) && !PreferenceUUID.getInstence().getUserId().equals("")) {
            mPresenter.getExcitationInfo(PreferenceUUID.getInstence().getUserId());
        }
    }

    @Override
    public void startIntent() {

    }
}
