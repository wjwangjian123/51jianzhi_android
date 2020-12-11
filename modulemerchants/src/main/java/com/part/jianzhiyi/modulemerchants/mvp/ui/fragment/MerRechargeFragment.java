package com.part.jianzhiyi.modulemerchants.mvp.ui.fragment;

import android.view.View;

import com.part.jianzhiyi.corecommon.ui.ListViewInScrollView;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.adapter.MerRechargeAdapter;
import com.part.jianzhiyi.modulemerchants.base.BaseFragment;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MMinMoneyEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MMyWalletEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MZfbPayEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MerWalletContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MerWalletPresenter;
import com.part.jianzhiyi.modulemerchants.mvp.ui.activity.MerMyWalletActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyx on 2020/11/23
 *
 * @author jyx
 * @describe 充值页面
 */
public class MerRechargeFragment extends BaseFragment<MerWalletPresenter> implements MerWalletContract.IMerWalletView {

    private ListViewInScrollView mListRecharge;
    private List<MMyWalletEntity.DataBean.SubListBean> mlist;
    private MerRechargeAdapter mMerRechargeAdapter;
    private int page = 1;

    @Override
    protected MerWalletPresenter createPresenter() {
        return new MerWalletPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        MobclickAgent.onPageStart("商户端充值页面");
        MobclickAgent.onResume(getActivity());
        mPresenter.getMyMoney("", "", "2", page);
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            if (mPresenter != null && mMerRechargeAdapter != null) {
//                page = 1;
//                mPresenter.getMyMoney("", "", "2", page);
//            }
//        }
//    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mListRecharge = view.findViewById(R.id.list_recharge);
        setToolbarVisible(false);
        mlist = new ArrayList<>();
        mMerRechargeAdapter = new MerRechargeAdapter(getActivity(), mlist);
        mListRecharge.setAdapter(mMerRechargeAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mer_recharge;
    }

    @Override
    protected void afterCreate() {

    }

    @Override
    protected void setListener() {
        super.setListener();
        MerMyWalletActivity merMyWalletActivity = new MerMyWalletActivity();
        merMyWalletActivity.setmOnRechargeClickListener(new MerMyWalletActivity.OnRechargeClickListener() {
            @Override
            public void OnRechargeClick(int type) {
                if (type == 1) {
                    //下拉刷新
                    page = 1;
                    mPresenter.getMyMoney("", "", "2", page);
                } else if (type == 2) {
                    //上拉加载更多
                    ++page;
                    mPresenter.getMyMoney("", "", "2", page);
                }
            }
        });
    }

    @Override
    public void updategetMinMoney(MMinMoneyEntity mMinMoneyEntity) {

    }

    @Override
    public void updategetMyMoney(MMyWalletEntity mMyWalletEntity) {
        if (page == 1) {
            mlist.clear();
        }
        if (mMyWalletEntity != null && mMyWalletEntity.getData() != null) {
            if (mMyWalletEntity.getData().getSubList().size() > 0) {
                mlist.addAll(mMyWalletEntity.getData().getSubList());
            }
            mMerRechargeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updategetOrder(MZfbPayEntity mZfbPayEntity) {

    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端充值页面");
        MobclickAgent.onPause(getActivity());
    }
}
