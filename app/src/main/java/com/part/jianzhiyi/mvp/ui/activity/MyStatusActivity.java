package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.MyitemEntity;
import com.part.jianzhiyi.model.entity.ResumeEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.mvp.contract.mine.MineUpdateResumeContract;
import com.part.jianzhiyi.mvp.presenter.mine.MineUpdateResumePresenter;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyStatusActivity extends BaseActivity<MineUpdateResumePresenter> implements MineUpdateResumeContract.IMineUpdateResumeView {

    private TextView mStatusTvSkip;
    private RadioButton mStatusRbPositive;
    private RadioButton mStatusRbSee;
    private RadioGroup mStatusRg;
    private CheckBox mStatusCk1;
    private CheckBox mStatusCk2;
    private CheckBox mStatusCk3;
    private CheckBox mStatusCk4;
    private LinearLayout mStatusLinear;
    private TextView mStatusTvGoon;
    private String nickname;
    private String sex;
    private String age;
    private String school_year;
    private String school_name;
    private String experience;
    private String introduce;
    private int profession;
    private int job_status;
    private String job_type;
    private String myitem;
    private String expect;
    private String profession1;
    private String job_status1;
    private String job_type1;
    private UserInfoEntity mUserInfoEntity;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.userInfo(PreferenceUUID.getInstence().getUserId());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_my_status;
    }

    @Override
    protected void initView() {
        mStatusTvSkip = (TextView) findViewById(R.id.status_tv_skip);
        mStatusRbPositive = (RadioButton) findViewById(R.id.status_rb_positive);
        mStatusRbSee = (RadioButton) findViewById(R.id.status_rb_see);
        mStatusRg = (RadioGroup) findViewById(R.id.status_rg);
        mStatusCk1 = (CheckBox) findViewById(R.id.status_ck1);
        mStatusCk2 = (CheckBox) findViewById(R.id.status_ck2);
        mStatusCk3 = (CheckBox) findViewById(R.id.status_ck3);
        mStatusCk4 = (CheckBox) findViewById(R.id.status_ck4);
        mStatusLinear = (LinearLayout) findViewById(R.id.status_linear);
        mStatusTvGoon = (TextView) findViewById(R.id.status_tv_goon);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.status_rl_title));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        super.setListener();
        boolean userLogin = PreferenceUUID.getInstence().isUserLogin();
        mStatusTvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(MyStatusActivity.this, "status_skip");
                if (!userLogin) {
                    Intent intent = new Intent(MyStatusActivity.this, LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("ToLogin", 1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MyStatusActivity.this, MainActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                }
                finish();
            }
        });
        mStatusTvGoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mStatusRbPositive.isChecked() && !mStatusRbSee.isChecked()) {
                    showToast("请选择求职状态");
                    return;
                }
                if (!mStatusCk1.isChecked() && !mStatusCk2.isChecked() && !mStatusCk3.isChecked() && !mStatusCk4.isChecked()) {
                    showToast("请选择求职类型");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                StringBuffer stringBuffer1 = new StringBuffer();
                if (mStatusRbPositive.isChecked()) {
                    job_status = 1;
                    job_status1 = mStatusRbPositive.getText().toString();
                } else if (mStatusRbSee.isChecked()) {
                    job_status = 2;
                    job_status1 = mStatusRbSee.getText().toString();
                }
                if (mStatusCk1.isChecked()) {
                    stringBuffer = stringBuffer.append(1);
                    stringBuffer1 = stringBuffer1.append(mStatusCk1.getText().toString());
                }
                if (mStatusCk2.isChecked()) {
                    stringBuffer = stringBuffer.append("," + 2);
                    stringBuffer1 = stringBuffer1.append("," + mStatusCk2.getText().toString());
                }
                if (mStatusCk3.isChecked()) {
                    stringBuffer = stringBuffer.append("," + 3);
                    stringBuffer1 = stringBuffer1.append("," + mStatusCk3.getText().toString());
                }
                if (mStatusCk4.isChecked()) {
                    stringBuffer = stringBuffer.append("," + 4);
                    stringBuffer1 = stringBuffer1.append("," + mStatusCk4.getText().toString());
                }
                job_type = String.valueOf(stringBuffer);
                job_type1 = String.valueOf(stringBuffer1);
                //更新简历
                mPresenter.updateResumeV2(nickname, sex, age, school_year, school_name, experience,
                        introduce, profession, job_status, job_type, myitem, expect, profession1, job_status1, job_type1);
            }
        });
    }

    @Override
    protected MineUpdateResumePresenter createPresenter() {
        return new MineUpdateResumePresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updateSuccess() {

    }

    @Override
    public void updateUserInfo(LoginResponseEntity entity) {

    }

    @Override
    public void updateupdateResumeV2(ResumeEntity resumeEntity) {
        MobclickAgent.onEvent(MyStatusActivity.this, "status_submit_successful");
        boolean userLogin = PreferenceUUID.getInstence().isUserLogin();
        if (!userLogin) {
            Intent intent = new Intent(MyStatusActivity.this, LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ToLogin", 1);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (mUserInfoEntity.getData().getMyitem() == null) {
            Intent intent = new Intent(MyStatusActivity.this, AboutMineActivity.class);
            startActivity(intent);
        } else if (mUserInfoEntity.getData().getExpect() == null) {
            Intent intent = new Intent(MyStatusActivity.this, ExpectPositionActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MyStatusActivity.this, MainActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void updateUserInfoPer(UserInfoEntity entity) {
        mUserInfoEntity = entity;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer1 = new StringBuffer();
        if (entity.getData() != null) {
            nickname = entity.getData().getName();
            sex = entity.getData().getSex();
            age = entity.getData().getAge();
            school_year = entity.getData().getSchool_year();
            school_name = entity.getData().getSchool_name();
            experience = entity.getData().getExperience();
            introduce = entity.getData().getIntroduce();
            profession1 = entity.getData().getProfession();
            job_status1 = entity.getData().getJob_status();
            job_type1 = entity.getData().getJob_type();
            profession = entity.getData().getProfession_type();
            job_status = entity.getData().getJob_status_type();
            job_type = entity.getData().getJob_position_type();
            for (int i = 0; i < entity.getData().getMyitem().size(); i++) {
                stringBuffer = stringBuffer.append(entity.getData().getMyitem().get(i).getId() + ",");
            }
            for (int i = 0; i < entity.getData().getExpect().size(); i++) {
                stringBuffer1 = stringBuffer1.append(entity.getData().getExpect().get(i).getId() + ",");
            }
            myitem = String.valueOf(stringBuffer);
            expect = String.valueOf(stringBuffer1);
            if (entity.getData().getJob_status_type() == 1) {
                mStatusRbPositive.setChecked(true);
            } else if (entity.getData().getJob_status_type() == 2) {
                mStatusRbSee.setChecked(true);
            } else {
                mStatusRbPositive.setChecked(true);
            }
            //截取字符串
            if (job_type != null && job_type != "") {
                String str = job_type.replace(" ", "");
                List<String> mlist = new ArrayList<>();
                mlist = Arrays.asList(str.split(","));
                for (int i = 0; i < mlist.size(); i++) {
                    if (mlist.get(i).equals("1")) {
                        mStatusCk1.setChecked(true);
                    } else if (mlist.get(i).equals("2")) {
                        mStatusCk2.setChecked(true);
                    } else if (mlist.get(i).equals("3")) {
                        mStatusCk3.setChecked(true);
                    } else if (mlist.get(i).equals("4")) {
                        mStatusCk4.setChecked(true);
                    } else {
                        mStatusCk1.setChecked(true);
                    }
                }
            }
        }
    }

    @Override
    public void updategetMyitem(MyitemEntity myitemEntity) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("我的状态页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("我的状态页面");
        MobclickAgent.onPause(this);
    }
}
