package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.ChoiceRecommendMoreAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.mvp.contract.HomeVocationListContract;
import com.part.jianzhiyi.mvp.presenter.HomeVocationListPresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class ChoiceRecommendListActivity extends BaseActivity<HomeVocationListPresenter> implements HomeVocationListContract.IVocationListView {


    private ListViewInScrollView lvHomeVocation;
    private List<JobListResponseEntity2.DataBean> list = new ArrayList<>();
    private ChoiceRecommendMoreAdapter adapter;

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_choice_recommend_list;
    }

    @Override
    protected void initView() {
        initToolbar("商家推荐");
        lvHomeVocation = findViewById(R.id.lv_recommend_vocation);
        adapter = new ChoiceRecommendMoreAdapter(this, list);
        lvHomeVocation.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        mPresenter.jobList(Constants.TYPE_CHOICE, Constants.POSITION_CHOICE_RECOMMEND, Constants.PAGE_INDEX, "0");
    }

    @Override
    protected void setListener() {
        super.setListener();
        lvHomeVocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChoiceRecommendListActivity.this, VocationActivity.class);
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("position", Constants.POSITION_CHOICE_RECOMMEND);
                intent.putExtra("sortId", "" + position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected HomeVocationListPresenter createPresenter() {
        return new HomeVocationListPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updateNewList(String position, List<JobListResponseEntity2.DataBean> dataBeanList) {
        if (list != null) {
            this.list.clear();
        }
        if (dataBeanList != null) {
            this.list.addAll(dataBeanList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("精选页点击更多后的展示页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("精选页点击更多后的展示页面");
        MobclickAgent.onPause(this);
    }
}
