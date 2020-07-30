package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.SeeMineListAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.ViewedEntity;
import com.part.jianzhiyi.mvp.contract.ChoiceContract;
import com.part.jianzhiyi.mvp.presenter.JobListPresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class SeeMineActivity extends BaseActivity<JobListPresenter> implements ChoiceContract.IChoiceView {

    private ListView mInfoSearch;
    private LinearLayout mInfoNodata;
    private SeeMineListAdapter mSeeMineListAdapter;
    private List<ViewedEntity.DataBean> mDataBeanList;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.viewedJob();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_see_mine;
    }

    @Override
    protected void initView() {
        mInfoSearch = (ListView) findViewById(R.id.info_search);
        mInfoNodata = (LinearLayout) findViewById(R.id.info_nodata);
        initToolbar("看过我");
    }

    @Override
    protected void initData() {
        mDataBeanList = new ArrayList<>();
        mSeeMineListAdapter = new SeeMineListAdapter(SeeMineActivity.this, mDataBeanList);
        mInfoSearch.setAdapter(mSeeMineListAdapter);
        mInfoSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MobclickAgent.onEvent(SeeMineActivity.this, "see_mine_list");
                Intent intent = new Intent(SeeMineActivity.this, ChatActivity.class);
                intent.putExtra("sortId", "" + position);
                intent.putExtra("type", 2);
                intent.putExtra("id", mDataBeanList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected JobListPresenter createPresenter() {
        return new JobListPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updateList(List<JobListResponseEntity> list) {

    }

    @Override
    public void updateviewedJob(ViewedEntity list) {
        if (mDataBeanList != null) {
            mDataBeanList.clear();
        }
        if (list.getData().size() > 0) {
            mInfoSearch.setVisibility(View.VISIBLE);
            mInfoNodata.setVisibility(View.GONE);
            mDataBeanList.addAll(list.getData());
            mSeeMineListAdapter.notifyDataSetChanged();
        } else {
            mInfoSearch.setVisibility(View.GONE);
            mInfoNodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("看过我页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("看过我页面");
        MobclickAgent.onPause(this);
    }
}
