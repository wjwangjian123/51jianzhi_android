package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.customview.NoScrollViewPager;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MMinMoneyEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MMyWalletEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MZfbPayEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MerWalletContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MerWalletPresenter;
import com.part.jianzhiyi.modulemerchants.mvp.ui.fragment.MerConsumeFragment;
import com.part.jianzhiyi.modulemerchants.mvp.ui.fragment.MerRechargeFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

@Route(path = "/merchants/activity/mywallet")
public class MerMyWalletActivity extends BaseActivity<MerWalletPresenter> implements MerWalletContract.IMerWalletView, View.OnClickListener {

    private TextView mWalletTvBond;
    private TextView mWalletTvMoney;
    private TextView mWalletTvRecharge;
    private TextView mTvConsume;
    private ImageView mIvConsume;
    private LinearLayout mWalletLlConsume;
    private TextView mTvRecharge;
    private ImageView mIvRecharge;
    private LinearLayout mWalletLlRecharge;
    private NoScrollViewPager mWalletViewpager;
    private SmartRefreshLayout mSmartRefresh;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_my_wallet;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.getMyMoney("", "", "1", 1);
        }
        MobclickAgent.onPageStart("商户端钱包页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void initView() {
        mWalletTvBond = (TextView) findViewById(R.id.wallet_tv_bond);
        mWalletTvMoney = (TextView) findViewById(R.id.wallet_tv_money);
        mWalletTvRecharge = (TextView) findViewById(R.id.wallet_tv_recharge);
        mTvConsume = (TextView) findViewById(R.id.tv_consume);
        mIvConsume = (ImageView) findViewById(R.id.iv_consume);
        mWalletLlConsume = (LinearLayout) findViewById(R.id.wallet_ll_consume);
        mTvRecharge = (TextView) findViewById(R.id.tv_recharge);
        mIvRecharge = (ImageView) findViewById(R.id.iv_recharge);
        mWalletLlRecharge = (LinearLayout) findViewById(R.id.wallet_ll_recharge);
        mWalletViewpager = (NoScrollViewPager) findViewById(R.id.wallet_viewpager);
        mSmartRefresh = findViewById(R.id.smartRefresh);
        initToolbar("我的钱包");
    }

    @Override
    protected void initData() {
        mTvConsume.setSelected(true);
        mTvRecharge.setSelected(false);
        mIvConsume.setVisibility(View.VISIBLE);
        mIvRecharge.setVisibility(View.INVISIBLE);
        mSmartRefresh.setEnableNestedScroll(true);//是否启用嵌套滚动
        mSmartRefresh.setEnableOverScrollBounce(true);//是否启用越界回弹
        mSmartRefresh.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
        mSmartRefresh.setEnableRefresh(true);
        mSmartRefresh.setEnableLoadMore(true);
        initViewPager();
    }

    private void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new MerConsumeFragment());
        fragments.add(new MerRechargeFragment());
        mWalletViewpager.setCurrentItem(0);
        mWalletViewpager.setNoScroll(false);
        mWalletViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    @Override
    protected MerWalletPresenter createPresenter() {
        return new MerWalletPresenter(this);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mWalletTvRecharge.setOnClickListener(this);
        mWalletLlConsume.setOnClickListener(this);
        mWalletLlRecharge.setOnClickListener(this);
        mWalletViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mTvConsume.setSelected(true);
                    mTvRecharge.setSelected(false);
                    mIvConsume.setVisibility(View.VISIBLE);
                    mIvRecharge.setVisibility(View.INVISIBLE);
                } else if (position == 1) {
                    mTvConsume.setSelected(false);
                    mTvRecharge.setSelected(true);
                    mIvConsume.setVisibility(View.INVISIBLE);
                    mIvRecharge.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mSmartRefresh.finishRefresh(2000);
                mPresenter.getMyMoney("", "", "1", 1);
                if (mTvConsume.isSelected()) {
                    if (mOnConsumeClickListener != null) {
                        mOnConsumeClickListener.OnConsumeClick(1);
                    }
                } else if (mTvRecharge.isSelected()) {
                    if (mOnRechargeClickListener != null) {
                        mOnRechargeClickListener.OnRechargeClick(1);
                    }
                }
            }
        });
        mSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mSmartRefresh.finishLoadMore(2000);
                if (mTvConsume.isSelected()) {
                    if (mOnConsumeClickListener != null) {
                        mOnConsumeClickListener.OnConsumeClick(2);
                    }
                } else if (mTvRecharge.isSelected()) {
                    if (mOnRechargeClickListener != null) {
                        mOnRechargeClickListener.OnRechargeClick(2);
                    }
                }
            }
        });
    }

    private static OnConsumeClickListener mOnConsumeClickListener;
    private static OnRechargeClickListener mOnRechargeClickListener;

    public void setmOnConsumeClickListener(OnConsumeClickListener mOnConsumeClickListener) {
        MerMyWalletActivity.mOnConsumeClickListener = mOnConsumeClickListener;
    }

    public void setmOnRechargeClickListener(OnRechargeClickListener mOnRechargeClickListener) {
        MerMyWalletActivity.mOnRechargeClickListener = mOnRechargeClickListener;
    }

    public interface OnConsumeClickListener {
        void OnConsumeClick(int type);
    }

    public interface OnRechargeClickListener {
        void OnRechargeClick(int type);
    }

    private long clickTime = 0;
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.wallet_tv_recharge) {
            MobclickAgent.onEvent(MerMyWalletActivity.this, "mer_wallet_recharge");
            if (System.currentTimeMillis() - clickTime > 3000) {
                clickTime = System.currentTimeMillis();
                Intent intent = new Intent(MerMyWalletActivity.this, MerPayActivity.class);
                startActivityForResult(intent, 1001);
            }else {
                showToast("点击过于频繁请稍后再试");
            }
        } else if (v.getId() == R.id.wallet_ll_consume) {
            MobclickAgent.onEvent(MerMyWalletActivity.this, "mer_wallet_consume");
            mTvConsume.setSelected(true);
            mTvRecharge.setSelected(false);
            mIvConsume.setVisibility(View.VISIBLE);
            mIvRecharge.setVisibility(View.INVISIBLE);
            mWalletViewpager.setCurrentItem(0);
        } else if (v.getId() == R.id.wallet_ll_recharge) {
            MobclickAgent.onEvent(MerMyWalletActivity.this, "mer_wallet_recharge_list");
            mTvConsume.setSelected(false);
            mTvRecharge.setSelected(true);
            mIvConsume.setVisibility(View.INVISIBLE);
            mIvRecharge.setVisibility(View.VISIBLE);
            mWalletViewpager.setCurrentItem(1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 1000) {
            mWalletViewpager.setCurrentItem(1);
        }
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updategetMinMoney(MMinMoneyEntity mMinMoneyEntity) {

    }

    @Override
    public void updategetMyMoney(MMyWalletEntity mMyWalletEntity) {
        if (mMyWalletEntity.getData() != null) {
            mWalletTvBond.setText(mMyWalletEntity.getData().getBusInfo().getSecurity());
            mWalletTvMoney.setText(mMyWalletEntity.getData().getBusInfo().getMoney());
            if (mMyWalletEntity.getData().getBusInfo().getIs_pay() == 1) {
                mWalletTvRecharge.setVisibility(View.VISIBLE);
            } else if (mMyWalletEntity.getData().getBusInfo().getIs_pay() == 2) {
                mWalletTvRecharge.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void updategetOrder(MZfbPayEntity mZfbPayEntity) {

    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端钱包页面");
        MobclickAgent.onPause(this);
    }
}
