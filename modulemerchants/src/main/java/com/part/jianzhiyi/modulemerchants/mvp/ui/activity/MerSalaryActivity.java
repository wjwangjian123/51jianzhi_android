package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.part.jianzhiyi.corecommon.selectdateview.dialog.ActionListener;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.BaseDialogFragment;
import com.part.jianzhiyi.corecommon.selectdateview.dialog.TextPickerDialog;
import com.part.jianzhiyi.corecommon.selectdateview.view.TextModel;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableContactEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableSalaryEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MPublishContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MPublishPresenter;
import com.umeng.analytics.MobclickAgent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MerSalaryActivity extends BaseActivity<MPublishPresenter> implements MPublishContract.IMPublishView {

    private EditText mEtSalary;
    private TextView mTvSalary;
    private TagFlowLayout mTflSelect;
    private TextView mTvNext;

    private List<String> mSalaryList;
    private List<String> mMethodList;
    private List<TextModel> mList;
    private String label_id;
    private String job_id;
    private String title;
    private String content;
    private String num;
    private String method;
    private int type = 0;
    private MJobInfoEntity mJobInfoEntity;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getMLabelMethod("2");
        mPresenter.getMLabelSalary("3");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_salary;
    }

    @Override
    protected void initView() {
        mEtSalary = (EditText) findViewById(R.id.et_salary);
        mTvSalary = (TextView) findViewById(R.id.tv_salary);
        mTflSelect = (TagFlowLayout) findViewById(R.id.tfl_select);
        mTvNext = (TextView) findViewById(R.id.tv_next);
        initToolbar("");
    }

    @Override
    protected void initData() {
        mMethodList = new ArrayList<>();
        mSalaryList = new ArrayList<>();
        mList = new ArrayList<>();
        label_id = getIntent().getStringExtra("label_id");
        job_id = getIntent().getStringExtra("job_id");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        num = getIntent().getStringExtra("num");
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            mJobInfoEntity = (MJobInfoEntity) getIntent().getSerializableExtra("mJobInfoEntity");
        }
        if (mJobInfoEntity != null && mJobInfoEntity.getData() != null) {
            mEtSalary.setText(mJobInfoEntity.getData().getPrice());
            mTvSalary.setText(mJobInfoEntity.getData().getPrice2());
        }
    }

    private long clickTime = 0;
    @Override
    protected void setListener() {
        super.setListener();
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(MerSalaryActivity.this, "mer_salary");
                mPresenter.getmdAdd("71");
                if (TextUtils.isEmpty(mEtSalary.getText().toString().trim())) {
                    showToast("请输入基本薪资");
                    return;
                }
                Set<Integer> selectedList = mTflSelect.getSelectedList();
                if (selectedList.size() == 0) {
                    showToast("请选择结算方式");
                    return;
                }
                if (selectedList.size() == 1) {
                    for (Integer str : selectedList) {
                        method = mMethodList.get(str);
                    }
                }
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    Intent intent = new Intent(MerSalaryActivity.this, MerOtherActivity.class);
                    intent.putExtra("label_id", label_id);
                    intent.putExtra("job_id", job_id);
                    intent.putExtra("title", title);
                    intent.putExtra("content", content);
                    intent.putExtra("num", num);
                    intent.putExtra("price1", mEtSalary.getText().toString().trim());
                    intent.putExtra("price2", mTvSalary.getText().toString().trim());
                    intent.putExtra("method", method);
                    intent.putExtra("type", type);
                    intent.putExtra("mJobInfoEntity", mJobInfoEntity);
                    startActivity(intent);
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mTvSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextPickerDialog pickDialog = TextPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, new MyAction(0));
                pickDialog.setList(mList);
                pickDialog.show(getFragmentManager(), "dialog");
            }
        });
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
                mTvSalary.setText(content);
            }
        }
    }

    @Override
    protected MPublishPresenter createPresenter() {
        return new MPublishPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void updategetMLabel(MLableEntity mLableEntity) {

    }

    @Override
    public void updategetMLabelMethod(MLableSalaryEntity mLableSalaryEntity) {
        mMethodList.clear();
        if (mLableSalaryEntity != null) {
            if (mLableSalaryEntity.getData().size() > 0) {
                mMethodList.addAll(mLableSalaryEntity.getData());
                mTflSelect.setAdapter(new TagAdapter<String>(mMethodList) {
                    @Override
                    public View getView(FlowLayout parent, int position, String databean) {
                        TextView tv = (TextView) LayoutInflater.from(MerSalaryActivity.this).inflate(R.layout.item_text_flow, ((TagFlowLayout) mTflSelect), false);
                        tv.setText(databean);
                        return tv;
                    }
                });
                if (mJobInfoEntity != null && mJobInfoEntity.getData() != null) {
                    for (int i = 0; i < mMethodList.size(); i++) {
                        if (mMethodList.get(i).equals(mJobInfoEntity.getData().getMethod())) {
                            method = mJobInfoEntity.getData().getMethod();
                            mTflSelect.getAdapter().setSelectedList(i);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updategetMLabelSalary(MLableSalaryEntity mLableSalaryEntity) {
        mSalaryList.clear();
        mList.clear();
        if (mLableSalaryEntity != null) {
            if (mLableSalaryEntity.getData().size() > 0) {
                mSalaryList.addAll(mLableSalaryEntity.getData());
                for (int i = 0; i < mSalaryList.size(); i++) {
                    mList.add(new TextModel(mSalaryList.get(i)));
                }
            }
        }
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

    }

    @Override
    public void updategetMerUserinfo(MUserInfoEntity mUserInfoEntity) {

    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    public void updategetTextFilter(ResponseData responseData) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端薪资福利页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端薪资福利页面");
        MobclickAgent.onPause(this);
    }
}
