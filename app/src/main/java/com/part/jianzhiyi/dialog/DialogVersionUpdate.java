package com.part.jianzhiyi.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.part.jianzhiyi.R;

import androidx.annotation.NonNull;

/**
 * Created by jyx on 2020/5/14
 *
 * @author jyx
 * @describe
 */
public class DialogVersionUpdate extends AlertDialog {

    private Context context;
    private TextView mDialogVersionTitle;
    private TextView mDialogVersionContent;
    private TextView mDialogTvCancel;
    private TextView mDialogTvUpdate;
    private String title;
    private String content;
    private OnJoinedClickListener onJoinedClickListener;

    protected DialogVersionUpdate(Context context) {
        super(context);
        init(context);
    }

    protected DialogVersionUpdate(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    protected DialogVersionUpdate(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public DialogVersionUpdate(@NonNull Context context, String title, String content, OnJoinedClickListener listener) {
        super(context, R.style.CircularDialog);
        init(context);
        this.title = title;
        this.content = content;
        this.onJoinedClickListener = listener;
    }

    private void init(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_version_update);
        initView();
        initListener();
        initData();
    }

    private void initData() {
        mDialogVersionTitle.setText(title);
        mDialogVersionContent.setText(content);
    }

    private void initView() {
        mDialogVersionTitle = findViewById(R.id.dialog_version_title);
        mDialogVersionContent = findViewById(R.id.dialog_version_content);
        mDialogTvCancel = findViewById(R.id.dialog_tv_cancel);
        mDialogTvUpdate = findViewById(R.id.dialog_tv_update);
    }

    private void initListener() {
        mDialogTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mDialogTvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onJoinedClickListener == null) {
                    return;
                }
                onJoinedClickListener.onJoinedClick();
            }
        });
    }

    public interface OnJoinedClickListener {
        void onJoinedClick();
    }

}
