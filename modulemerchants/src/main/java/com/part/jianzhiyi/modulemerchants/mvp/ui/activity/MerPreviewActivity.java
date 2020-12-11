package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.part.jianzhiyi.corecommon.utils.UiUtils;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableContactEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableSalaryEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MPublishContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MPublishPresenter;
import com.umeng.analytics.MobclickAgent;

public class MerPreviewActivity extends BaseActivity<MPublishPresenter> implements MPublishContract.IMPublishView {

    private TextView mTvReturn;
    private TextView mTvJobTitle;
    private TextView mTvMethod;
    private TextView mTvTime;
    private TextView mTvSex;
    private TextView mTvPrice1;
    private TextView mTvPrice2;
    private TextView mTvCompany;
    private ImageView mIvContract;
    private TextView mTvContract;
    private WebView mWebView;
    private TextView mTvConfirm;
    private String label_id;
    private String job_id;
    private String title;
    private String content;
    private String num;
    private String method;
    private String price1;
    private String price2;
    private String time;
    private String sex;
    private String contact;
    private String company;
    private int contact_type;
    private String place;
    private String duration;
    private String contact1;
    private String age1;
    private String age2;
    private String id;
    private int type = 0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getMerUserinfo();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_preview;
    }

    @Override
    protected void initView() {
        mTvReturn = (TextView) findViewById(R.id.tv_return);
        mTvJobTitle = (TextView) findViewById(R.id.tv_job_title);
        mTvMethod = (TextView) findViewById(R.id.tv_method);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvSex = (TextView) findViewById(R.id.tv_sex);
        mTvPrice1 = (TextView) findViewById(R.id.tv_price1);
        mTvPrice2 = (TextView) findViewById(R.id.tv_price2);
        mTvCompany = (TextView) findViewById(R.id.tv_company);
        mIvContract = (ImageView) findViewById(R.id.iv_contract);
        mTvContract = (TextView) findViewById(R.id.tv_contract);
        mWebView = (WebView) findViewById(R.id.webView);
        mTvConfirm = (TextView) findViewById(R.id.tv_confirm);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.rl_title));
    }

    @Override
    protected void initData() {
        label_id = getIntent().getStringExtra("label_id");
        job_id = getIntent().getStringExtra("job_id");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        num = getIntent().getStringExtra("num");
        method = getIntent().getStringExtra("method");
        price1 = getIntent().getStringExtra("price1");
        price2 = getIntent().getStringExtra("price2");
        time = getIntent().getStringExtra("time");
        sex = getIntent().getStringExtra("sex");
        contact = getIntent().getStringExtra("contact");
        contact_type = getIntent().getIntExtra("contact_type", 0);
        place = getIntent().getStringExtra("place");
        duration = getIntent().getStringExtra("duration");
        contact1 = getIntent().getStringExtra("contact1");
        age1 = getIntent().getStringExtra("age1");
        age2 = getIntent().getStringExtra("age2");
        id = getIntent().getStringExtra("id");
        type = getIntent().getIntExtra("type", 0);

        mTvJobTitle.setText(title);
        mTvMethod.setText(method);
        mTvTime.setText(time);
        mTvSex.setText(sex);
        mTvPrice1.setText(price1);
        mTvPrice2.setText(price2);
        mTvContract.setText(contact1);
        String name = "icon_detail" + contact_type;
        int imageResId = UiUtils.getImageResId(this, name);
        mIvContract.setImageResource(imageResId);
        if (content != null) {
            mWebView.loadData(content, "text/html; charset=UTF-8", null);
        }
    }

    private long firstTime = 0;
    @Override
    protected void setListener() {
        super.setListener();
        mTvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(MerPreviewActivity.this, "mer_preview");
                if (System.currentTimeMillis() - firstTime > 3000) {
                    firstTime = System.currentTimeMillis();
                    mPresenter.getmdAdd("73");
                    //确认发布
                    if (type == 0) {
                        mPresenter.getAddJob(title, method, time, sex, price1, content, contact, contact_type, num, place, duration, "1", "", label_id, price2, age1, age2);
                    } else if (type == 1) {
                        mPresenter.getAddJob(title, method, time, sex, price1, content, contact, contact_type, num, place, duration, "2", id, label_id, price2, age1, age2);
                    }
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    @Override
    protected MPublishPresenter createPresenter() {
        return new MPublishPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    private long clickTime = 0;
    /**
     * 发布成功提示
     */
    private void initDialogTip(String mTip) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MerPreviewActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(MerPreviewActivity.this).inflate(R.layout.dialog_tip_publish_success, null, false);
        TextView tip = inflate.findViewById(R.id.tv_tip);
        ImageView cancel = inflate.findViewById(R.id.iv_cancel);
        alertDialog.setView(inflate);
        tip.setText(mTip);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        //显示
        alertDialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    Intent intent = new Intent(MerPreviewActivity.this, MerMainActivity.class);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                    finish();
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    @Override
    public void updategetMLabel(MLableEntity mLableEntity) {

    }

    @Override
    public void updategetMLabelMethod(MLableSalaryEntity mLableSalaryEntity) {

    }

    @Override
    public void updategetMLabelSalary(MLableSalaryEntity mLableSalaryEntity) {

    }

    @Override
    public void updategetMLabelContact(MLableContactEntity mLableContactEntity) {

    }

    @Override
    public void updategetIsSing(ResponseData responseData) {

    }

    @Override
    public void updategetCheckJob(ResponseData responseData) {

    }

    @Override
    public void updategetAddJob(ResponseData responseData) {
        if (responseData != null) {
            if (responseData.getCode().equals("200")) {
                initDialogTip(responseData.getMsg());
            } else {
                showToast(responseData.getMsg());
            }
        }
    }

    @Override
    public void updategetMerUserinfo(MUserInfoEntity mUserInfoEntity) {
        if (mUserInfoEntity != null) {
            mTvCompany.setText(mUserInfoEntity.getUserinfo().getCompany());
        }
    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端预览职位页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端预览职位页面");
        MobclickAgent.onPause(this);
    }
}
