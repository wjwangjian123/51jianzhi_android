package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

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

    private ImageView mRankIvReturn;
    private ListViewInScrollView mLvRank;
    private RankAdapter adpater;
    private List<JobListResponseEntity2.DataBean> rankList;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getRankList(Constants.TYPE_RANK, Constants.POSITION_RANK,"0");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_rank;
    }

    @Override
    protected void initView() {
        mRankIvReturn = (ImageView) findViewById(R.id.rank_iv_return);
        mLvRank = (ListViewInScrollView) findViewById(R.id.lv_rank);

        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.rank_rl_title));

        rankList = new ArrayList<>();
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
        mRankIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLvRank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToDetail(rankList.get(position).getId(), position);
            }
        });
    }

    @Override
    public void updateRank(List<JobListResponseEntity2.DataBean> list) {
        if (list == null || list.size() == 0) {
            setLoadMode(LoadDataTipView.MODE.NODATA);
            return;
        }
        if (rankList != null) {
            this.rankList.clear();
        }
        if (list != null) {
            rankList.addAll(list);
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
