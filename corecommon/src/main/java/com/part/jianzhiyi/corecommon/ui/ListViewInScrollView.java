package com.part.jianzhiyi.corecommon.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 用于ListView必须嵌入ScrollView时使用,重写了onMeasure()
 *
 * @author wucx
 * 文件名称：ListViewInScrollView.java
 * 修改时间：2017年3月11日
 */
public class ListViewInScrollView extends ListView {

    public ListViewInScrollView(Context context) {
        super(context);
    }

    public ListViewInScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
