package com.part.jianzhiyi.corecommon.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/10
 * @modified by:shixinxin
 **/
public class ObservableScrollView extends ScrollView {
    private ScrollChangedListener mListener;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mListener != null){
            mListener.onScrollChanged(l,t,oldl,oldt);
        }
    }

    public void setScrollChangedListener(ScrollChangedListener listener){
        mListener = listener;
    }

    public static interface ScrollChangedListener{
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

}
