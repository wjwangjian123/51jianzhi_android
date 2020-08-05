package com.part.jianzhiyi.mvp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.ActionListener;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.BaseDialogFragment;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.TextPickerDialog;
import com.part.jianzhiyi.corecommon.selectdateview.view.TextModel;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.MyitemEntity;
import com.part.jianzhiyi.model.entity.ResumeEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.mvp.contract.mine.MineUpdateResumeContract;
import com.part.jianzhiyi.mvp.presenter.mine.MineUpdateResumePresenter;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/app/activity/resume")
public class ResumeActivity extends BaseActivity<MineUpdateResumePresenter> implements MineUpdateResumeContract.IMineUpdateResumeView {

    private TextView mResumeTvSkip;
    private ImageView mResumeIvReturn;
    private RadioButton mResumeRbBoy;
    private RadioButton mResumeRbGirl;
    private RadioGroup mResumeRgSex;
    private RadioButton mResumeRbStudent;
    private RadioButton mResumeRbOffice;
    private RadioButton mResumeRbFree;
    private RadioGroup mResumeRgIdentity;
    private LinearLayout mResumeLinearYear;
    private TextView mResumeTvStart;
    private List<TextModel> list_age;
    private TextPickerDialog pickDialog;
    private TextView mResumeTvYear;
    private int toResume = 1;
    private int errorType = 1;
    private String nickname;
    private String sex;
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
        return R.layout.activity_resume;
    }

    @Override
    protected void initView() {
        mResumeTvSkip = (TextView) findViewById(R.id.resume_tv_skip);
        mResumeIvReturn = (ImageView) findViewById(R.id.resume_iv_return);
        mResumeRbBoy = (RadioButton) findViewById(R.id.resume_rb_boy);
        mResumeRbGirl = (RadioButton) findViewById(R.id.resume_rb_girl);
        mResumeRgSex = (RadioGroup) findViewById(R.id.resume_rg_sex);
        mResumeRbStudent = (RadioButton) findViewById(R.id.resume_rb_student);
        mResumeRbOffice = (RadioButton) findViewById(R.id.resume_rb_office);
        mResumeRbFree = (RadioButton) findViewById(R.id.resume_rb_free);
        mResumeRgIdentity = (RadioGroup) findViewById(R.id.resume_rg_identity);
        mResumeLinearYear = (LinearLayout) findViewById(R.id.resume_linear_year);
        mResumeTvStart = (TextView) findViewById(R.id.resume_tv_start);
        mResumeTvYear = (TextView) findViewById(R.id.resume_tv_year);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.resume_rl_title));
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        toResume = extras.getInt("ToResume");
        errorType = extras.getInt("errorType");
        list_age = new ArrayList<>();
        for (int i = 18; i < 61; i++) {
            list_age.add(new TextModel(String.valueOf(i)));
        }
        if (toResume == 1) {
            //普通跳转
            mResumeTvSkip.setVisibility(View.VISIBLE);
            mResumeIvReturn.setVisibility(View.GONE);
        } else if (toResume == 2) {
            //从兼职详情进入页面
            mResumeTvSkip.setVisibility(View.GONE);
            mResumeIvReturn.setVisibility(View.VISIBLE);
        }
        if (errorType == 202) {
            pickDialog = TextPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, MyAction);
            pickDialog.setList(list_age);
            pickDialog.show(getFragmentManager(), "dialog");
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        mResumeIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        boolean userLogin = PreferenceUUID.getInstence().isUserLogin();
        mResumeTvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(ResumeActivity.this, "resume_skip");
                if (!userLogin) {
                    Intent intent = new Intent(ResumeActivity.this, LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("ToLogin", 1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ResumeActivity.this, MainActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                }
                finish();
            }
        });
        mResumeTvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mResumeRbBoy.isChecked() && !mResumeRbGirl.isChecked()) {
                    showToast("请选择性别");
                    return;
                }
                if (!mResumeRbStudent.isChecked() && !mResumeRbOffice.isChecked() && !mResumeRbFree.isChecked()) {
                    showToast("请选择身份");
                    return;
                }
                if (mResumeTvYear.getText().toString().equals("请选择您的年龄")) {
                    showToast("请选择年龄");
                    return;
                }
                if (mResumeRbBoy.isChecked()) {
                    sex = mResumeRbBoy.getText().toString();
                } else if (mResumeRbGirl.isChecked()) {
                    sex = mResumeRbGirl.getText().toString();
                }
                if (mResumeRbStudent.isChecked()) {
                    profession = 1;
                    profession1 = mResumeRbStudent.getText().toString();
                } else if (mResumeRbOffice.isChecked()) {
                    profession = 2;
                    profession1 = mResumeRbOffice.getText().toString();
                } else if (mResumeRbFree.isChecked()) {
                    profession = 3;
                    profession1 = mResumeRbFree.getText().toString();
                }
                //更新简历
                mPresenter.updateResumeV2(nickname, sex, mResumeTvYear.getText().toString(), school_year, school_name, experience,
                        introduce, profession, job_status, job_type, myitem, expect,profession1, job_status1, job_type1);
            }
        });
        mResumeLinearYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDialog = TextPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, MyAction);
                pickDialog.setList(list_age);
                pickDialog.show(getFragmentManager(), "dialog");
            }
        });
    }

    ActionListener MyAction = new ActionListener() {
        @Override
        public void onCancelClick(BaseDialogFragment dialog) {

        }

        @Override
        public void onDoneClick(BaseDialogFragment dialog) {
            String content;
            content = pickDialog.getSelectedTitle();
            mResumeTvYear.setText(content);
        }
    };

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
        MobclickAgent.onEvent(ResumeActivity.this, "resume_submit_successful");
        showToast("保存成功");
        if (toResume == 1) {
            if (mUserInfoEntity.getData().getJob_status()==null||mUserInfoEntity.getData().getJob_status()==""||
                    mUserInfoEntity.getData().getJob_type()==null||mUserInfoEntity.getData().getJob_type()==""){
                Intent intent = new Intent(ResumeActivity.this, MyStatusActivity.class);
                startActivity(intent);
            }else if (mUserInfoEntity.getData().getMyitem()==null){
                Intent intent = new Intent(ResumeActivity.this, AboutMineActivity.class);
                startActivity(intent);
            }else if (mUserInfoEntity.getData().getExpect()==null){
                Intent intent = new Intent(ResumeActivity.this, ExpectPositionActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(ResumeActivity.this, MainActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        } else if (toResume == 2) {
            setResult(1000);
        }
        finish();
    }

    @Override
    public void updateUserInfoPer(UserInfoEntity entity) {
        mUserInfoEntity=entity;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer1 = new StringBuffer();
        if (entity != null) {
            nickname = entity.getData().getName();
            sex = entity.getData().getSex();
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
                stringBuffer = stringBuffer.append(entity.getData().getMyitem().get(i).getItem() + ",");
            }
            for (int i = 0; i < entity.getData().getExpect().size(); i++) {
                stringBuffer1 = stringBuffer1.append(entity.getData().getExpect().get(i).getItem() + ",");
            }
            myitem = String.valueOf(stringBuffer);
            expect = String.valueOf(stringBuffer1);
            if (entity.getData().getSex().equals("男")) {
                mResumeRbBoy.setChecked(true);
            } else if (entity.getData().getSex().equals("女")) {
                mResumeRbGirl.setChecked(true);
            } else {
                mResumeRbBoy.setChecked(true);
            }
            if (entity.getData().getProfession_type() == 1) {
                mResumeRbStudent.setChecked(true);
            } else if (entity.getData().getProfession_type() == 2) {
                mResumeRbOffice.setChecked(true);
            } else if (entity.getData().getProfession_type() == 3) {
                mResumeRbFree.setChecked(true);
            } else {
                mResumeRbOffice.setChecked(true);
            }
            if (entity.getData().getAge() != null && entity.getData().getAge() != "") {
                mResumeTvYear.setText(entity.getData().getAge());
            }
        }
    }

    @Override
    public void updategetMyitem(MyitemEntity myitemEntity) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("完善基本信息");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("完善基本信息");
        MobclickAgent.onPause(this);
    }
}
