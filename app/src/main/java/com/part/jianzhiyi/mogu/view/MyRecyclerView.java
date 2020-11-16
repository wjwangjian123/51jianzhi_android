package com.part.jianzhiyi.mogu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jyx on 2020/8/13
 *
 * @author jyx
 * @describe
 */
public class MyRecyclerView extends RecyclerView {

    public MyRecyclerView(Context context) {
        this(context,null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    //禁止手动滑动
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }
}
