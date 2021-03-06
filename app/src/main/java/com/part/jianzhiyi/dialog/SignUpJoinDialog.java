package com.part.jianzhiyi.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.DialogJoinUpAdapter;
import com.part.jianzhiyi.model.entity.JobDetailEntity;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.preference.PreferenceUUID;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SignUpJoinDialog extends AlertDialog {

    private Context context;
    private TextView mJoinTvPlace;
    private RecyclerView mJoinRecycle;
    private TextView mTvJoined;
    private ImageView mIvCancel;
    private DialogJoinUpAdapter mDialogJoinUpAdapter;
    private LoginResponseEntity entity;
    private List<JobDetailEntity.DataBean.JobListBean> mJobListBean;
    private JobDetailEntity.DataBean.InfoBean mInfoBean;
    private OnJoinedClickListener onJoinedClickListener;
    private StringBuffer stringBuffer = new StringBuffer();

    protected SignUpJoinDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    protected SignUpJoinDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected SignUpJoinDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public SignUpJoinDialog(@NonNull Context context, LoginResponseEntity entity, List<JobDetailEntity.DataBean.JobListBean> jobListBean, JobDetailEntity.DataBean.InfoBean infoBean, OnJoinedClickListener listener) {
        super(context, R.style.CircularDialog);
        init(context);
        this.entity = entity;
        this.mJobListBean = jobListBean;
        this.mInfoBean = infoBean;
        this.onJoinedClickListener = listener;
    }

    private void init(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_join_up);
        initView();
        initListener();
        initData();
    }

    private void initData() {
        //???????????????????????????
        if (mInfoBean.getPlace() == null || mInfoBean.getPlace() == "") {
            if (PreferenceUUID.getInstence().getCity() == "" || PreferenceUUID.getInstence().getCity() == null) {
                mJoinTvPlace.setText("???????????????????????????");
            } else {
                mJoinTvPlace.setText("?????????" + PreferenceUUID.getInstence().getCity());
            }
        } else {
            mJoinTvPlace.setText("?????????" + mInfoBean.getPlace());
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mJoinRecycle.setLayoutManager(linearLayoutManager);
        mDialogJoinUpAdapter = new DialogJoinUpAdapter(context);
        mJoinRecycle.setAdapter(mDialogJoinUpAdapter);
        for (int i = 0; i <mJobListBean.size() ; i++) {
            if (i<mInfoBean.getDefault_checknum()){
                mJobListBean.get(i).setIvType(1);
            }
        }
        if (mJobListBean.size() > 0) {
            mDialogJoinUpAdapter.setList(mJobListBean);
        }
        mDialogJoinUpAdapter.setmOnSetClickListener(new DialogJoinUpAdapter.OnRecyclerSetClickListener() {
            @Override
            public void onSetClick(int position, String id) {
                if (mJobListBean.get(position).getIvType() == 0) {
                    mJobListBean.get(position).setIvType(1);
                } else if (mJobListBean.get(position).getIvType() == 1) {
                    mJobListBean.get(position).setIvType(0);
                }
                mDialogJoinUpAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        mJoinTvPlace = findViewById(R.id.join_tv_place);
        mJoinRecycle = findViewById(R.id.join_recycle);
        mTvJoined = findViewById(R.id.tv_joined);
        mIvCancel = findViewById(R.id.join_iv_cancel);
    }

    private void initListener() {
        mTvJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(context, "vocation_sign");
                dismiss();
                if (onJoinedClickListener == null) {
                    return;
                }
                stringBuffer = stringBuffer.append(mInfoBean.getId());
                for (int i = 0; i < mJobListBean.size(); i++) {
                    if (mJobListBean.get(i).getIvType() == 1) {
                        stringBuffer = stringBuffer.append("," + mJobListBean.get(i).getId());
                    }
                }
                String jobId = String.valueOf(stringBuffer);
                onJoinedClickListener.onJoinedClick(jobId);
            }
        });

        mIvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(context, "vocation_cancel");
                dismiss();
            }
        });
    }


    public interface OnJoinedClickListener {
        void onJoinedClick(String jobid);
    }
}


