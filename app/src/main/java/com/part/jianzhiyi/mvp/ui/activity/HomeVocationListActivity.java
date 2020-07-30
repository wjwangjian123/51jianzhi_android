package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.HomeVocationAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.corecommon.utils.SharedPrefUtils;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.mvp.contract.HomeVocationListContract;
import com.part.jianzhiyi.mvp.presenter.HomeVocationListPresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/app/activity/homevocationlist")
public class HomeVocationListActivity extends BaseActivity<HomeVocationListPresenter> implements HomeVocationListContract.IVocationListView {

    private String type;
    private String category;
    private String postion;
    private ImageView ivHomeVaction;
    private ListViewInScrollView lvHomeVocation;
    private List<JobListResponseEntity2.DataBean> list = new ArrayList<>();
    private HomeVocationAdapter adapter;
    private String title;

    @Override
    protected void init() {
        super.init();
        type = getIntent().getStringExtra("type");
        postion = getIntent().getStringExtra("position");
        title = getIntent().getStringExtra("title");
        category = getIntent().getStringExtra("category");
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home_vocation_list;
    }

    @Override
    protected void initView() {
        initToolbar(title, R.color.color_F3A922, R.color.color_ffffff, R.drawable.ic_white_back);
        if (isMaxVersion()) {
            addStatusViewWithColor(this, getResources().getColor(R.color.color_F3A922));
        }
        ivHomeVaction = findViewById(R.id.iv_home_vaction);
        lvHomeVocation = findViewById(R.id.lv_home_vocation);
        lvHomeVocation = findViewById(R.id.lv_home_vocation);
        adapter = new HomeVocationAdapter(this, list);
        lvHomeVocation.setAdapter(adapter);
    }


    @Override
    protected void initData() {
        switch (type) {
            case Constants.TYPE_HIGH_PAY:
//                postion = Constants.POSITION_HIGH_PAY;
                ivHomeVaction.setImageResource(R.drawable.ic_salary_title_bg);
                break;
            case Constants.TYPE_SPEED_WORK:
//                postion = Constants.POSITION_SPEED_WORK;
                ivHomeVaction.setImageResource(R.drawable.ic_speeds_title_bg);
                break;
        }
        mPresenter.jobList(type, postion, Constants.PAGE_INDEX, category);
    }

    @Override
    protected void setListener() {
        super.setListener();
        lvHomeVocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                if (type.equals(SharedPrefUtils.getString(HomeVocationListActivity.this, "type_one", ""))) {
                    MobclickAgent.onEvent(HomeVocationListActivity.this, "home_play");
                } else {
                    MobclickAgent.onEvent(HomeVocationListActivity.this, "home_fast");
                }
                Intent intent = new Intent(HomeVocationListActivity.this, VocationActivity.class);
                intent.putExtra("id", list.get(pos).getId());
                intent.putExtra("position", postion);
                intent.putExtra("sortId", "" + pos);
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
        this.list.addAll(dataBeanList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("首页点击热门类型跳转的页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("首页点击热门类型跳转的页面");
        MobclickAgent.onPause(this);
    }
}

