package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.ChoiceSpecialMoreAdapter;
import com.part.jianzhiyi.adapter.SearchViewListAdapter;
import com.part.jianzhiyi.adapter.SearchVocationListAdapter;
import com.part.jianzhiyi.adapter.VocationListAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.customview.LoadDataTipView;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.model.entity.ViewedEntity;
import com.part.jianzhiyi.mvp.contract.ChoiceContract;
import com.part.jianzhiyi.mvp.presenter.JobListPresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class VocationListActivity extends BaseActivity<JobListPresenter> implements ChoiceContract.IChoiceView {


    private List<JobListResponseEntity> oldList = new ArrayList<>();
    private List<ViewedEntity.DataBean> mBeanList = new ArrayList<>();
    private List<JobListResponseEntity2.DataBean> list = new ArrayList<>();
    private ListView listView;
    private VocationListAdapter adapter;
    private ChoiceSpecialMoreAdapter choiceAdapter2;
    private String type;
    private String postion;
    private boolean isShowTitle;
    private boolean isChoice = false;
    private boolean isSpecialChoice = false;
    private SearchVocationListAdapter oldAdapter;
    private SearchViewListAdapter mSearchViewListAdapter;
    private String title;
    private boolean isOld;

    @Override
    protected void init() {
        super.init();
        // 2——高薪招聘 3——极速上网  4——工作邀请 225——已报名 226——已录取 227——已到岗 228-被查看 8——已完成 6-精选列表
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        switch (type) {
            case Constants.TYPE_HIGH_PAY:
                isShowTitle = true;
                postion = Constants.POSITION_HIGH_PAY;

                break;
            case Constants.TYPE_SPEED_WORK:
                isShowTitle = true;
                postion = Constants.POSITION_SPEED_WORK;
                break;
            case Constants.TYPE_CHOICE:
                isShowTitle = true;
                isChoice = true;
                postion = Constants.POSITION_CHOICE;
                break;
            case Constants.TYPE_CHOICE_SPECIAL:
                isShowTitle = true;
                isSpecialChoice = true;
                postion = Constants.POSITION_CHOICE;
                break;
        }
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_vocation_list;
    }

    @Override
    protected void initView() {
        listView = findViewById(R.id.list_view);
        initToolbar(title);
//        initToolbar(isShowTitle ? isSpecialChoice?"精选推荐":isChoice?"小编推荐":"职位列表" : "");

        if (isShowTitle) {
            if (isChoice || isSpecialChoice) {
                listView.setDivider(getResources().getDrawable(R.color.transparency));
                listView.setDividerHeight(0);
                choiceAdapter2 = new ChoiceSpecialMoreAdapter(this, list);
                listView.setAdapter(choiceAdapter2);
            } else {
                adapter = new VocationListAdapter(this, list);
                listView.setAdapter(adapter);
            }
        } else {
            oldAdapter = new SearchVocationListAdapter(this, oldList);
            mSearchViewListAdapter = new SearchViewListAdapter(this, mBeanList);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                if (type.equals("228")) {
                    Intent intent = new Intent(VocationListActivity.this, VocationActivity.class);
                    intent.putExtra("id", isOld ? mBeanList.get(pos).getId() : list.get(pos).getId());
                    intent.putExtra("position", postion);
                    intent.putExtra("sortId", "" + pos);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(VocationListActivity.this, VocationActivity.class);
                    intent.putExtra("id", isOld ? oldList.get(pos).getId() : list.get(pos).getId());
                    intent.putExtra("position", postion);
                    intent.putExtra("sortId", "" + pos);
                    startActivity(intent);
                }
            }
        });


    }


    @Override
    protected void initData() {
        switch (type) {
            case "224":
                mPresenter.inviteJob();
                break;
            case "225":
                mPresenter.joinedJob();
                break;
            case "226":
                mPresenter.approvedJob();
                break;
            case "227":
                mPresenter.arrivedJob();
                break;
            case "228":
                mPresenter.viewedJob();
                break;
            case "8":
                mPresenter.donedJob();
                break;
            default:
                mPresenter.jobList(type, postion, Constants.PAGE_INDEX);
        }
    }

    @Override
    protected JobListPresenter createPresenter() {
        return new JobListPresenter(this);
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updateList(List<JobListResponseEntity> list) {
        isOld = true;
        this.oldList.clear();
        listView.setAdapter(oldAdapter);
        this.oldList.addAll(list);
        oldAdapter.notifyDataSetChanged();
        if (this.oldList.isEmpty()) {
            setLoadMode(LoadDataTipView.MODE.NODATA);
        }
    }

    @Override
    public void updateviewedJob(ViewedEntity list) {
        isOld = true;
        this.oldList.clear();
        this.mBeanList.clear();
        listView.setAdapter(mSearchViewListAdapter);
        this.mBeanList.addAll(list.getData());
        mSearchViewListAdapter.notifyDataSetChanged();
        if (this.mBeanList.isEmpty()) {
            setLoadMode(LoadDataTipView.MODE.NODATA);
        }
    }

    @Override
    public void updateNewList(String position, List<JobListResponseEntity2.DataBean> dataBeanList) {
        this.list.clear();
        this.list.addAll(dataBeanList);
        if (isChoice || isSpecialChoice) {
            choiceAdapter2.notifyDataSetChanged();
        } else {
            adapter.notifyDataSetChanged();
        }
        if (this.list.isEmpty()) {
            setLoadMode(LoadDataTipView.MODE.NODATA);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(title + "页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(title + "页面");
        MobclickAgent.onPause(this);
    }
}