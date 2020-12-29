package com.part.jianzhiyi.modulemerchants.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.part.jianzhiyi.modulemerchants.R;

import java.util.List;

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
    private View mView;
    private String title;
    private List<String> content;
    private int isForced;
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

    public DialogVersionUpdate(@NonNull Context context, String mtitle, List<String> mcontent, int is_forced, OnJoinedClickListener listener) {
        super(context, R.style.CircularDialog);
        init(context);
        this.title = mtitle;
        this.content = mcontent;
        this.isForced = is_forced;
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
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < content.size(); i++) {
            stringBuffer = stringBuffer.append(content.get(i) + "\n");
        }
        String s = String.valueOf(stringBuffer);
        mDialogVersionContent.setText(s);
        if (isForced == 0) {
            mDialogTvCancel.setVisibility(View.VISIBLE);
            mView.setVisibility(View.VISIBLE);
        } else if (isForced == 1) {
            mDialogTvCancel.setVisibility(View.GONE);
            mView.setVisibility(View.GONE);
        }
    }

    private void initView() {
        mDialogVersionTitle = findViewById(R.id.dialog_version_title);
        mDialogVersionContent = findViewById(R.id.dialog_version_content);
        mDialogTvCancel = findViewById(R.id.dialog_tv_cancel);
        mDialogTvUpdate = findViewById(R.id.dialog_tv_update);
        mView = findViewById(R.id.view);
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
