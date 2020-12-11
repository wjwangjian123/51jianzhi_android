package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.customview.NoScrollViewPager;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MMineContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MMinePresenter;
import com.part.jianzhiyi.modulemerchants.mvp.ui.fragment.MerMineFragment;
import com.part.jianzhiyi.modulemerchants.mvp.ui.fragment.MerPositionFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

@Route(path = "/merchants/activity/mermain")
public class MerMainActivity extends BaseActivity<MMinePresenter> implements MMineContract.IMMineView, View.OnClickListener {

    private NoScrollViewPager mMainViewpager;
    private ImageView mIvHome;
    private TextView mTvHome;
    private LinearLayout mMainLlHome;
    private ImageView mIvPublish;
    private TextView mTvPublish;
    private LinearLayout mMainLlPublish;
    private ImageView mIvMine;
    private TextView mTvMine;
    private LinearLayout mMainLlMine;

    private String TAG = "MainActivity";
    private View[] mViews;
    private View[] mViewImgs;
    private int type = 0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_main;
    }

    @Override
    protected void initView() {
        mMainViewpager = (NoScrollViewPager) findViewById(R.id.main_viewpager);
        mIvHome = (ImageView) findViewById(R.id.iv_home);
        mTvHome = (TextView) findViewById(R.id.tv_home);
        mMainLlHome = (LinearLayout) findViewById(R.id.main_ll_home);
        mIvPublish = (ImageView) findViewById(R.id.iv_publish);
        mMainLlPublish = (LinearLayout) findViewById(R.id.main_ll_publish);
        mIvMine = (ImageView) findViewById(R.id.iv_mine);
        mTvMine = (TextView) findViewById(R.id.tv_mine);
        mMainLlMine = (LinearLayout) findViewById(R.id.main_ll_mine);
        setToolBarVisible(false);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mMainLlHome.setOnClickListener(this);
        mMainLlPublish.setOnClickListener(this);
        mMainLlMine.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 0);
        mViews = new View[]{mTvHome, mTvMine};
        mViewImgs = new View[]{mIvHome, mIvMine};
        initViewPager();
    }

    private void initViewPager() {
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new MerPositionFragment());
        fragments.add(new MerMineFragment());
        //设置viewpager的缓存
//        mMainViewpager.setOffscreenPageLimit(3);
        //禁止viewpager左右滑动
        mMainViewpager.setNoScroll(true);
        if (type == 0) {
            mMainViewpager.setCurrentItem(0);
            setTabSelected(0);
        } else if (type == 1) {
            mMainViewpager.setCurrentItem(1);
            setTabSelected(1);
        } else if (type == 2) {
            //显示已发布
            mMainViewpager.setCurrentItem(0);
            setTabSelected(0);
            //传值到首页
            if (mBtnClickListener != null) {
                mBtnClickListener.onTabClick();
            }
        }
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

    private static BtnClickListener mBtnClickListener;

    public static void setmBtnClickListener(BtnClickListener mBtnClickListener) {
        MerMainActivity.mBtnClickListener = mBtnClickListener;
    }

    private long clickTime1 = 0;
    private void initDialogAuthTip(String mtip, int isSing, int cer_status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MerMainActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(MerMainActivity.this).inflate(R.layout.dialog_tip_publish_auth, null, false);
        TextView text = inflate.findViewById(R.id.tv_text);
        TextView tip = inflate.findViewById(R.id.tv_tip);
        TextView auth = inflate.findViewById(R.id.tv_auth);
        ImageView cancel = inflate.findViewById(R.id.iv_cancel);
        alertDialog.setView(inflate);
        text.setText("温馨提示");
        auth.setText("去认证");
        tip.setText(mtip);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        //显示
        alertDialog.show();
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (System.currentTimeMillis() - clickTime1 > 3000) {
                    clickTime1 = System.currentTimeMillis();
                    //跳转个人认证
                    if (isSing == 0) {
                        //未签署进入协议
                        Intent intent = new Intent(MerMainActivity.this, MerAuthHtmlActivity.class);
                        startActivity(intent);
                    } else if (isSing == 1) {
                        Intent intent = new Intent(MerMainActivity.this, MerAuthActivity.class);
                        if (cer_status == 3) {
                            intent.putExtra("urlType", 1);
                        } else {
                            intent.putExtra("urlType", 0);
                        }
                        startActivity(intent);
                    }
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void updategetMerUserinfo(MUserInfoEntity mUserInfoEntity) {
        if (mUserInfoEntity != null) {
            if (mUserInfoEntity.getUserinfo().getJob_add() == 0) {
                showToast(mUserInfoEntity.getUserinfo().getAdd_msg());
            } else if (mUserInfoEntity.getUserinfo().getJob_add() == 1) {
                initDialogAuthTip(mUserInfoEntity.getUserinfo().getAdd_msg(), mUserInfoEntity.getUserinfo().getIs_sing(), mUserInfoEntity.getUserinfo().getCert_status());
            } else if (mUserInfoEntity.getUserinfo().getJob_add() == 2) {
                //跳转到发布职位
                Intent intent = new Intent(MerMainActivity.this, MerSelectPositionActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        }
    }

    @Override
    public void updategetAvatar(ResponseData responseData) {

    }

    @Override
    public void updategetOpinion(ResponseData responseData) {

    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    public interface BtnClickListener {
        void onTabClick();
    }

    @Override
    protected MMinePresenter createPresenter() {
        return new MMinePresenter(this);
    }

    @Override
    public void startIntent() {

    }

    private long clickTime = 0;
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_ll_home) {
            mMainViewpager.setCurrentItem(0);
            setTabSelected(0);
        } else if (v.getId() == R.id.main_ll_publish) {
            //判断是否可发布
            if (System.currentTimeMillis() - clickTime > 3000) {
                clickTime = System.currentTimeMillis();
                mPresenter.getMerUserinfo();
            }else {
                showToast("点击过于频繁请稍后再试");
            }
        } else if (v.getId() == R.id.main_ll_mine) {
            mMainViewpager.setCurrentItem(1);
            setTabSelected(1);
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent != null) {
            type = getIntent().getIntExtra("type", 0);
            if (type == 0) {
                mMainViewpager.setCurrentItem(0);
                setTabSelected(0);
            } else if (type == 1) {
                mMainViewpager.setCurrentItem(1);
                setTabSelected(1);
            } else if (type == 2) {
                //显示已发布
                mMainViewpager.setCurrentItem(0);
                setTabSelected(0);
                //传值到首页
                if (mBtnClickListener != null) {
                    mBtnClickListener.onTabClick();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getSupportFragmentManager().getFragments();
        if (getSupportFragmentManager().getFragments().size() > 0) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment mFragment : fragments) {
                mFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端主页");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端主页");
        MobclickAgent.onPause(this);
    }
}
