package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.ExpectPositionAdapter;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.MyitemEntity;
import com.part.jianzhiyi.model.entity.ResumeEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.mvp.contract.mine.MineUpdateResumeContract;
import com.part.jianzhiyi.mvp.presenter.mine.MineUpdateResumePresenter;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.umeng.analytics.MobclickAgent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExpectPositionActivity extends BaseActivity<MineUpdateResumePresenter> implements MineUpdateResumeContract.IMineUpdateResumeView {

    private TextView mExpectTvSkip;
    private RelativeLayout mExpectRlTitle;
    private RecyclerView mExpectRecycle;
    private TextView mExpectTvGoon;
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
    private List<MyitemEntity.DataBean> mList;
    private ExpectPositionAdapter mExpectPositionAdapter;
    private Set<Integer> selectedList;
    private StringBuffer stringBuffer = new StringBuffer();

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.userInfo(PreferenceUUID.getInstence().getUserId());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_expect;
    }

    @Override
    protected void initView() {
        mExpectTvSkip = (TextView) findViewById(R.id.expect_tv_skip);
        mExpectRlTitle = (RelativeLayout) findViewById(R.id.expect_rl_title);
        mExpectRecycle = (RecyclerView) findViewById(R.id.expect_recycle);
        mExpectTvGoon = (TextView) findViewById(R.id.expect_tv_goon);
        setToolBarVisible(false);
        setImmerseLayout(mExpectRlTitle);
        mList = new ArrayList<>();
        selectedList = new HashSet<>();
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ExpectPositionActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mExpectRecycle.setLayoutManager(linearLayoutManager);
        mExpectPositionAdapter = new ExpectPositionAdapter(ExpectPositionActivity.this);
        mExpectRecycle.setAdapter(mExpectPositionAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        boolean userLogin = PreferenceUUID.getInstence().isUserLogin();
        mExpectTvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(ExpectPositionActivity.this, "status_skip");
                if (!userLogin) {
                    Intent intent = new Intent(ExpectPositionActivity.this, LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("ToLogin", 1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ExpectPositionActivity.this, MainActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                }
                finish();
            }
        });
        mExpectPositionAdapter.setmOnItemClickListener(new ExpectPositionAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, int mposition, String id) {
                if (mList.get(position).getChildren().get(mposition).getIvType() == 0) {
                    mList.get(position).getChildren().get(mposition).setIvType(1);
                } else if (mList.get(position).getChildren().get(mposition).getIvType() == 1) {
                    mList.get(position).getChildren().get(mposition).setIvType(0);
                }
                mExpectPositionAdapter.notifyDataSetChanged();
            }
        });
        mExpectTvGoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringBuffer.delete(0,stringBuffer.length());
                stringBuffer.setLength(0);
                for (int i = 0; i < mList.size(); i++) {
                    for (int j = 0; j <mList.get(i).getChildren().size() ; j++) {
                        if (mList.get(i).getChildren().get(j).getIvType() == 1) {
                            stringBuffer = stringBuffer.append(mList.get(i).getChildren().get(j).getId() + ",");
                        }
                    }
                }
                //选择职位
                expect = String.valueOf(stringBuffer);
                if (expect == null || expect == ""||expect.equals("")) {
                    showToast("请选择期望职位");
                    return;
                }
                List<String> mlist = new ArrayList<>();
                if (expect != null && expect != "") {
                    String str = expect.replace(" ", "");
                    mlist = Arrays.asList(str.split(","));
                }

                if (mlist.size() > 3) {
                    showToast("最多选择三个");
                    return;
                }
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
    public void updateSuccess() {

    }

    @Override
    public void updateUserInfo(LoginResponseEntity entity) {

    }

    @Override
    public void updateupdateResumeV2(ResumeEntity resumeEntity) {
        MobclickAgent.onEvent(ExpectPositionActivity.this, "status_submit_successful");
        boolean userLogin = PreferenceUUID.getInstence().isUserLogin();
        if (!userLogin) {
            Intent intent = new Intent(ExpectPositionActivity.this, LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ToLogin", 1);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Intent intent = new Intent(ExpectPositionActivity.this, MainActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void updateUserInfoPer(UserInfoEntity entity) {
        mUserInfoEntity = entity;
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
            if (entity.getData().getMyitem() != null) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < entity.getData().getMyitem().size(); i++) {
                    stringBuffer = stringBuffer.append(entity.getData().getMyitem().get(i).getId() + ",");
                }
                myitem = String.valueOf(stringBuffer);
            }
            if (entity.getData().getExpect() != null) {
                StringBuffer stringBuffer1 = new StringBuffer();
                for (int i = 0; i < entity.getData().getExpect().size(); i++) {
                    stringBuffer1 = stringBuffer1.append(entity.getData().getExpect().get(i).getId() + ",");
                }
                expect = String.valueOf(stringBuffer1);
            }
            mPresenter.getMyitem("2");
        }
    }

    @Override
    public void updategetMyitem(MyitemEntity myitemEntity) {
        if (mList != null) {
            mList.clear();
        }
        if (myitemEntity.getData().size() > 0) {
            mList.addAll(myitemEntity.getData());
            mExpectPositionAdapter.setList(mList);
        }
    }

    @Override
    public void startIntent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("选择期望职位页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("选择期望职位页面");
        MobclickAgent.onPause(this);
    }
}
