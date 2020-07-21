package com.part.jianzhiyi.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.constants.IntentConstant;
import com.part.jianzhiyi.mvp.ui.activity.HtmlActivity;
import com.part.jianzhiyi.preference.PreferenceUUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;

/**
 * @author shixinxin
 * dialog
 */

public class PrivacyPolicyDialog extends AlertDialog {

    private Context context;
    private TextView mDialogTvContent;
    private TextView mDialogTvPrivacy;
    private TextView mDialogTvAgreement;
    private TextView mDialogTvConfirm;
    private DialogRefuseListener onDialogRefuseListener;


    public PrivacyPolicyDialog(@NonNull Context context, DialogRefuseListener onDialogRefuseListener) {
        super(context, R.style.CircularDialog);
        this.onDialogRefuseListener = onDialogRefuseListener;
        init(context);
    }

    protected PrivacyPolicyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected PrivacyPolicyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }


    private void init(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_privacy_policy_dialog);
        initViews();
        initData();
        initListener();
    }

    private void initViews() {
        mDialogTvContent = (TextView) findViewById(R.id.dialog_tv_content);
        mDialogTvPrivacy = (TextView) findViewById(R.id.dialog_tv_privacy);
        mDialogTvAgreement = (TextView) findViewById(R.id.dialog_tv_agreement);
        mDialogTvConfirm = (TextView) findViewById(R.id.dialog_tv_confirm);

        String contextString = context.getString(R.string.privacy_policy_detail);
        mDialogTvContent.setText(contextString);
    }


    private void initData() {

    }

    private void initListener() {
        mDialogTvPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HtmlActivity.class);
                intent.putExtra(IntentConstant.HTML_URL, Constants.HTML_PRIVACY_URL + Constants.APPID + "&status=" + Constants.STATUS);
                context.startActivity(intent);
            }
        });
        mDialogTvAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HtmlActivity.class);
                intent.putExtra(IntentConstant.HTML_URL, Constants.HTML_USER_URL + Constants.APPID + "&status=" + Constants.STATUS);
                context.startActivity(intent);
            }
        });
        mDialogTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                PreferenceUUID.getInstence().changePrivacyState();
            }
        });
    }


    public interface DialogRefuseListener {
        void onClickRefuse();
    }

}
