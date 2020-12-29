package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.adapter.GuideAdapter;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import androidx.viewpager.widget.ViewPager;

public class MerGuideActivity extends BaseActivity {

    private ViewPager mGuideViewpager;
    private GuideAdapter mGuideAdapter;
    private int is_sing = 0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_guide;
    }

    @Override
    protected void initView() {
        mGuideViewpager = (ViewPager) findViewById(R.id.guide_viewpager);
        setToolBarVisible(false);
    }

    private long clickTime = 0;

    @Override
    protected void initData() {
        is_sing = getIntent().getIntExtra("is_sing", 0);
        int[] imageRes = new int[]{R.mipmap.icon_mer_yindao1, R.mipmap.icon_mer_yindao2};
        ArrayList<ImageView> mList = new ArrayList<>();
        for (int i = 0; i < imageRes.length; i++) {
            ImageView imageView = new ImageView(MerGuideActivity.this);
            imageView.setImageResource(imageRes[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mList.add(imageView);
        }
        mGuideAdapter = new GuideAdapter();
        mGuideViewpager.setCurrentItem(0);
        mGuideViewpager.setAdapter(mGuideAdapter);
        mGuideAdapter.setData(mList);
        mGuideAdapter.setOnItemClickListener(new GuideAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    mGuideViewpager.setCurrentItem(1);
                }
                if (position == 1) {
                    PreferenceUUID.getInstence().putisMerGuide();
                    if (System.currentTimeMillis() - clickTime > 3000) {
                        clickTime = System.currentTimeMillis();
                        //判断商户是否已经签署协议
                        if (is_sing == 0) {
                            //未签署跳转到认证协议
                            Intent intent = new Intent(MerGuideActivity.this, MerAuthHtmlActivity.class);
                            startActivity(intent);
                        } else {
                            //已签署跳转到商户端主页
                            Intent intent = new Intent(MerGuideActivity.this, MerMainActivity.class);
                            intent.putExtra("type", 0);
                            startActivity(intent);
                        }
                    } else {
                        showToast("点击过于频繁请稍后再试");
                    }
                    //销毁当前activity
                    MerGuideActivity.this.finish();
                }
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void startIntent() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //跳转到商户主页，我的
            Intent intent = new Intent(MerGuideActivity.this, MerMainActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端引导页");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端引导页");
        MobclickAgent.onPause(this);
    }
}
