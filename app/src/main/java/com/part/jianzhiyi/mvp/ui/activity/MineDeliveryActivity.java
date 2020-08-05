package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.MineDeliveryAdapter;
import com.part.jianzhiyi.adapter.MineDeliveryViewedAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.ViewedEntity;
import com.part.jianzhiyi.mvp.contract.ChoiceContract;
import com.part.jianzhiyi.mvp.presenter.JobListPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MineDeliveryActivity extends BaseActivity<JobListPresenter> implements ChoiceContract.IChoiceView {

    private RadioButton mDeliveryRbSee;
    private RadioButton mDeliveryRbJoin;
    private RadioButton mDeliveryRbApproved;
    private RadioButton mDeliveryRbDoned;
    private RadioGroup mDeliveryRg;
    private RecyclerView mDeliveryRecycle;
    private SmartRefreshLayout mDeliverySmartRefresh;
    private LinearLayout mDeliveryNodata;
    private MineDeliveryAdapter mMineDeliveryAdapter;
    private MineDeliveryViewedAdapter mMineDeliveryViewedAdapter;
    private List<JobListResponseEntity> mList;
    private List<ViewedEntity.DataBean> mBeanList;
    private String postion;
    private int type = 1;
    private int positionType = 1;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mine_delivery;
    }

    @Override
    protected void initView() {
        mDeliveryRbSee = (RadioButton) findViewById(R.id.delivery_rb_see);
        mDeliveryRbJoin = (RadioButton) findViewById(R.id.delivery_rb_join);
        mDeliveryRbApproved = (RadioButton) findViewById(R.id.delivery_rb_approved);
        mDeliveryRbDoned = (RadioButton) findViewById(R.id.delivery_rb_doned);
        mDeliveryRg = (RadioGroup) findViewById(R.id.delivery_rg);
        mDeliveryRecycle = (RecyclerView) findViewById(R.id.delivery_recycle);
        mDeliverySmartRefresh = (SmartRefreshLayout) findViewById(R.id.delivery_smartRefresh);
        mDeliveryNodata = (LinearLayout) findViewById(R.id.delivery_nodata);
        initToolbar("我的投递");

        Intent intent = getIntent();
        if (intent != null) {
            positionType = intent.getIntExtra("positionType", 0);
            if (positionType==0){
                if (intent.getData() != null) {
                    String type = intent.getData().getQueryParameter("type");
                    if (!type.equals(null)){
                        if (type.equals("6")){
                            positionType=2;
                        }
                    }
//                    Log.d("aaaaaaaaaaaaa", intent.getData().toString() + "," + type);
                }
            }
        }
        if (positionType == 1) {
            type = 1;
            mDeliveryRbSee.setChecked(true);
            //调用被查看接口
            mPresenter.viewedJob();
        } else if (positionType == 2) {
            type = 2;
            mDeliveryRbJoin.setChecked(true);
            //已报名
            mPresenter.joinedJob();
        } else if (positionType == 3) {
            type = 3;
            mDeliveryRbApproved.setChecked(true);
            //已录取
            mPresenter.approvedJob();
        } else if (positionType == 4) {
            type = 4;
            mDeliveryRbDoned.setChecked(true);
            //已完成
            mPresenter.donedJob();
        }
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MineDeliveryActivity.this);
        mDeliveryRecycle.setLayoutManager(linearLayoutManager);
        mList = new ArrayList<>();
        mBeanList = new ArrayList<>();
        mMineDeliveryAdapter = new MineDeliveryAdapter(MineDeliveryActivity.this);
        mMineDeliveryViewedAdapter = new MineDeliveryViewedAdapter(MineDeliveryActivity.this);
        mDeliverySmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mDeliverySmartRefresh.finishRefresh(2000);
                if (type == 1) {
                    //被查看
                    mPresenter.viewedJob();
                } else if (type == 2) {
                    //已报名
                    mPresenter.joinedJob();
                } else if (type == 3) {
                    //已录取
                    mPresenter.approvedJob();
                } else if (type == 4) {
                    //已完成
                    mPresenter.donedJob();
                }
            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();
        mMineDeliveryAdapter.setmOnItemClickListener(new MineDeliveryAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, String id) {
                Intent intent = new Intent(MineDeliveryActivity.this, VocationActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("position", postion);
                intent.putExtra("sortId", "" + position);
                startActivity(intent);
            }
        });
        mMineDeliveryViewedAdapter.setmOnItemClickListener(new MineDeliveryViewedAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, String id) {
                Intent intent = new Intent(MineDeliveryActivity.this, VocationActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("position", postion);
                intent.putExtra("sortId", "" + position);
                startActivity(intent);
            }
        });
        mDeliveryRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.delivery_rb_see:
                        //被查看
                        type = 1;
                        mPresenter.viewedJob();
                        break;
                    case R.id.delivery_rb_join:
                        //已报名
                        type = 2;
                        mPresenter.joinedJob();
                        break;
                    case R.id.delivery_rb_approved:
                        //已录取
                        type = 3;
                        mPresenter.approvedJob();
                        break;
                    case R.id.delivery_rb_doned:
                        //已完成
                        type = 4;
                        mPresenter.donedJob();
                        break;
                }
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
        if (mList != null) {
            mList.clear();
        }
        if (list.size() > 0) {
            mDeliverySmartRefresh.setVisibility(View.VISIBLE);
            mDeliveryNodata.setVisibility(View.GONE);
            mDeliveryRecycle.setAdapter(mMineDeliveryAdapter);
            mList.addAll(list);
            mMineDeliveryAdapter.setList(mList);
        } else {
            mDeliverySmartRefresh.setVisibility(View.GONE);
            mDeliveryNodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateviewedJob(ViewedEntity list) {
        if (mList != null) {
            mList.clear();
        }
        if (mBeanList != null) {
            mBeanList.clear();
        }
        if (list.getData().size() > 0) {
            mDeliverySmartRefresh.setVisibility(View.VISIBLE);
            mDeliveryNodata.setVisibility(View.GONE);
            mDeliveryRecycle.setAdapter(mMineDeliveryViewedAdapter);
            mBeanList.addAll(list.getData());
            mMineDeliveryViewedAdapter.setList(mBeanList);
        } else {
            mDeliverySmartRefresh.setVisibility(View.GONE);
            mDeliveryNodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("我的投递页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("我的投递页面");
        MobclickAgent.onPause(this);
    }
}
