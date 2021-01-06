package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.adapter.MerCompanyAdapter;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.constants.Constants;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MCompanyInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MCompanyContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MCompanyPresenter;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/merchants/activity/mercompanyinfo")
public class MerCompanyInfoActivity extends BaseActivity<MCompanyPresenter> implements MCompanyContract.IMCompanyView {

    private TextView mTvName;
    private TextView mTvPosition;
    private TextView mTvType;
    private TextView mTvStatus;
    private TextView mTvDate;
    private TextView mTvAddress;
    private TextView mTvId;
    private TextView mTvRange;
    private RelativeLayout mRlIntro;
    private TextView mTvContent;
    private View mViewIntro;
    private TextView mTvDizhi;
    private ListViewInScrollView mListPosition;
    private String uid;
    private String job_id;
    private int is_company = 0;
    private List<MCompanyInfoEntity.JobListBean> mList;
    private MerCompanyAdapter mMerCompanyAdapter;
    private TextView mTvIntro;
    private RelativeLayout mRlInfo;
    private View mViewInfo;
    private RelativeLayout mRlAddress;
    private View mViewAddress;
    private TextView mTvZhiwei;
    private RelativeLayout mRlZhiwei;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_company_info;
    }

    @Override
    protected void initView() {
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvPosition = (TextView) findViewById(R.id.tv_position);
        mTvType = (TextView) findViewById(R.id.tv_type);
        mTvStatus = (TextView) findViewById(R.id.tv_status);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mTvId = (TextView) findViewById(R.id.tv_id);
        mTvRange = (TextView) findViewById(R.id.tv_range);
        mRlIntro = (RelativeLayout) findViewById(R.id.rl_intro);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mViewIntro = (View) findViewById(R.id.view_intro);
        mTvDizhi = (TextView) findViewById(R.id.tv_dizhi);
        mListPosition = (ListViewInScrollView) findViewById(R.id.list_position);
        mTvIntro = (TextView) findViewById(R.id.tv_intro);
        mRlInfo = (RelativeLayout) findViewById(R.id.rl_info);
        mViewInfo = (View) findViewById(R.id.view_info);
        mRlAddress = (RelativeLayout) findViewById(R.id.rl_address);
        mViewAddress = (View) findViewById(R.id.view_address);
        mTvZhiwei = (TextView) findViewById(R.id.tv_zhiwei);
        mRlZhiwei = (RelativeLayout) findViewById(R.id.rl_zhiwei);
        initToolbar("公司详情");
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        uid = getIntent().getStringExtra("uid");
        job_id = getIntent().getStringExtra("job_id");
        is_company = getIntent().getIntExtra("is_company", 0);
        mPresenter.getCompanyInfo(uid, job_id);
        mMerCompanyAdapter = new MerCompanyAdapter(MerCompanyInfoActivity.this, mList);
        mListPosition.setAdapter(mMerCompanyAdapter);
        if (is_company == 1) {
            mTvIntro.setText("公司介绍");
            mTvZhiwei.setText("公司在招职位");
            mRlInfo.setVisibility(View.VISIBLE);
            mViewInfo.setVisibility(View.VISIBLE);
            mRlAddress.setVisibility(View.VISIBLE);
            mViewAddress.setVisibility(View.VISIBLE);
        } else if (is_company == 2) {
            mTvIntro.setText("公司介绍");
            mTvZhiwei.setText("在招职位");
            mRlInfo.setVisibility(View.GONE);
            mViewInfo.setVisibility(View.GONE);
            mRlAddress.setVisibility(View.GONE);
            mViewAddress.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        mListPosition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ARouter.getInstance().build("/app/activity/vocation").withString("id", mList.get(position).getId()).withString("position", Constants.POSITION_COMPANYINFO).withString("sortId", "" + position).navigation();
            }
        });
    }

    @Override
    protected MCompanyPresenter createPresenter() {
        return new MCompanyPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updategetCompanyInfo(MCompanyInfoEntity mCompanyInfoEntity) {
        this.mList.clear();
        if (mCompanyInfoEntity != null) {
            if (mCompanyInfoEntity.getCode().equals("200")) {
                if (mCompanyInfoEntity.getData() != null) {
                    mTvName.setText(mCompanyInfoEntity.getData().getCompany());
                    mTvPosition.setText(mCompanyInfoEntity.getData().getJob_count());
                    mTvType.setText(mCompanyInfoEntity.getData().getType());
                    mTvStatus.setText(mCompanyInfoEntity.getData().getStatus());
                    mTvDate.setText(mCompanyInfoEntity.getData().getCreate_time());
                    mTvAddress.setText(mCompanyInfoEntity.getData().getAddress());
                    mTvId.setText(mCompanyInfoEntity.getData().getCompany_num());
                    mTvRange.setText(mCompanyInfoEntity.getData().getScope());
                    mTvDizhi.setText(mCompanyInfoEntity.getData().getAddress());
                    if (mCompanyInfoEntity.getData().getCompany_desc() != null && mCompanyInfoEntity.getData().getCompany_desc() != "") {
                        mRlIntro.setVisibility(View.VISIBLE);
                        mViewIntro.setVisibility(View.VISIBLE);
                        mTvContent.setText(mCompanyInfoEntity.getData().getCompany_desc());
                    } else {
                        mRlIntro.setVisibility(View.GONE);
                        mViewIntro.setVisibility(View.GONE);
                    }
                }
                if (mCompanyInfoEntity.getJob_list().size() > 0) {
                    mRlZhiwei.setVisibility(View.VISIBLE);
                    mList.addAll(mCompanyInfoEntity.getJob_list());
                    mMerCompanyAdapter.notifyDataSetChanged();
                } else {
                    mRlZhiwei.setVisibility(View.GONE);
                }
            } else {
                showToast(mCompanyInfoEntity.getMsg());
            }
        }
    }

    @Override
    public void updategetIntroduced(ResponseData responseData) {

    }

}
