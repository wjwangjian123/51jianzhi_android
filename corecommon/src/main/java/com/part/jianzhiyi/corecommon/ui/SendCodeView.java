package com.part.jianzhiyi.corecommon.ui;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.part.jianzhiyi.corecommon.R;
import com.part.jianzhiyi.corecommon.constants.ConstantsDimens;
import com.part.jianzhiyi.corecommon.enums.MARGIN;
import com.part.jianzhiyi.corecommon.utils.UiUtils;

public class SendCodeView extends LinearLayout implements View.OnClickListener {

    private final static long sendSmsCodeTime = 60000;
    private Context context;
    private TextView tvAreaCode;
    private InputFilteEditText etPhone;
    private InputFilteEditText etCode;
    private TextView tvSendCode;
    private CountDownTimer timer;
    private InputFilteEditText.OnTextChangeListener onTextChangeListener;
    private OnSendCodeClickListener onSendCodeClickListener;

    public void setOnSendCodeClickListener(OnSendCodeClickListener onSendCodeClickListener) {
        this.onSendCodeClickListener = onSendCodeClickListener;
    }

    public void setOnTextChangeListener(InputFilteEditText.OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
        etPhone.setOnTextChangeListener(onTextChangeListener);
        etCode.setOnTextChangeListener(onTextChangeListener);
    }

    public SendCodeView(Context context) {
        super(context);
        init(context);
    }

    public SendCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SendCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.include_common_et_code, null, false);
        etPhone = view.findViewById(R.id.et_input_phone);
        etCode = view.findViewById(R.id.et_input_code);
        tvSendCode = view.findViewById(R.id.tv_send_code);
        tvSendCode.setOnClickListener(this);
        addView(view);
    }


    public void isShowArea(boolean isShow) {
        tvAreaCode.setVisibility(isShow ? VISIBLE : GONE);
        if (isShow) {
            UiUtils.margin(tvAreaCode, MARGIN.LEFT, ConstantsDimens.NORMAL_PADDING);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_send_code) {
            if (timer != null) {
                return;
            }
            if (onSendCodeClickListener != null) {
                onSendCodeClickListener.onSendCodeClick();
            }
        }
    }

    public void startTimer() {
        timer = new CountDownTimer(sendSmsCodeTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvSendCode.setText(millisUntilFinished / 1000 + " 秒后重试");
                tvSendCode.setSelected(true);
            }

            @Override
            public void onFinish() {
                if (timer != null) {
                    tvSendCode.setSelected(false);
                    tvSendCode.setText("发送验证码");
                    timer.cancel();
                    timer = null;

                }
            }
        };
        timer.start();
    }

    public String getPhone() {
        return etPhone.getText().toString();
    }

    public String getSmsCode() {
        return etCode.getText().toString();
    }

    public void setHint(String phoneHint, String codeHint) {
        etPhone.setHint(phoneHint);
        etCode.setHint(codeHint);
    }

    public void setPhone(String phone) {
        etPhone.setText(phone);
    }

    public void clearTimer() {
        if (timer != null) {
            tvSendCode.setSelected(false);
            tvSendCode.setText("发送验证码");
            timer.cancel();
            timer = null;

        }
    }


    public interface OnSendCodeClickListener {
        void onSendCodeClick();
    }
}
