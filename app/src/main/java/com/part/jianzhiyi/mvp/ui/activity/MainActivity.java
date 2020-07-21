package com.part.jianzhiyi.mvp.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.customview.NoScrollViewPager;
import com.part.jianzhiyi.mvp.ui.fragment.ChoiceFragment;
import com.part.jianzhiyi.mvp.ui.fragment.HomeFragment;
import com.part.jianzhiyi.mvp.ui.fragment.InformationFragment;
import com.part.jianzhiyi.mvp.ui.fragment.MineFragment;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

@Route(path = "/app/activity/main")
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private NoScrollViewPager mMainViewpager;
    private ImageView mMainIvHome;
    private TextView mMainTvHome;
    private LinearLayout mMainLinearHome;
    private ImageView mMainIvChoice;
    private TextView mMainTvChoice;
    private LinearLayout mMainLinearChoice;
    private ImageView mMainIvMessage;
    private TextView mMainTvMessage;
    private LinearLayout mMainLinearMessage;
    private ImageView mMainIvMine;
    private TextView mMainTvMine;
    private LinearLayout mMainLinearMine;

    private String TAG = "MainActivity";
    private View[] mViews;
    private View[] mViewImgs;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mMainViewpager = (NoScrollViewPager) findViewById(R.id.main_viewpager);
        mMainIvHome = (ImageView) findViewById(R.id.main_iv_home);
        mMainTvHome = (TextView) findViewById(R.id.main_tv_home);
        mMainLinearHome = (LinearLayout) findViewById(R.id.main_linear_home);
        mMainIvChoice = (ImageView) findViewById(R.id.main_iv_choice);
        mMainTvChoice = (TextView) findViewById(R.id.main_tv_choice);
        mMainLinearChoice = (LinearLayout) findViewById(R.id.main_linear_choice);
        mMainIvMessage = (ImageView) findViewById(R.id.main_iv_message);
        mMainTvMessage = (TextView) findViewById(R.id.main_tv_message);
        mMainLinearMessage = (LinearLayout) findViewById(R.id.main_linear_message);
        mMainIvMine = (ImageView) findViewById(R.id.main_iv_mine);
        mMainTvMine = (TextView) findViewById(R.id.main_tv_mine);
        mMainLinearMine = (LinearLayout) findViewById(R.id.main_linear_mine);
        mMainLinearHome.setOnClickListener(this);
        mMainLinearChoice.setOnClickListener(this);
        mMainLinearMessage.setOnClickListener(this);
        mMainLinearMine.setOnClickListener(this);
        setToolBarVisible(false);
        mViews = new View[]{mMainTvHome, mMainTvChoice, mMainTvMessage,mMainTvMine};
        mViewImgs = new View[]{mMainIvHome, mMainIvChoice, mMainIvMessage,mMainIvMine};
        setTabSelected(0);
        initViewPager();
    }

    private void initViewPager() {
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ChoiceFragment());
        fragments.add(new InformationFragment());
        fragments.add(new MineFragment());
        //设置viewpager的缓存
        mMainViewpager.setOffscreenPageLimit(3);
        //禁止viewpager左右滑动
        mMainViewpager.setNoScroll(true);
        mMainViewpager.setCurrentItem(0);
        mMainViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_linear_home) {
            mMainViewpager.setCurrentItem(0);
            setTabSelected(0);
        } else if (v.getId() == R.id.main_linear_choice) {
            mMainViewpager.setCurrentItem(1);
            setTabSelected(1);
        } else if (v.getId() == R.id.main_linear_message) {
            mMainViewpager.setCurrentItem(2);
            setTabSelected(2);
        }else if (v.getId() == R.id.main_linear_mine) {
            mMainViewpager.setCurrentItem(3);
            setTabSelected(3);
        }
    }

    private void setTabSelected(int position) {
        int count = mViews.length;
        for (int i = 0; i < count; i++) {
            if (i == position) {
                mViews[i].setSelected(true);
                mViewImgs[i].setSelected(true);
            } else {
                mViews[i].setSelected(false);
                mViewImgs[i].setSelected(false);
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void startIntent() {

    }

    /**
     * 记录用户首次点击返回键的时间
     */
    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                showToast("再按一次退出应用");
                firstTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
