package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.umeng.analytics.MobclickAgent;

public class MerPhotoViewActivity extends BaseActivity {

    private ImageView mIvCancel;
    private ImageView mIvPhoto;
    private String imagePath;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_photo_view;
    }

    @Override
    protected void initView() {
        mIvCancel = (ImageView) findViewById(R.id.iv_cancel);
        mIvPhoto = (ImageView) findViewById(R.id.iv_photo);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.iv_cancel));
    }

    @Override
    protected void initData() {
        imagePath = getIntent().getStringExtra("imageUrl");
        if (!imagePath.equals("") && !imagePath.equals(null)) {
            Glide.with(MerPhotoViewActivity.this).load(imagePath).into(mIvPhoto);
        }
        mIvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端查看大图页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端查看大图页面");
        MobclickAgent.onPause(this);
    }
}
