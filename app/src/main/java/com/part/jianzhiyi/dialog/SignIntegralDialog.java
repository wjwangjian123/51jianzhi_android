package com.part.jianzhiyi.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.part.jianzhiyi.R;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;


public class SignIntegralDialog extends AlertDialog {

    private Context context;
    private SignInfoEntity.DataBean mSignInfoEntity;
    private View[] mViews;
    private TextView[] mTextViews;
    private TextView[] mScoreViews;
    private View[] mIvViews;
    private View[] mIvScoreViews;
    private View[] mIvDuiViews;
    private View[] mTvReceivedViews;
    private TextView mSignTvTitle;
    private TextView mSignTvCon;
    private TextView mTvOne;
    private ImageView mIvOne;
    private TextView mTvOneScore;
    private ImageView mIvOneSelect;
    private TextView mTvOneSelect;
    private LinearLayout mSignLlOne;
    private ImageView mIvOneHigh;
    private TextView mTvTwo;
    private ImageView mIvTwo;
    private TextView mTvTwoScore;
    private ImageView mIvTwoSelect;
    private TextView mTvTwoSelect;
    private LinearLayout mSignLlTwo;
    private ImageView mIvTwoHigh;
    private TextView mTvThree;
    private ImageView mIvThree;
    private TextView mTvThreeScore;
    private ImageView mIvThreeSelect;
    private TextView mTvThreeSelect;
    private LinearLayout mSignLlThree;
    private ImageView mIvThreeHigh;
    private TextView mTvFour;
    private ImageView mIvFour;
    private TextView mTvFourScore;
    private ImageView mIvFourSelect;
    private TextView mTvFourSelect;
    private LinearLayout mSignLlFour;
    private ImageView mIvFourHigh;
    private TextView mTvFive;
    private ImageView mIvFive;
    private TextView mTvFiveScore;
    private ImageView mIvFiveSelect;
    private TextView mTvFiveSelect;
    private LinearLayout mSignLlFive;
    private ImageView mIvFiveHigh;
    private TextView mTvSix;
    private ImageView mIvSix;
    private TextView mTvSixScore;
    private ImageView mIvSixSelect;
    private TextView mTvSixSelect;
    private LinearLayout mSignLlSix;
    private ImageView mIvSixHigh;
    private TextView mTvSeven;
    private ImageView mIvSeven;
    private TextView mTvSevenScore;
    private ImageView mIvSevenSelect;
    private TextView mTvSevenSelect;
    private RelativeLayout mSignLlSeven;
    private ImageView mIvSevenHigh;
    private TextView mSignTvOk;

    protected SignIntegralDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    protected SignIntegralDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected SignIntegralDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public SignIntegralDialog(@NonNull Context context, SignInfoEntity.DataBean mDaySignEntity) {
        super(context, R.style.CircularDialog);
        init(context);
        this.mSignInfoEntity = mDaySignEntity;
    }

    private void init(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jifen_sign);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mSignTvTitle = findViewById(R.id.sign_tv_title);
        mSignTvCon = findViewById(R.id.sign_tv_con);
        mTvOne = findViewById(R.id.tv_one);
        mIvOne = findViewById(R.id.iv_one);
        mTvOneScore = findViewById(R.id.tv_one_score);
        mIvOneSelect = findViewById(R.id.iv_one_select);
        mTvOneSelect = findViewById(R.id.tv_one_select);
        mSignLlOne = findViewById(R.id.sign_ll_one);
        mIvOneHigh = findViewById(R.id.iv_one_high);
        mTvTwo = findViewById(R.id.tv_two);
        mIvTwo = findViewById(R.id.iv_two);
        mTvTwoScore = findViewById(R.id.tv_two_score);
        mIvTwoSelect = findViewById(R.id.iv_two_select);
        mTvTwoSelect = findViewById(R.id.tv_two_select);
        mSignLlTwo = findViewById(R.id.sign_ll_two);
        mIvTwoHigh = findViewById(R.id.iv_two_high);
        mTvThree = findViewById(R.id.tv_three);
        mIvThree = findViewById(R.id.iv_three);
        mTvThreeScore = findViewById(R.id.tv_three_score);
        mIvThreeSelect = findViewById(R.id.iv_three_select);
        mTvThreeSelect = findViewById(R.id.tv_three_select);
        mSignLlThree = findViewById(R.id.sign_ll_three);
        mIvThreeHigh = findViewById(R.id.iv_three_high);
        mTvFour = findViewById(R.id.tv_four);
        mIvFour = findViewById(R.id.iv_four);
        mTvFourScore = findViewById(R.id.tv_four_score);
        mIvFourSelect = findViewById(R.id.iv_four_select);
        mTvFourSelect = findViewById(R.id.tv_four_select);
        mSignLlFour = findViewById(R.id.sign_ll_four);
        mIvFourHigh = findViewById(R.id.iv_four_high);
        mTvFive = findViewById(R.id.tv_five);
        mIvFive = findViewById(R.id.iv_five);
        mTvFiveScore = findViewById(R.id.tv_five_score);
        mIvFiveSelect = findViewById(R.id.iv_five_select);
        mTvFiveSelect = findViewById(R.id.tv_five_select);
        mSignLlFive = findViewById(R.id.sign_ll_five);
        mIvFiveHigh = findViewById(R.id.iv_five_high);
        mTvSix = findViewById(R.id.tv_six);
        mIvSix = findViewById(R.id.iv_six);
        mTvSixScore = findViewById(R.id.tv_six_score);
        mIvSixSelect = findViewById(R.id.iv_six_select);
        mTvSixSelect = findViewById(R.id.tv_six_select);
        mSignLlSix = findViewById(R.id.sign_ll_six);
        mIvSixHigh = findViewById(R.id.iv_six_high);
        mTvSeven = findViewById(R.id.tv_seven);
        mIvSeven = findViewById(R.id.iv_seven);
        mTvSevenScore = findViewById(R.id.tv_seven_score);
        mIvSevenSelect = findViewById(R.id.iv_seven_select);
        mTvSevenSelect = findViewById(R.id.tv_seven_select);
        mSignLlSeven = findViewById(R.id.sign_ll_seven);
        mIvSevenHigh = findViewById(R.id.iv_seven_high);
        mSignTvOk = findViewById(R.id.sign_tv_ok);
        mViews = new View[]{mSignLlOne, mSignLlTwo, mSignLlThree, mSignLlFour, mSignLlFive, mSignLlSix, mSignLlSeven};
        mTextViews = new TextView[]{mTvOne, mTvTwo, mTvThree, mTvFour, mTvFive, mTvSix, mTvSeven};
        mScoreViews = new TextView[]{mTvOneScore, mTvTwoScore, mTvThreeScore, mTvFourScore, mTvFiveScore, mTvSixScore, mTvSevenScore};
        mIvViews = new View[]{mIvOneHigh, mIvTwoHigh, mIvThreeHigh, mIvFourHigh, mIvFiveHigh, mIvSixHigh, mIvSevenHigh};
        mIvScoreViews = new View[]{mIvOne, mIvTwo, mIvThree, mIvFour, mIvFive, mIvSix, mIvSeven};
        mIvDuiViews = new View[]{mIvOneSelect, mIvTwoSelect, mIvThreeSelect, mIvFourSelect, mIvFiveSelect, mIvSixSelect, mIvSevenSelect};
        mTvReceivedViews = new View[]{mTvOneSelect, mTvTwoSelect, mTvThreeSelect, mTvFourSelect, mTvFiveSelect, mTvSixSelect, mTvSevenSelect};
    }

    private void initData() {
        mSignTvTitle.setText("已累计签到" + mSignInfoEntity.getDay() + "天");
        mSignTvCon.setText(mSignInfoEntity.getCon() + "");
        for (int i = 0; i < mSignInfoEntity.getGetDayJf().size(); i++) {
            mTextViews[i].setText("第" + mSignInfoEntity.getGetDayJf().get(i).getDay() + "天");
            mScoreViews[i].setText("+" + mSignInfoEntity.getGetDayJf().get(i).getJf());
            if (mSignInfoEntity.getGetDayJf().get(i).getMax() == 1) {
                mIvViews[i].setVisibility(View.VISIBLE);
            } else {
                mIvViews[i].setVisibility(View.GONE);
            }
        }
        int count = mViews.length;
        for (int i = 0; i < count; i++) {
            if (mSignInfoEntity.getDay() > i) {
                mViews[i].setSelected(true);
                mTextViews[i].setVisibility(View.GONE);
                mIvScoreViews[i].setVisibility(View.GONE);
                mScoreViews[i].setVisibility(View.GONE);
                mIvDuiViews[i].setVisibility(View.VISIBLE);
                mTvReceivedViews[i].setVisibility(View.VISIBLE);
            } else {
                mViews[i].setSelected(false);
                mTextViews[i].setVisibility(View.VISIBLE);
                mIvScoreViews[i].setVisibility(View.VISIBLE);
                mScoreViews[i].setVisibility(View.VISIBLE);
                mIvDuiViews[i].setVisibility(View.GONE);
                mTvReceivedViews[i].setVisibility(View.GONE);
            }
        }
    }

    private void initListener() {
        mSignTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}


