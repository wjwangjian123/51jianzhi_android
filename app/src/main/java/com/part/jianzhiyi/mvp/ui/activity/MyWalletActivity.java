package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.moku.MokuBillListEntity;
import com.part.jianzhiyi.model.entity.moku.TxLogEntity;
import com.part.jianzhiyi.model.entity.moku.WalletEntity;
import com.part.jianzhiyi.mvp.contract.moku.MineWalletContract;
import com.part.jianzhiyi.mvp.presenter.moku.MineWalletPresenter;
import com.part.jianzhiyi.mvp.ui.fragment.RunningRecordFragment;
import com.part.jianzhiyi.mvp.ui.fragment.TxLogFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MyWalletActivity extends BaseActivity<MineWalletPresenter> implements MineWalletContract.IMineWalletView, View.OnClickListener {

    private TextView mWalletTvYue;
    private TextView mWalletTvMoney;
    private TextView mWalletTvLeiji;
    private TextView mWalletTvCumulative;
    private TextView mWalletTvTixian;
    private ImageView mWalletIvAll;
    private LinearLayout mWalletLinearAll;
    private ImageView mWalletIvTixian;
    private LinearLayout mWalletLinearTixian;
    private ViewPager mWalletViewpager;
    private int type = 0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getMyMoney();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected void initView() {
        mWalletTvYue = findViewById(R.id.wallet_tv_yue);
        mWalletTvMoney = findViewById(R.id.wallet_tv_money);
        mWalletTvLeiji = findViewById(R.id.wallet_tv_leiji);
        mWalletTvCumulative = findViewById(R.id.wallet_tv_Cumulative);
        mWalletTvTixian = findViewById(R.id.wallet_tv_tixian);
        mWalletIvAll = findViewById(R.id.wallet_iv_all);
        mWalletLinearAll = findViewById(R.id.wallet_linear_all);
        mWalletIvTixian = findViewById(R.id.wallet_iv_tixian);
        mWalletLinearTixian = findViewById(R.id.wallet_linear_tixian);
        mWalletViewpager = findViewById(R.id.wallet_viewpager);
        initToolbar("我的钱包");
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 0);
        initViewPager();
    }

    private void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new RunningRecordFragment());
        fragments.add(new TxLogFragment());
        if (type == 0) {
            mWalletViewpager.setCurrentItem(0);
        } else if (type == 1) {
            mWalletViewpager.setCurrentItem(1);
        }
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
    protected MineWalletPresenter createPresenter() {
        return new MineWalletPresenter(this);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mWalletTvTixian.setOnClickListener(this);
        mWalletLinearAll.setOnClickListener(this);
        mWalletLinearTixian.setOnClickListener(this);
        mWalletViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mWalletIvAll.setVisibility(View.VISIBLE);
                    mWalletIvTixian.setVisibility(View.INVISIBLE);
                } else if (position == 1) {
                    mWalletIvAll.setVisibility(View.INVISIBLE);
                    mWalletIvTixian.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.wallet_tv_tixian) {
            MobclickAgent.onEvent(MyWalletActivity.this, "moku_wallet_tixian");
            Intent intent = new Intent(MyWalletActivity.this, WithdrawalActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.wallet_linear_all) {
            mWalletIvAll.setVisibility(View.VISIBLE);
            mWalletIvTixian.setVisibility(View.INVISIBLE);
            mWalletViewpager.setCurrentItem(0);
        } else if (v.getId() == R.id.wallet_linear_tixian) {
            mWalletIvAll.setVisibility(View.INVISIBLE);
            mWalletIvTixian.setVisibility(View.VISIBLE);
            mWalletViewpager.setCurrentItem(1);
        }
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updategetLiushuiList(MokuBillListEntity responseData) {

    }

    @Override
    public void updategetMyMoney(WalletEntity responseData) {
        if (responseData.getData() != null) {
            mWalletTvMoney.setText(responseData.getData().getMoney());
            mWalletTvCumulative.setText(responseData.getData().getAllmoney());
        }
    }

    @Override
    public void updategetTxLog(TxLogEntity txLogEntity) {

    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent != null) {
            type = getIntent().getIntExtra("type", 0);
            if (type == 0) {
                mWalletViewpager.setCurrentItem(0);
            } else if (type == 1) {
                mWalletViewpager.setCurrentItem(1);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mPresenter != null) {
            mPresenter.getMyMoney();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("我的钱包");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("我的钱包");
        MobclickAgent.onPause(this);
    }
}
