package com.part.jianzhiyi.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.IntegralListAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.model.entity.integral.ExcitationInfoEntity;
import com.part.jianzhiyi.model.entity.integral.MyIntegralEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.mvp.contract.integral.IntegralContract;
import com.part.jianzhiyi.mvp.presenter.integral.IntegralPresenter;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class IntegralListActivity extends BaseActivity<IntegralPresenter> implements IntegralContract.IntegralView {

    private ImageView mIvReturn;
    private TextView mTvIntegral;
    private ListView mLvIntegralList;
    private SmartRefreshLayout mSmartRefresh;
    private IntegralListAdapter mAdapter;
    private List<MyIntegralEntity.DataBeanX.DataBean> mList;
    private int page = 1;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        if (!PreferenceUUID.getInstence().getUserId().equals(null) && !PreferenceUUID.getInstence().getUserId().equals("")) {
            mPresenter.getMyIntegInfo(PreferenceUUID.getInstence().getUserId(), page);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_integral_list;
    }

    @Override
    protected void initView() {
        mIvReturn = (ImageView) findViewById(R.id.iv_return);
        mTvIntegral = (TextView) findViewById(R.id.tv_integral);
        mLvIntegralList = (ListView) findViewById(R.id.lv_integral_list);
        mSmartRefresh = (SmartRefreshLayout) findViewById(R.id.smartRefresh);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.rl_title));
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mAdapter = new IntegralListAdapter(IntegralListActivity.this, mList);
        mLvIntegralList.setAdapter(mAdapter);
        mSmartRefresh.setEnableNestedScroll(true);//是否启用嵌套滚动
        mSmartRefresh.setEnableOverScrollBounce(true);//是否启用越界回弹
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
        mSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                if (!PreferenceUUID.getInstence().getUserId().equals(null) && !PreferenceUUID.getInstence().getUserId().equals("")) {
                    mPresenter.getMyIntegInfo(PreferenceUUID.getInstence().getUserId(), page);
                }
            }
        });
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                if (!PreferenceUUID.getInstence().getUserId().equals(null) && !PreferenceUUID.getInstence().getUserId().equals("")) {
                    mPresenter.getMyIntegInfo(PreferenceUUID.getInstence().getUserId(), page);
                }
            }
        });
    }

    @Override
    protected IntegralPresenter createPresenter() {
        return new IntegralPresenter(this);
    }

    @Override
    public void updategetMyIntegInfo(MyIntegralEntity entity) {
        mSmartRefresh.finishRefresh();
        mSmartRefresh.finishLoadMore();
        if (entity != null) {
            mTvIntegral.setText(entity.getData().getIntegral());
            if (mList.size() > 0) {
                mSmartRefresh.setEnableAutoLoadMore(true);
            }
            if (page == 1) {
                this.mList.clear();
            }
            if (entity.getData().getData().size() > 0) {
                this.mList.addAll(entity.getData().getData());
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updategetExcitationInfo(ExcitationInfoEntity entity) {

    }

    @Override
    public void startIntent() {

    }
}
