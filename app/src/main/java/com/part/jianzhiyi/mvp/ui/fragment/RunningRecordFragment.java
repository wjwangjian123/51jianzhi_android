package com.part.jianzhiyi.mvp.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseFragment;
import com.part.jianzhiyi.model.entity.moku.MokuBillListEntity;
import com.part.jianzhiyi.model.entity.moku.TxLogEntity;
import com.part.jianzhiyi.model.entity.moku.WalletEntity;
import com.part.jianzhiyi.mogu.adapter.WalletListAdapter;
import com.part.jianzhiyi.mvp.contract.moku.MineWalletContract;
import com.part.jianzhiyi.mvp.presenter.moku.MineWalletPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by jyx on 2020/11/10
 *
 * @author jyx
 * @describe
 */
public class RunningRecordFragment extends BaseFragment<MineWalletPresenter> implements MineWalletContract.IMineWalletView {

    private ListView mRecordRecycle;
    private SmartRefreshLayout mRecordSmartRefresh;
    private ImageView mRecordIvEmpty;
    private WalletListAdapter adapter;
    private List<MokuBillListEntity.DataBean> mlist;

    @Override
    protected MineWalletPresenter createPresenter() {
        return new MineWalletPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_runningrecord;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mRecordRecycle = view.findViewById(R.id.record_recycle);
        mRecordSmartRefresh = view.findViewById(R.id.record_smartRefresh);
        mRecordIvEmpty = view.findViewById(R.id.record_iv_empty);

        setToolbarVisible(false);
        mlist = new ArrayList<>();
        adapter = new WalletListAdapter(getActivity(), mlist);
        mRecordRecycle.setAdapter(adapter);

        mRecordSmartRefresh.setEnableNestedScroll(true);//是否启用嵌套滚动
        mRecordSmartRefresh.setEnableOverScrollBounce(true);//是否启用越界回弹
        mRecordSmartRefresh.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
        mRecordSmartRefresh.setEnableRefresh(true);
        mRecordSmartRefresh.setEnableLoadMore(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getLiushuiList("1");
    }

    @Override
    protected void afterCreate() {

    }

    @Override
    protected void setListener() {
        super.setListener();
        mRecordSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRecordSmartRefresh.finishRefresh(2000);
                mPresenter.getLiushuiList("1");
            }
        });
        mRecordSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mRecordSmartRefresh.finishLoadMore(2000);
            }
        });
    }

    @Override
    public void updategetLiushuiList(MokuBillListEntity responseData) {
        this.mlist.clear();
        if (responseData.getData() != null) {
            if (responseData.getData().size() > 0) {
                mRecordRecycle.setVisibility(View.VISIBLE);
                mRecordSmartRefresh.setVisibility(View.VISIBLE);
                mRecordIvEmpty.setVisibility(View.GONE);
                mlist.addAll(responseData.getData());
            } else {
                mRecordRecycle.setVisibility(View.GONE);
                mRecordSmartRefresh.setVisibility(View.GONE);
                mRecordIvEmpty.setVisibility(View.VISIBLE);
            }
        } else {
            mRecordRecycle.setVisibility(View.GONE);
            mRecordSmartRefresh.setVisibility(View.GONE);
            mRecordIvEmpty.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updategetMyMoney(WalletEntity responseData) {

    }

    @Override
    public void updategetTxLog(TxLogEntity txLogEntity) {

    }
}
