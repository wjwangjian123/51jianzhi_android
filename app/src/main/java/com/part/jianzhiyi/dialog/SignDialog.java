package com.part.jianzhiyi.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.customview.CustomHorizontalProgresNoNum;
import com.part.jianzhiyi.model.entity.AddSignEntity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;


public class SignDialog extends AlertDialog {

    private Context context;
    private OnSignClickListener mOnSignClickListener;
    private AddSignEntity.DataBean mDaySignEntity;
    private TextView mSignTvActive;
    private CustomHorizontalProgresNoNum mSignProgress;
    private LinearLayout mSignLinearOne;
    private LinearLayout mSignLinearTwo;
    private LinearLayout mSignLinearThree;
    private LinearLayout mSignLinearFour;
    private LinearLayout mSignLinearFive;
    private LinearLayout mSignLinearSix;
    private LinearLayout mSignLinearSeven;
    private TextView mSignTvOne;
    private TextView mSignTvTwo;
    private TextView mSignTvThree;
    private TextView mSignTvFour;
    private TextView mSignTvFive;
    private TextView mSignTvSix;
    private TextView mSignTvSeven;
    private TextView mSignTvOk;
    private View[] mViews;
    private View[] mtextViews;

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

    public SignDialog(@NonNull Context context, AddSignEntity.DataBean mDaySignEntity) {
        super(context, R.style.CircularDialog);
        init(context);
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
        mSignTvActive.setText("简历活跃度      " + mDaySignEntity.getResume() + "%");
        mSignProgress.setProgress(Integer.parseInt(mDaySignEntity.getResume()));
        int count = mViews.length;
        for (int i = 0; i < count; i++) {
            if (mDaySignEntity.getDay() > i) {
                mViews[i].setSelected(true);
                mtextViews[i].setSelected(true);
            } else {
                mViews[i].setSelected(false);
                mtextViews[i].setSelected(false);
            }
        }
    }

    private void initView() {
        mSignTvActive = findViewById(R.id.sign_tv_active);
        mSignProgress = findViewById(R.id.sign_progress);
        mSignLinearOne = findViewById(R.id.sign_linear_one);
        mSignLinearTwo = findViewById(R.id.sign_linear_two);
        mSignLinearThree = findViewById(R.id.sign_linear_three);
        mSignLinearFour = findViewById(R.id.sign_linear_four);
        mSignLinearFive = findViewById(R.id.sign_linear_five);
        mSignLinearSix = findViewById(R.id.sign_linear_six);
        mSignLinearSeven = findViewById(R.id.sign_linear_seven);
        mSignTvOne = findViewById(R.id.sign_tv_one);
        mSignTvTwo = findViewById(R.id.sign_tv_two);
        mSignTvThree = findViewById(R.id.sign_tv_three);
        mSignTvFour = findViewById(R.id.sign_tv_four);
        mSignTvFive = findViewById(R.id.sign_tv_five);
        mSignTvSix = findViewById(R.id.sign_tv_six);
        mSignTvSeven = findViewById(R.id.sign_tv_seven);
        mSignTvOk = findViewById(R.id.sign_tv_ok);
        mViews = new View[]{mSignLinearOne, mSignLinearTwo, mSignLinearThree, mSignLinearFour,
                mSignLinearFive, mSignLinearSix, mSignLinearSeven};
        mtextViews = new View[]{mSignTvOne, mSignTvTwo, mSignTvThree, mSignTvFour,
                mSignTvFive, mSignTvSix, mSignTvSeven};
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


