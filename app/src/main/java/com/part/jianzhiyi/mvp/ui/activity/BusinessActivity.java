package com.part.jianzhiyi.mvp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.mvp.contract.BusinessContract;
import com.part.jianzhiyi.mvp.presenter.BusinessPresenter;
import com.umeng.analytics.MobclickAgent;

public class BusinessActivity extends BaseActivity<BusinessPresenter> implements BusinessContract.IBusinessView{

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_business;
    }

    @Override
    protected void initView() {
        initToolbar("商务合作");

    }

    @Override
    protected void initData() {
        mPresenter.getTitle();
    }

    @Override
    protected BusinessPresenter createPresenter() {
        return new BusinessPresenter(this);
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void noData() {

    }

    @Override
    public void startIntent() {

    }

    @Override
    public void requestError() {

    }

    @Override
    public void updateContract(String text) {
        ((TextView) findViewById(R.id.tv_contract)).setText("QQ客服：" + text);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商务合作页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商务合作页面");
        MobclickAgent.onPause(this);
    }
}
