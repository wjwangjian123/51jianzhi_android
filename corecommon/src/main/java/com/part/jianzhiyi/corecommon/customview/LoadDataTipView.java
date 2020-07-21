package com.part.jianzhiyi.corecommon.customview;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.part.jianzhiyi.corecommon.R;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.corecommon.utils.UiUtils;

/**
 * 加载数据显示view
 * 加载失败显示
 */
public class LoadDataTipView extends RelativeLayout {

    private View view;
    private RelativeLayout rlNoData;
    private ImageView ivNoData;
    private TextView tvNoData;
    private Context context;
    private OnRetryListener onRetryListener;


    public LoadDataTipView(Context context) {
        super(context);
        init(context);
    }

    public LoadDataTipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadDataTipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOnRetryListener(OnRetryListener onRetryListener) {
        this.onRetryListener = onRetryListener;
    }

    private void init(Context context) {
        this.context = context;
        initViews(context);
        initListener();
    }


    private void initViews(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_default_no_data, null);
        rlNoData = view.findViewById(R.id.rl_no_data);
        tvNoData = view.findViewById(R.id.tv_no_data_tips);
        ivNoData = view.findViewById(R.id.iv_no_data);
        int width = Tools.getApplicationByReflection().getResources().getDisplayMetrics().widthPixels;
        int height = Tools.getApplicationByReflection().getResources().getDisplayMetrics().heightPixels;
//        ivNoData.getLayoutParams().width = width;
//        ivNoData.getLayoutParams().height = width;
        int top = height / 2 - UiUtils.dip2px(44) - UiUtils.getStatusBarHeight(Tools.getApplicationByReflection());
        ((MarginLayoutParams) ivNoData.getLayoutParams()).topMargin = top - width;
        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(view);
        rlNoData.setVisibility(GONE);
    }


    private void initListener() {
        tvNoData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRetryListener == null) {
                    return;
                }
                onRetryListener.onRetry();
            }
        });
    }

    public void setImageMarginTop(int top) {
        ((MarginLayoutParams) ivNoData.getLayoutParams()).topMargin = top;
    }


    public void setNoDataUI(MODE type) {
        if (type == MODE.ERROR) {
            tvNoData.setText(Html.fromHtml("加载异常，<font color='#F5633C'>请重试</font>"));
        } else if (type == MODE.LOADING) {
            tvNoData.setText("加载数据");
        } else if (type == MODE.NODATA) {
            tvNoData.setText("暂无数据");
        }
        setVisibility(VISIBLE);
        rlNoData.setVisibility(View.VISIBLE);
//        tvNoData.setVisibility(View.VISIBLE);
        tvNoData.setClickable(type == MODE.ERROR);
    }

    public void setUI(String title, int resId) {
        tvNoData.setText(title);
        ivNoData.setImageResource(resId);
    }


    public void setNoDateBackground(int resId) {
        rlNoData.setBackgroundResource(resId);
    }

    public void setNoDataHidden() {
        rlNoData.setVisibility(GONE);
    }

    public interface OnRetryListener {
        void onRetry();
    }

    public enum MODE {
        LOADING,//加载数据
        NODATA,//暂无数据
        ERROR,//加载异常
        SUCCESS;
    }
}
