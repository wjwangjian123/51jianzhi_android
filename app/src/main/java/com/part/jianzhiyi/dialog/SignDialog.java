package com.part.jianzhiyi.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.adapter.DialogContactBusinessAdapter;
import com.part.jianzhiyi.customview.CustomHorizontalProgresNoNum;
import com.part.jianzhiyi.model.entity.DaySignEntity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;


public class SignDialog extends AlertDialog {

    private Context context;
    private OnSignClickListener mOnSignClickListener;
    private String active;
    private DaySignEntity mDaySignEntity;
    private TextView mSignTvText;
    private TextView mSignTvActive;
    private CustomHorizontalProgresNoNum mSignProgress;
    private LinearLayout mSignLinearOne;
    private LinearLayout mSignLinearTwo;
    private LinearLayout mSignLinearThree;
    private LinearLayout mSignLinearFour;
    private LinearLayout mSignLinearFive;
    private LinearLayout mSignLinearSix;
    private LinearLayout mSignLinearSeven;
    private TextView mSignTvOk;
    private View[] mViews;

    protected SignDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    protected SignDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected SignDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public SignDialog(@NonNull Context context, String active, DaySignEntity mDaySignEntity) {
        super(context, R.style.CircularDialog);
        init(context);
        this.active = active;
        this.mDaySignEntity = mDaySignEntity;
    }

    private void init(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        mSignTvActive.setText("简历活跃度      " + active + "%");
        mSignProgress.setProgress(Integer.parseInt(active));
        int count = mViews.length;
        for (int i = 0; i < count; i++) {
            if (mDaySignEntity.getData().getDay()>=i) {
                mViews[i].setSelected(true);
            } else {
                mViews[i].setSelected(false);
            }
        }
    }

    private void initView() {
        mSignTvText = findViewById(R.id.sign_tv_text);
        mSignTvActive = findViewById(R.id.sign_tv_active);
        mSignProgress = findViewById(R.id.sign_progress);
        mSignLinearOne = findViewById(R.id.sign_linear_one);
        mSignLinearTwo = findViewById(R.id.sign_linear_two);
        mSignLinearThree = findViewById(R.id.sign_linear_three);
        mSignLinearFour = findViewById(R.id.sign_linear_four);
        mSignLinearFive = findViewById(R.id.sign_linear_five);
        mSignLinearSix = findViewById(R.id.sign_linear_six);
        mSignLinearSeven = findViewById(R.id.sign_linear_seven);
        mSignTvOk = findViewById(R.id.sign_tv_ok);
        mViews = new View[]{mSignLinearOne, mSignLinearTwo, mSignLinearThree,mSignLinearFour,
                mSignLinearFive,mSignLinearSix,mSignLinearSeven};
    }

    private void initListener() {
        mSignTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface OnSignClickListener {
        void onSignClick(String jobid, int position);
    }
}


