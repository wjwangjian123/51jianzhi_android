package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.MyitemEntity;
import com.part.jianzhiyi.model.entity.ResumeEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.mvp.contract.mine.MineUpdateResumeContract;
import com.part.jianzhiyi.mvp.presenter.mine.MineUpdateResumePresenter;
import com.umeng.analytics.MobclickAgent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AboutMineActivity extends BaseActivity<MineUpdateResumePresenter> implements MineUpdateResumeContract.IMineUpdateResumeView {

    private TextView mAboutTvSkip;
    private ImageView mAboutIvReturn;
    private TagFlowLayout mAboutTfl;
    private TextView mAboutTvGoon;
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
    private List<MyitemEntity.DataBean> mList;
    private UserInfoEntity mUserInfoEntity;
    private int type = 0;
    private String myitemStr;


    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.userInfo(PreferenceUUID.getInstence().getUserId());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_about_mine;
    }

    @Override
    protected void initView() {
        mAboutTvSkip = (TextView) findViewById(R.id.about_tv_skip);
        mAboutIvReturn = (ImageView) findViewById(R.id.about_iv_return);
        mAboutTfl = (TagFlowLayout) findViewById(R.id.about_tfl);
        mAboutTvGoon = (TextView) findViewById(R.id.about_tv_goon);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.about_rl_title));
        mList = new ArrayList<>();
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            //普通跳转
            mAboutTvSkip.setVisibility(View.VISIBLE);
            mAboutIvReturn.setVisibility(View.GONE);
        } else if (type == 1) {
            //从简历页面进入
            mAboutTvSkip.setVisibility(View.GONE);
            mAboutIvReturn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAboutIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        boolean userLogin = PreferenceUUID.getInstence().isUserLogin();
        mAboutTvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getaddMd("10");
                MobclickAgent.onEvent(AboutMineActivity.this, "about_skip");
                if (!userLogin) {
                    Intent intent = new Intent(AboutMineActivity.this, LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("ToLogin", 1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AboutMineActivity.this, MainActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                }
                finish();
            }
        });
        mAboutTvGoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(AboutMineActivity.this, "about_submit");
                mPresenter.getaddMd("9");
                //选择优点
                Set<Integer> selectedList = mAboutTfl.getSelectedList();
                if (selectedList.size() > 0) {
                    StringBuffer stringBuffer = new StringBuffer();
                    StringBuffer stringBuffer1 = new StringBuffer();
                    for (Integer str : selectedList) {
                        stringBuffer = stringBuffer.append(mList.get(str).getId() + ",");
                        stringBuffer1 = stringBuffer1.append(mList.get(str).getItem() + ",");
                    }
                    myitem = String.valueOf(stringBuffer);
                    myitemStr = String.valueOf(stringBuffer1);
                } else {
                    showToast("请选择你的优点");
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
        MobclickAgent.onEvent(AboutMineActivity.this, "status_submit_successful");
        boolean userLogin = PreferenceUUID.getInstence().isUserLogin();
        if (type == 0) {
            if (!userLogin) {
                Intent intent = new Intent(AboutMineActivity.this, LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ToLogin", 1);
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (mUserInfoEntity.getData().getExpect().size() == 0) {
                Intent intent = new Intent(AboutMineActivity.this, ExpectPositionActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            } else {
                Intent intent = new Intent(AboutMineActivity.this, MainActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        } else if (type == 1) {
            Intent intent = new Intent();
            intent.putExtra("myitem", myitem);
            intent.putExtra("myitemStr", myitemStr);
            setResult(1000, intent);
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
            if (entity.getData().getMyitem().size() > 0) {
                StringBuffer stringBuffer = new StringBuffer();
                StringBuffer stringBuffer1 = new StringBuffer();
                for (int i = 0; i < entity.getData().getMyitem().size(); i++) {
                    stringBuffer = stringBuffer.append(entity.getData().getMyitem().get(i).getId() + ",");
                    stringBuffer1 = stringBuffer1.append(entity.getData().getMyitem().get(i).getItem() + "、");
                }
                myitem = String.valueOf(stringBuffer);
                myitemStr = String.valueOf(stringBuffer1);
            }
            if (entity.getData().getExpect().size() > 0) {
                StringBuffer stringBuffer1 = new StringBuffer();
                for (int i = 0; i < entity.getData().getExpect().size(); i++) {
                    stringBuffer1 = stringBuffer1.append(entity.getData().getExpect().get(i).getId() + ",");
                }
                expect = String.valueOf(stringBuffer1);
            }
            mPresenter.getMyitem("1");
        }
    }

    @Override
    public void updategetMyitem(MyitemEntity myitemEntity) {
        if (mList != null) {
            mList.clear();
        }
        if (myitemEntity != null) {
            mList.addAll(myitemEntity.getData());
            mAboutTfl.setAdapter(new TagAdapter<MyitemEntity.DataBean>(mList) {
                @Override
                public View getView(FlowLayout parent, int position, MyitemEntity.DataBean dataBean) {
                    TextView tv = (TextView) LayoutInflater.from(AboutMineActivity.this).inflate(R.layout.item_about_flow, ((TagFlowLayout) mAboutTfl), false);
                    tv.setText(dataBean.getItem());
                    return tv;
                }
            });
        }
    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }

    @Override
    public void startIntent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("关于我页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("关于我页面");
        MobclickAgent.onPause(this);
    }

}
