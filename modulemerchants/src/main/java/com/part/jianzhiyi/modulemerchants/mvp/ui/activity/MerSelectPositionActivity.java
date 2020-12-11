package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.adapter.MerPositionAdapter;
import com.part.jianzhiyi.modulemerchants.adapter.MerTagAdapter;
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
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MerSelectPositionActivity extends BaseActivity<MPublishPresenter> implements MPublishContract.IMPublishView {

    private RecyclerView mRecyclePosition;
    private TagFlowLayout mTflSelect;
    private TextView mTvNext;
    private MerPositionAdapter mMerPositionAdapter;
    private List<MLableEntity.DataBean> mList;
    private List<MLableEntity.DataBean.ListsBean> mBeanList;
    private MerTagAdapter mMerTagAdapter;
    private String label_id;
    private String job_id;
    private MJobInfoEntity mJobInfoEntity;
    private int type = 0;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getMLabel("1");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_select_position;
    }

    @Override
    protected void initView() {
        mRecyclePosition = (RecyclerView) findViewById(R.id.recycle_position);
        mTflSelect = (TagFlowLayout) findViewById(R.id.tfl_select);
        mTvNext = (TextView) findViewById(R.id.tv_next);
        initToolbar("");
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            mJobInfoEntity = (MJobInfoEntity) getIntent().getSerializableExtra("mJobInfoEntity");
        }
        mList = new ArrayList<>();
        mBeanList = new ArrayList<>();
        if (mJobInfoEntity != null && mJobInfoEntity.getData() != null) {
            label_id = mJobInfoEntity.getData().getLabel_id();
            job_id = mJobInfoEntity.getData().getId();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MerSelectPositionActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclePosition.setLayoutManager(linearLayoutManager);
        mMerPositionAdapter = new MerPositionAdapter(MerSelectPositionActivity.this);
        mRecyclePosition.setAdapter(mMerPositionAdapter);
        mMerPositionAdapter.setmOnItemClickListener(new MerPositionAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, String id) {
                int count = mList.size();
                for (int i = 0; i < count; i++) {
                    if (position == i) {
                        mList.get(i).setSelect(true);
                    } else {
                        mList.get(i).setSelect(false);
                    }
                }
                mMerPositionAdapter.setList(mList);
                mBeanList.clear();
                mBeanList.addAll(mList.get(position).getLists());
                mMerTagAdapter.notifyDataChanged();
            }
        });
        mMerTagAdapter = new MerTagAdapter(mBeanList, MerSelectPositionActivity.this);
        mTflSelect.setAdapter(mMerTagAdapter);
    }

    private long clickTime = 0;
    @Override
    protected void setListener() {
        super.setListener();
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(MerSelectPositionActivity.this, "mer_select_position");
                mPresenter.getmdAdd("70");
                Set<Integer> selectedList = mTflSelect.getSelectedList();
                if (selectedList.size() == 0) {
                    showToast("请选择职位");
                    return;
                }
                if (selectedList.size() == 1) {
                    for (Integer str : selectedList) {
                        label_id = mBeanList.get(str).getId();
                    }
                }
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    mPresenter.getCheckJob(label_id, job_id);
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

    @Override
    public void updategetMLabel(MLableEntity mLableEntity) {
        mList.clear();
        mBeanList.clear();
        if (mLableEntity != null) {
            if (mLableEntity.getData().size() > 0) {
                if (mJobInfoEntity != null && mJobInfoEntity.getData() != null) {
                    for (int i = 0; i < mLableEntity.getData().size(); i++) {
                        if (mLableEntity.getData().get(i).getId().equals(mJobInfoEntity.getData().getLabel_pid())) {
                            mLableEntity.getData().get(i).setSelect(true);
                            mBeanList.addAll(mLableEntity.getData().get(i).getLists());
                        }
                        for (int j = 0; j < mLableEntity.getData().get(i).getLists().size(); j++) {
                            if (mLableEntity.getData().get(i).getLists().get(j).getId().equals(mJobInfoEntity.getData().getLabel_id())) {
                                mMerTagAdapter.setSelectedList(j);
                            }
                        }
                    }
                } else {
                    mLableEntity.getData().get(0).setSelect(true);
                    if (mLableEntity.getData().get(0).getLists().size() > 0) {
                        mBeanList.addAll(mLableEntity.getData().get(0).getLists());
                    }
                }
                mList.addAll(mLableEntity.getData());
                mMerPositionAdapter.setList(mList);
                mMerTagAdapter.notifyDataChanged();
            }
        }
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
        if (responseData != null) {
            if (responseData.getCode().equals("200")) {
                Intent intent = new Intent(MerSelectPositionActivity.this, MerPositionInfoActivity.class);
                intent.putExtra("label_id", label_id);
                intent.putExtra("job_id", job_id);
                intent.putExtra("type", type);
                intent.putExtra("mJobInfoEntity", mJobInfoEntity);
                startActivity(intent);
            } else if (responseData.getCode().equals("202")) {
                //去认证
                initDialogAuthTip(responseData.getMsg());
            } else if (responseData.getCode().equals("203")) {
                //提示
                initDialogTip(responseData.getMsg());
            } else {
                showToast(responseData.getMsg());
            }
        }
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

    private long clickTime1 = 0;
    private void initDialogAuthTip(String mtip) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MerSelectPositionActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(MerSelectPositionActivity.this).inflate(R.layout.dialog_tip_publish_auth, null, false);
        TextView text = inflate.findViewById(R.id.tv_text);
        TextView tip = inflate.findViewById(R.id.tv_tip);
        TextView auth = inflate.findViewById(R.id.tv_auth);
        ImageView cancel = inflate.findViewById(R.id.iv_cancel);
        alertDialog.setView(inflate);
        text.setText("温馨提示");
        auth.setText("去认证");
        tip.setText(mtip);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        //显示
        alertDialog.show();
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (System.currentTimeMillis() - clickTime1 > 3000) {
                    clickTime1 = System.currentTimeMillis();
                    //跳转企业认证
                    Intent intent = new Intent(MerSelectPositionActivity.this, MerUploadInfoActivity.class);
                    intent.putExtra("urlType", 1);
                    startActivity(intent);
                    MerSelectPositionActivity.this.finish();
                }else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void initDialogTip(String mtip) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MerSelectPositionActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(MerSelectPositionActivity.this).inflate(R.layout.dialog_tip_publish, null, false);
        TextView tip = inflate.findViewById(R.id.tv_tip);
        ImageView cancel = inflate.findViewById(R.id.iv_cancel);
        alertDialog.setView(inflate);
        tip.setText(mtip);
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
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("商户端选择职位类型页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端选择职位类型页面");
        MobclickAgent.onPause(this);
    }
}
