package com.part.jianzhiyi.corecommon.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author:shixinxin
 * @Date :2020-03-30
 **/
public class ListViewInScrollView2 extends ListView {
    public ListViewInScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height=0;
        for (int i = 0; i < getChildCount(); i++) {
           height+= getChildAt(i).getMeasuredHeight();
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,height);
    }
}
