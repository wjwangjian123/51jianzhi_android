package com.part.jianzhiyi.mvp.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.base.BaseActivity;
import com.part.jianzhiyi.constants.IntentConstant;
import com.part.jianzhiyi.corecommon.constants.ConstantsDimens;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.ActionListener;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.BaseDialogFragment;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.TextPickerDialog;
import com.part.jianzhiyi.corecommon.selectdateview.view.TextModel;
import com.part.jianzhiyi.corecommon.ui.ObservableScrollView;
import com.part.jianzhiyi.corecommon.utils.SoftKeyboardUtils;
import com.part.jianzhiyi.corecommon.utils.UiUtils;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.MyitemEntity;
import com.part.jianzhiyi.model.entity.ResumeEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.mvp.contract.mine.MineUpdateResumeContract;
import com.part.jianzhiyi.mvp.presenter.mine.MineUpdateResumePresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class MineUpdateResumeActivity extends BaseActivity<MineUpdateResumePresenter> implements MineUpdateResumeContract.IMineUpdateResumeView {

    private TextView mTvUsername;
    private LinearLayout mMineLinear;
    private EditText mTvSign;
    private ImageView mIvAvatar;
    private RelativeLayout mRlLogin;
    private TextView mTvMine;
    private LinearLayout mLlInfo;
    private TextView mTvName;
    private EditText mEtNickname;
    private ImageView mIvNicknameRight;
    private TextView mTvSex;
    private ImageView mIvSexRight;
    private TextView mTvAge;
    private ImageView mIvAgeRight;
    private TextView mTvStatus;
    private LinearLayout mLlStatus;
    private TextView mProfession;
    private TextView mTvProfession;
    private ImageView mIvProfessionRight;
    private TextView mJobStatus;
    private TextView mTvJobStatus;
    private ImageView mIvJobStatusRight;
    private TextView mJobType;
    private TextView mTvJobType;
    private ImageView mIvJobTypeRight;
    private TextView mTvSave;
    private ObservableScrollView mScrollView;
    private List<TextModel> list;
    private List<TextModel> list_age;
    private List<TextModel> list_profession;
    private List<TextModel> list_job_status;
    private List<TextModel> list_job_type;
    private int profession = 1;
    private int job_status = 1;
    private int job_type;

    @Override
    protected void init() {
        super.init();
        list=new ArrayList<>();
        list_age=new ArrayList<>();
        list_profession=new ArrayList<>();
        list_job_status=new ArrayList<>();
        list_job_type=new ArrayList<>();
        list.add(new TextModel("男"));
        list.add(new TextModel("女"));
        for (int i = 18; i < 61; i++) {
            list_age.add(new TextModel(i+""));
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

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mine_update_resume;
    }

    @Override
    protected void initView() {
        mTvUsername = (TextView) findViewById(R.id.tvUsername);
        mMineLinear = (LinearLayout) findViewById(R.id.mine_linear);
        mTvSign = (EditText) findViewById(R.id.tvSign);
        mIvAvatar = (ImageView) findViewById(R.id.iv_avatar);
        mRlLogin = (RelativeLayout) findViewById(R.id.rl_login);
        mTvMine = (TextView) findViewById(R.id.tv_mine);
        mLlInfo = (LinearLayout) findViewById(R.id.llInfo);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mEtNickname = (EditText) findViewById(R.id.etNickname);
        mIvNicknameRight = (ImageView) findViewById(R.id.iv_nickname_right);
        mTvSex = (TextView) findViewById(R.id.tvSex);
        mIvSexRight = (ImageView) findViewById(R.id.iv_sex_right);
        mTvAge = (TextView) findViewById(R.id.tvAge);
        mIvAgeRight = (ImageView) findViewById(R.id.iv_age_right);
        mTvStatus = (TextView) findViewById(R.id.tv_status);
        mLlStatus = (LinearLayout) findViewById(R.id.llStatus);
        mProfession = (TextView) findViewById(R.id.profession);
        mTvProfession = (TextView) findViewById(R.id.tv_profession);
        mIvProfessionRight = (ImageView) findViewById(R.id.iv_profession_right);
        mJobStatus = (TextView) findViewById(R.id.job_status);
        mTvJobStatus = (TextView) findViewById(R.id.tv_job_status);
        mIvJobStatusRight = (ImageView) findViewById(R.id.iv_job_status_right);
        mJobType = (TextView) findViewById(R.id.job_type);
        mTvJobType = (TextView) findViewById(R.id.tv_job_type);
        mIvJobTypeRight = (ImageView) findViewById(R.id.iv_job_type_right);
        mTvSave = (TextView) findViewById(R.id.tv_save);
        mScrollView = (ObservableScrollView) findViewById(R.id.scrollView);
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
        mIvSexRight.setOnClickListener(new View.OnClickListener() {
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
        mIvAgeRight.setOnClickListener(new View.OnClickListener() {
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
        mIvProfessionRight.setOnClickListener(new View.OnClickListener() {
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
        mIvJobStatusRight.setOnClickListener(new View.OnClickListener() {
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
        mIvJobTypeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextPickerDialog pickDialog = TextPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, new MyAction(5));
                pickDialog.setList(list_job_type);
                pickDialog.show(getFragmentManager(), "dialog");
            }
        });
        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEtNickname.getText().toString())) {
                    showToast("请填写姓名");
                    return;
                }
                //更新简历
                mPresenter.updateResumeV2(mEtNickname.getText().toString(), mTvSex.getText().toString(), mTvAge.getText().toString(), "", "", "",
                        mTvSign.getText().toString(), profession, job_status, String.valueOf(job_type), mTvProfession.getText().toString(), mTvJobStatus.getText().toString(), mTvJobType.getText().toString());
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
    }

    @Override
    public void updateupdateResumeV2(ResumeEntity resumeEntity) {
        showToast("保存成功");
        MobclickAgent.onEvent(this, "resume_success");
        setResult(IntentConstant.REQEUST_CODE);
        finish();
    }

    @Override
    public void updateUserInfoPer(UserInfoEntity userInfoEntity) {

    }

    @Override
    public void updategetMyitem(MyitemEntity myitemEntity) {

    }

    @Override
    public void startIntent() {

    }

    private class MyAction implements ActionListener {


        private int position;
        public MyAction(int i) {
            this.position=i;
        }

        @Override
        public void onCancelClick(BaseDialogFragment dialog) {

        }

        @Override
        public void onDoneClick(BaseDialogFragment dialog) {
            String content = "";
            if (position==0){
                TextPickerDialog selectedTitle = (TextPickerDialog) dialog;
                content = selectedTitle.getSelectedTitle();
                mTvSex.setText(content);
            }
            if (position==2){
                TextPickerDialog selectedTitle = (TextPickerDialog) dialog;
                content = selectedTitle.getSelectedTitle();
                mTvAge.setText(content);
            }
            if (position==3){
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
            if (position==4){
                TextPickerDialog selectedTitle = (TextPickerDialog) dialog;
                content = selectedTitle.getSelectedTitle();
                mTvJobStatus.setText(content);
                if (content.equals("积极找工作")) {
                    job_status = 1;
                } else if (content.equals("随便看看")) {
                    job_status = 2;
                }
            }
            if (position==5){
                TextPickerDialog selectedTitle = (TextPickerDialog) dialog;
                content = selectedTitle.getSelectedTitle();
                mTvJobType.setText(content);
                if (content.equals("我想找长期稳定工作")) {
                    job_type = 1;
                } else if (content.equals("我想找短期兼职工作")) {
                    job_type = 2;
                } else if (content.equals("我想在家赚钱")) {
                    job_type = 3;
                } else if (content.equals("什么工作都行")) {
                    job_type = 4;
                }
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
