package com.part.jianzhiyi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.corecommon.constants.IntentConstant;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.ActionListener;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.BaseDialogFragment;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.DatePickerDialog;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.TextPickerDialog;
import com.part.jianzhiyi.corecommon.selectdateview.view.TextModel;
import com.part.jianzhiyi.corecommon.utils.SoftKeyboardUtils;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.MyitemEntity;
import com.part.jianzhiyi.model.entity.ResumeEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.mvp.contract.mine.MineUpdateResumeContract;
import com.part.jianzhiyi.mvp.presenter.mine.MineUpdateResumePresenter;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MineUpdateResumeActivity extends BaseActivity<MineUpdateResumePresenter> implements MineUpdateResumeContract.IMineUpdateResumeView {

    private TextView mTvUsername;
    private EditText mTvSign;
    private EditText mEtNickname;
    private TextView mTvSex;
    private TextView mTvAge;
    private TextView mTvProfession;
    private TextView mTvJobStatus;
    private TextView mTvJobType;
    private TextView mTvSave;
    private TextView mTvAdvantage;
    private TextView mTvExpected;
    private TextView mTvSchoolYear;
    private EditText mEtSchoolName;
    private EditText mEtExperience;

    private List<TextModel> list;
    private List<TextModel> list_age;
    private List<TextModel> list_profession;
    private List<TextModel> list_job_status;
    private List<TextModel> list_job_type;

    private int profession;
    private int job_status;
    private String job_type;
    private String myitem;
    private String expect;

    @Override
    protected void init() {
        super.init();
        list = new ArrayList<>();
        list_age = new ArrayList<>();
        list_profession = new ArrayList<>();
        list_job_status = new ArrayList<>();
        list_job_type = new ArrayList<>();
        list.add(new TextModel("男"));
        list.add(new TextModel("女"));
        for (int i = 12; i < 61; i++) {
            list_age.add(new TextModel(i + ""));
        }
        list_profession.add(new TextModel("学生"));
        list_profession.add(new TextModel("上班族"));
        list_profession.add(new TextModel("自由职业"));
        list_job_status.add(new TextModel("积极找工作"));
        list_job_status.add(new TextModel("随便看看"));
        list_job_type.add(new TextModel("我想找长期稳定工作"));
        list_job_type.add(new TextModel("我想找短期兼职工作"));
        list_job_type.add(new TextModel("我想在家赚钱"));
        list_job_type.add(new TextModel("什么工作都行"));
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.userInfo(PreferenceUUID.getInstence().getUserId());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mine_update_resume;
    }

    @Override
    protected void initView() {
        mTvUsername = (TextView) findViewById(R.id.tvUsername);
        mTvSign = (EditText) findViewById(R.id.tvSign);
        mEtNickname = (EditText) findViewById(R.id.etNickname);
        mTvSex = (TextView) findViewById(R.id.tvSex);
        mTvAge = (TextView) findViewById(R.id.tvAge);
        mTvProfession = (TextView) findViewById(R.id.tv_profession);
        mTvJobStatus = (TextView) findViewById(R.id.tv_job_status);
        mTvJobType = (TextView) findViewById(R.id.tv_job_type);
        mTvSave = (TextView) findViewById(R.id.tv_save);
        mTvAdvantage = (TextView) findViewById(R.id.tv_advantage);
        mTvExpected = (TextView) findViewById(R.id.tv_expected);
        mTvSchoolYear = (TextView) findViewById(R.id.tv_school_year);
        mEtSchoolName = (EditText) findViewById(R.id.et_school_name);
        mEtExperience = (EditText) findViewById(R.id.et_experience);

        initToolbar("我的简历");
        MobclickAgent.onEvent(this, "resume_in");
        if (SoftKeyboardUtils.isSoftShowing(this)) {
            SoftKeyboardUtils.hideSoftKeyboard(this);
        }
    }

    @Override
    protected void initData() {
        mPresenter.loadUserInfo();
    }

    @Override
    protected MineUpdateResumePresenter createPresenter() {
        return new MineUpdateResumePresenter(this);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mTvSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextPickerDialog pickDialog = TextPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, new MyAction(0));
                pickDialog.setList(list);
                pickDialog.show(getFragmentManager(), "dialog");
            }
        });
        mTvAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextPickerDialog pickDialog = TextPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, new MyAction(2));
                pickDialog.setList(list_age);
                pickDialog.show(getFragmentManager(), "dialog");
            }
        });
        mTvProfession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextPickerDialog pickDialog = TextPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, new MyAction(3));
                pickDialog.setList(list_profession);
                pickDialog.show(getFragmentManager(), "dialog");
            }
        });
        mTvJobStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextPickerDialog pickDialog = TextPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, new MyAction(4));
                pickDialog.setList(list_job_status);
                pickDialog.show(getFragmentManager(), "dialog");
            }
        });
        mTvJobType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextPickerDialog pickDialog = TextPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, new MyAction(5));
                pickDialog.setList(list_job_type);
                pickDialog.show(getFragmentManager(), "dialog");
            }
        });
        mTvAdvantage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到我的优点
                Intent intent = new Intent(MineUpdateResumeActivity.this, AboutMineActivity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, 1001);
            }
        });
        mTvExpected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到期望职位
                Intent intent = new Intent(MineUpdateResumeActivity.this, ExpectPositionActivity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, 1002);
            }
        });
        mTvSchoolYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, new MyAction(6));
                datePickerDialog.show(getFragmentManager(), "dialog");
            }
        });
        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEtNickname.getText().toString())) {
                    showToast("请填写姓名");
                    return;
                }
                if (TextUtils.isEmpty(mTvSex.getText().toString())) {
                    showToast("请选择性别");
                    return;
                }
                if (TextUtils.isEmpty(mTvAge.getText().toString())) {
                    showToast("请选择年龄");
                    return;
                }
                if (TextUtils.isEmpty(mTvProfession.getText().toString())) {
                    showToast("请选择身份");
                    return;
                }
                if (TextUtils.isEmpty(mTvJobStatus.getText().toString())) {
                    showToast("请选择求职状态");
                    return;
                }
                if (TextUtils.isEmpty(mTvJobType.getText().toString())) {
                    showToast("请选择求职类型");
                    return;
                }
                if (TextUtils.isEmpty(mTvAdvantage.getText().toString())) {
                    showToast("请选择您的优点");
                    return;
                }
                if (TextUtils.isEmpty(mTvExpected.getText().toString())) {
                    showToast("请选择期望职位");
                    return;
                }
                //更新简历
                mPresenter.updateResumeV2(mEtNickname.getText().toString(), mTvSex.getText().toString(), mTvAge.getText().toString(), mTvSchoolYear.getText().toString().trim(), mEtSchoolName.getText().toString().trim(), mEtExperience.getText().toString().trim(),
                        mTvSign.getText().toString(), profession, job_status, job_type, myitem, expect, mTvProfession.getText().toString(), mTvJobStatus.getText().toString(), mTvJobType.getText().toString());
            }
        });
    }

    @Override
    public void updateSuccess() {
        showToast("保存成功");
        MobclickAgent.onEvent(this, "resume_success");
        setResult(IntentConstant.REQEUST_CODE);
        finish();
    }

    @Override
    public void updateUserInfo(LoginResponseEntity entity) {
        mTvUsername.setText(entity.getPhone());
        if (!TextUtils.isEmpty(entity.getIntroduce())) {
            mTvSign.setText(entity.getIntroduce());
        }
        mEtNickname.setText(entity.getName());
        mTvSex.setText(entity.getSex());
        mTvAge.setText(entity.getAge());
        mTvProfession.setText(entity.getProfession());
        mTvJobStatus.setText(entity.getJob_status());
        mTvJobType.setText(entity.getJob_type());
        mTvSchoolYear.setText(entity.getSchool_year());
        mEtSchoolName.setText(entity.getSchool_name());
        mEtExperience.setText(entity.getExperience());
    }

    @Override
    public void updateupdateResumeV2(ResumeEntity resumeEntity) {
        showToast("保存成功");
        MobclickAgent.onEvent(this, "resume_success");
        setResult(IntentConstant.REQEUST_CODE);
        finish();
    }

    @Override
    public void updateUserInfoPer(UserInfoEntity entity) {
        if (entity.getData() != null) {
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
                String s = String.valueOf(stringBuffer1);
                mTvAdvantage.setText(s);
            }
            if (entity.getData().getExpect().size() > 0) {
                StringBuffer stringBuffer1 = new StringBuffer();
                StringBuffer stringBuffer2 = new StringBuffer();
                for (int i = 0; i < entity.getData().getExpect().size(); i++) {
                    stringBuffer1 = stringBuffer1.append(entity.getData().getExpect().get(i).getId() + ",");
                    stringBuffer2 = stringBuffer2.append(entity.getData().getExpect().get(i).getItem() + "、");
                }
                expect = String.valueOf(stringBuffer1);
                String s = String.valueOf(stringBuffer2);
                mTvExpected.setText(s);
            }
        }
    }

    @Override
    public void updategetMyitem(MyitemEntity myitemEntity) {

    }

    @Override
    public void updategetaddMd(ResponseData responseData) {

    }

    @Override
    public void startIntent() {

    }

    private class MyAction implements ActionListener {


        private int position;

        public MyAction(int i) {
            this.position = i;
        }

        @Override
        public void onCancelClick(BaseDialogFragment dialog) {

        }

        @Override
        public void onDoneClick(BaseDialogFragment dialog) {
            String content = "";
            if (position == 0) {
                TextPickerDialog selectedTitle = (TextPickerDialog) dialog;
                content = selectedTitle.getSelectedTitle();
                mTvSex.setText(content);
            }
            if (position == 2) {
                TextPickerDialog selectedTitle = (TextPickerDialog) dialog;
                content = selectedTitle.getSelectedTitle();
                mTvAge.setText(content);
            }
            if (position == 3) {
                TextPickerDialog selectedTitle = (TextPickerDialog) dialog;
                content = selectedTitle.getSelectedTitle();
                mTvProfession.setText(content);
                if (content.equals("学生")) {
                    profession = 1;
                } else if (content.equals("上班族")) {
                    profession = 2;
                } else if (content.equals("自由职业")) {
                    profession = 3;
                }
            }
            if (position == 4) {
                TextPickerDialog selectedTitle = (TextPickerDialog) dialog;
                content = selectedTitle.getSelectedTitle();
                mTvJobStatus.setText(content);
                if (content.equals("积极找工作")) {
                    job_status = 1;
                } else if (content.equals("随便看看")) {
                    job_status = 2;
                }
            }
            if (position == 5) {
                TextPickerDialog selectedTitle = (TextPickerDialog) dialog;
                content = selectedTitle.getSelectedTitle();
                mTvJobType.setText(content);
                if (content.equals("我想找长期稳定工作")) {
                    job_type = "1";
                } else if (content.equals("我想找短期兼职工作")) {
                    job_type = "2";
                } else if (content.equals("我想在家赚钱")) {
                    job_type = "3";
                } else if (content.equals("什么工作都行")) {
                    job_type = "4";
                }
            }
            if (position == 6) {
                DatePickerDialog datePickerDialog = (DatePickerDialog) dialog;
                Date time = datePickerDialog.getSelectedDate().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy", Locale.CHINA);
                content = simpleDateFormat.format(time);
                mTvSchoolYear.setText(content);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == 1000 && data != null) {
                myitem = data.getStringExtra("myitem");
                String myitemStr = data.getStringExtra("myitemStr");
                mTvAdvantage.setText(myitemStr);
            }
        }
        if (requestCode == 1002) {
            if (resultCode == 1006 && data != null) {
                expect = data.getStringExtra("expect");
                String expectStr = data.getStringExtra("expectStr");
                mTvExpected.setText(expectStr);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("我的简历");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("我的简历");
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            MobclickAgent.onEvent(this, "resume_back");
        }
        return super.onKeyDown(keyCode, event);
    }
}
