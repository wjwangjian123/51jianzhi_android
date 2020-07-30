package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.RankAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.customview.LoadDataTipView;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.mvp.contract.RankContract;
import com.part.jianzhiyi.mvp.presenter.RankPresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends BaseActivity<RankPresenter> implements RankContract.IRankView {

    private RelativeLayout mOneRl;
    private RelativeLayout mTwoRl;
    private RelativeLayout mThreeRl;
    private TextView mOneTvTitle;
    private TextView mOneTvMethod;
    private TextView mOneTvTime;
    private TextView mOneTvSex;
    private TextView mOneTvNum;
    private TextView mTwoTvTitle;
    private TextView mTwoTvMethod;
    private TextView mTwoTvTime;
    private TextView mTwoTvSex;
    private TextView mTwoTvNum;
    private TextView mThreeTvTitle;
    private TextView mThreeTvMethod;
    private TextView mThreeTvTime;
    private TextView mThreeTvSex;
    private TextView mThreeTvNum;
    private ListViewInScrollView mLvRank;
    private RankAdapter adpater;
    private List<JobListResponseEntity2.DataBean> rankList;
    private List<JobListResponseEntity2.DataBean> rankList1;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getRankList(Constants.TYPE_RANK, Constants.POSITION_RANK);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_rank;
    }

    @Override
    protected void initView() {
        mOneRl = findViewById(R.id.one_rl);
        mTwoRl = findViewById(R.id.two_rl);
        mThreeRl = findViewById(R.id.three_rl);
        mOneTvTitle = findViewById(R.id.one_tv_title);
        mOneTvMethod = findViewById(R.id.one_tv_method);
        mOneTvTime = findViewById(R.id.one_tv_time);
        mOneTvSex = findViewById(R.id.one_tv_sex);
        mOneTvNum = findViewById(R.id.one_tv_num);
        mTwoTvTitle = findViewById(R.id.two_tv_title);
        mTwoTvMethod = findViewById(R.id.two_tv_method);
        mTwoTvTime = findViewById(R.id.two_tv_time);
        mTwoTvSex = findViewById(R.id.two_tv_sex);
        mTwoTvNum = findViewById(R.id.two_tv_num);
        mThreeTvTitle = findViewById(R.id.three_tv_title);
        mThreeTvMethod = findViewById(R.id.three_tv_method);
        mThreeTvTime = findViewById(R.id.three_tv_time);
        mThreeTvSex = findViewById(R.id.three_tv_sex);
        mThreeTvNum = findViewById(R.id.three_tv_num);
        mLvRank = (ListViewInScrollView) findViewById(R.id.lv_rank);
        initToolbar("排行榜");
        rankList = new ArrayList<>();
        rankList1 = new ArrayList<>();
        adpater = new RankAdapter(this, rankList);
        mLvRank.setAdapter(adpater);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected RankPresenter createPresenter() {
        return new RankPresenter(this);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mLvRank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToDetail(rankList.get(position).getId(), position);
            }
        });
        mTwoRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetail(rankList1.get(0).getId(), 0);
            }
        });
        mOneRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetail(rankList1.get(1).getId(), 1);
            }
        });
        mThreeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetail(rankList1.get(2).getId(), 2);
            }
        });
    }

    @Override
    public void updateRank(List<JobListResponseEntity2.DataBean> list) {
        if (list == null || list.size() == 0) {
            setLoadMode(LoadDataTipView.MODE.NODATA);
            return;
        }
        if (rankList1 != null) {
            this.rankList1.clear();
        }
        if (rankList != null) {
            this.rankList.clear();
        }
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (i < 3) {
                    this.rankList1.add(list.get(i));
                } else {
                    this.rankList.add(list.get(i));
                }
            }
            if (list.size() > 0) {
                mTwoRl.setVisibility(View.VISIBLE);
                JobListResponseEntity2.DataBean weeklyBean = list.get(0);
                mTwoTvTitle.setText(weeklyBean.getTitle());
                mTwoTvMethod.setText(weeklyBean.getMethod());
                if (weeklyBean.getTime() == null || weeklyBean.getTime() == "") {
                    mTwoTvTime.setText("不限");
                } else {
                    mTwoTvTime.setText(weeklyBean.getTime());
                }
                mTwoTvSex.setText(weeklyBean.getSex() == null ? "男女不限" : weeklyBean.getSex());
                mTwoTvNum.setText("已报名" + weeklyBean.getOrder_number() + "人");
            } else {
                mTwoRl.setVisibility(View.GONE);
            }
            if (list.size() > 1) {
                mOneRl.setVisibility(View.VISIBLE);
                JobListResponseEntity2.DataBean weeklyBean = list.get(1);
                mOneTvTitle.setText(weeklyBean.getTitle());
                mOneTvMethod.setText(weeklyBean.getMethod());
                if (weeklyBean.getTime() == null || weeklyBean.getTime() == "") {
                    mOneTvTime.setText("不限");
                } else {
                    mOneTvTime.setText(weeklyBean.getTime());
                }
                mOneTvSex.setText(weeklyBean.getSex() == null ? "男女不限" : weeklyBean.getSex());
                mOneTvNum.setText("已报名" + weeklyBean.getOrder_number() + "人");
            } else {
                mOneRl.setVisibility(View.GONE);
            }
            if (list.size() > 2) {
                mThreeRl.setVisibility(View.VISIBLE);
                JobListResponseEntity2.DataBean weeklyBean = list.get(2);
                mThreeTvTitle.setText(weeklyBean.getTitle());
                mThreeTvMethod.setText(weeklyBean.getMethod());
                if (weeklyBean.getTime() == null || weeklyBean.getTime() == "") {
                    mThreeTvTime.setText("不限");
                } else {
                    mThreeTvTime.setText(weeklyBean.getTime());
                }
                mThreeTvSex.setText(weeklyBean.getSex() == null ? "男女不限" : weeklyBean.getSex());
                mThreeTvNum.setText("已报名" + weeklyBean.getOrder_number() + "人");
            } else {
                mThreeRl.setVisibility(View.GONE);
            }
        }
        adpater.notifyDataSetChanged();
    }

    private void goToDetail(String id, int position) {
        Intent intent = new Intent(RankActivity.this, VocationActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("position", Constants.POSITION_RANK);
        intent.putExtra("sortId", "" + position);
        startActivity(intent);
    }

    @Override
    public void startIntent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("排行榜");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("排行榜");
        MobclickAgent.onPause(this);
    }
}
