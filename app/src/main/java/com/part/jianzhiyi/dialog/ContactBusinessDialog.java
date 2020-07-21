package com.part.jianzhiyi.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.DialogContactBusinessAdapter;
import com.part.jianzhiyi.model.entity.JoinJobEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ContactBusinessDialog extends AlertDialog {

    private Context context;
    private ImageView mContactIvCancel;
    private RecyclerView mContactRecycle;
    private DialogContactBusinessAdapter mDialogContactBusinessAdapter;
    private List<JoinJobEntity.DataBean> mList;
    private OnJoinedClickListener onJoinedClickListener;

    protected ContactBusinessDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    protected ContactBusinessDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected ContactBusinessDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public ContactBusinessDialog(@NonNull Context context, List<JoinJobEntity.DataBean> jobListBean, OnJoinedClickListener listener) {
        super(context, R.style.CircularDialog);
        init(context);
        this.mList = jobListBean;
        this.onJoinedClickListener = listener;
    }

    private void init(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_contact_business);
        initView();
        initListener();
        initData();
    }

    private void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mContactRecycle.setLayoutManager(linearLayoutManager);
        mDialogContactBusinessAdapter = new DialogContactBusinessAdapter(context);
        mContactRecycle.setAdapter(mDialogContactBusinessAdapter);
        if (mList.size() > 0) {
            mDialogContactBusinessAdapter.setList(mList);
        }
        mDialogContactBusinessAdapter.setmOnSetClickListener(new DialogContactBusinessAdapter.OnRecyclerSetClickListener() {
            @Override
            public void onSetClick(int position, String id) {
                if (onJoinedClickListener == null) {
                    return;
                }
                onJoinedClickListener.onJoinedClick(id, position);
            }
        });
    }

    private void initView() {
        mContactIvCancel = findViewById(R.id.contact_iv_cancel);
        mContactRecycle = findViewById(R.id.contact_recycle);
    }

    private void initListener() {
        mContactIvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    public interface OnJoinedClickListener {
        void onJoinedClick(String jobid, int position);
    }
}


