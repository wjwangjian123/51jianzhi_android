package com.part.jianzhiyi.mvp.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseFragment;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.moku.MokuBillListEntity;
import com.part.jianzhiyi.model.entity.moku.TxLogEntity;
import com.part.jianzhiyi.model.entity.moku.WalletEntity;
import com.part.jianzhiyi.mogu.adapter.TxLogListAdapter;
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
public class TxLogFragment extends BaseFragment<MineWalletPresenter> implements MineWalletContract.IMineWalletView {

    private ListView mTxRecycle;
    private SmartRefreshLayout mTxSmartRefresh;
    private ImageView mTxIvEmpty;
    private TxLogListAdapter txLogListAdapter;
    private List<TxLogEntity.DataBean> mtxLoglist;
    private int type;
    private int tixainPage = 1;
    private int pagetotal = 0;

    @Override
    protected MineWalletPresenter createPresenter() {
        return new MineWalletPresenter(this);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mTxRecycle = view.findViewById(R.id.tx_recycle);
        mTxSmartRefresh = view.findViewById(R.id.tx_smartRefresh);
        mTxIvEmpty = view.findViewById(R.id.tx_iv_empty);
        setToolbarVisible(false);
        mtxLoglist = new ArrayList<>();
        txLogListAdapter = new TxLogListAdapter(getActivity(), mtxLoglist);
        mTxRecycle.setAdapter(txLogListAdapter);
        mTxSmartRefresh.setEnableNestedScroll(true);//是否启用嵌套滚动
        mTxSmartRefresh.setEnableOverScrollBounce(true);//是否启用越界回弹
        mTxSmartRefresh.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
        mTxSmartRefresh.setEnableRefresh(true);
        mTxSmartRefresh.setEnableLoadMore(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_txlog;
    }

    @Override
    public void onResume() {
        super.onResume();
        type = 1;
        tixainPage = 1;
        mPresenter.getTxLog(tixainPage);
    }

    @Override
    protected void afterCreate() {

    }

    @Override
    protected void setListener() {
        super.setListener();
        mTxSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mTxSmartRefresh.finishRefresh(2000);
                type = 1;
                tixainPage = 1;
                mPresenter.getTxLog(tixainPage);
            }
        });
        mTxSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mTxSmartRefresh.finishLoadMore(2000);
                type = 2;
                if (tixainPage < pagetotal) {
                    ++tixainPage;
                    mPresenter.getTxLog(tixainPage);
                }
            }
        });
    }

    @Override
    public void updategetLiushuiList(MokuBillListEntity responseData) {

    }

    @Override
    public void updategetMyMoney(WalletEntity responseData) {

    }

    @Override
    public void updategetTxLog(TxLogEntity txLogEntity) {
        if (type == 1) {
            this.mtxLoglist.clear();
        }
        if (txLogEntity != null) {
            pagetotal = txLogEntity.getMap().getPagetotal();
            if (txLogEntity.getData().size() > 0) {
                mTxRecycle.setVisibility(View.VISIBLE);
                mTxSmartRefresh.setVisibility(View.VISIBLE);
                mTxIvEmpty.setVisibility(View.GONE);
                mtxLoglist.addAll(txLogEntity.getData());
            } else {
                mTxRecycle.setVisibility(View.GONE);
                mTxSmartRefresh.setVisibility(View.GONE);
                mTxIvEmpty.setVisibility(View.VISIBLE);
            }
        } else {
            mTxRecycle.setVisibility(View.GONE);
            mTxSmartRefresh.setVisibility(View.GONE);
            mTxIvEmpty.setVisibility(View.VISIBLE);
        }
        txLogListAdapter.notifyDataSetChanged();
    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }
}
