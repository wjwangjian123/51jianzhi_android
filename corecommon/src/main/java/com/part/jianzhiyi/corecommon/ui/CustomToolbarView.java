package com.part.jianzhiyi.corecommon.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.part.jianzhiyi.corecommon.R;
import com.part.jianzhiyi.corecommon.enums.MARGIN;
import com.part.jianzhiyi.corecommon.utils.UiUtils;

public class CustomToolbarView extends LinearLayout {
    private Context context;
    private View view;
    private ImageView ivLeftReturn;
    private ImageView ivRight;
    private TextView textMidTitle;
    private TextView textRightEdit;
    private Button btnLeftReturn;
    private ImageView rightImageView;

    public CustomToolbarView(Context context) {
        super(context);
        init(context);
    }

    public CustomToolbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomToolbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }


    private void init(Context context) {
        this.context = context;
        initViews(context);
        initListener();
    }

    private void initViews(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_base_toolbar, null);
        view.findViewById(R.id.base_root).setBackgroundResource(R.color.transparency);
        view.findViewById(R.id.base_realtive).setVisibility(VISIBLE);
        ivLeftReturn = view.findViewById(R.id.iv_left_return);
        btnLeftReturn = view.findViewById(R.id.btn_left_return);
        textMidTitle = view.findViewById(R.id.text_mid_title);
        textRightEdit = view.findViewById(R.id.text_right_edit);
        ivRight = view.findViewById(R.id.iv_right);


        rightImageView = view.findViewById(R.id.iv_right);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            ((MarginLayoutParams) (baseRealtive.getLayoutParams())).topMargin = Constants.STATUS_BAR_HEIGHT;
//        }

        UiUtils.margin(ivLeftReturn, MARGIN.LEFT, UiUtils.dip2px(15));
        addView(view);
    }

    private void initListener() {
        btnLeftReturn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLeftToolbarListener != null) {
                    onLeftToolbarListener.onLeftToolbarListener();
                }
            }
        });

        ivLeftReturn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLeftToolbarListener != null) {
                    onLeftToolbarListener.onLeftToolbarListener();
                }
            }
        });

        rightImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRightToolbarListener != null) {
                    onRightToolbarListener.onRightToolbarListener();
                }
            }
        });

        textRightEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRightToolbarListener != null) {
                    onRightToolbarListener.onRightToolbarListener();
                }
            }
        });

    }

    public void initToolbar(String title, int resId, OnLeftToolbarListener onLeftToolbarListener) {
        this.onLeftToolbarListener = onLeftToolbarListener;
        ivLeftReturn.setImageResource(resId);
        textMidTitle.setText(title);
        ivLeftReturn.setVisibility(VISIBLE);
        textMidTitle.setVisibility(VISIBLE);
    }


    public void initNormalToolbar(String title, OnLeftToolbarListener listener) {
        ivLeftReturn.setVisibility(VISIBLE);
        ivLeftReturn.setImageResource(R.drawable.ic_arrow_return);
        textMidTitle.setText(title);
        this.onLeftToolbarListener = listener;
    }

    public void setTitle(String title) {
        textMidTitle.setText(title);
    }

    public void setLeftImageView(int resId) {
        ivLeftReturn.setImageResource(resId);
    }

    public void clear() {
        ivLeftReturn.setImageResource(0);
        ivRight.setImageResource(0);
    }

    public void setRightImage(int resId, OnRightToolbarListener onRightToolbarListener) {
        ivRight.setImageResource(resId);
        ivRight.setVisibility(VISIBLE);
        this.onRightToolbarListener = onRightToolbarListener;
    }

    public void setTitleColor(int resId) {
        textMidTitle.setTextColor(getResources().getColor(resId));
    }

    public void setRightImage(int resId) {
        rightImageView.setVisibility(VISIBLE);
        rightImageView.setImageResource(resId);
    }

    public void setRightTextColor(int resId) {
        textRightEdit.setTextColor(getResources().getColor(resId));
    }


    public void setRightText(String rightText) {
        textRightEdit.setText(rightText);
        textRightEdit.setVisibility(VISIBLE);
    }

    public void setRightText(String rightText, OnRightToolbarListener onRightToolbarListener) {
        this.onRightToolbarListener = onRightToolbarListener;
        textRightEdit.setText(rightText);
        textRightEdit.setVisibility(VISIBLE);
    }

    public void setRightTextVisible(boolean isShow) {
        textRightEdit.setVisibility(isShow ? VISIBLE : GONE);
    }

    public boolean getRightTextVisible() {
        return textRightEdit.getVisibility() == VISIBLE;
    }


    public void setRightImageVisible(boolean isShow) {
        ivRight.setVisibility(isShow ? VISIBLE : GONE);
    }

    OnLeftToolbarListener onLeftToolbarListener;
    OnRightToolbarListener onRightToolbarListener;


    public interface OnLeftToolbarListener {
        void onLeftToolbarListener();
    }


    public interface OnRightToolbarListener {
        void onRightToolbarListener();
    }


    public void setOnLeftToolbarListener(OnLeftToolbarListener onLeftToolbarListener) {
        this.onLeftToolbarListener = onLeftToolbarListener;
    }


    public void setOnRightToolbarListener(OnRightToolbarListener onRightToolbarListener) {
        this.onRightToolbarListener = onRightToolbarListener;
    }

    public void setToolbarBackground(int resId) {
        findViewById(R.id.base_realtive).setBackgroundResource(resId);
    }


    public void setBackResource(int iamgeResId) {
        ivLeftReturn.setImageResource(iamgeResId);
    }

}
