package com.part.jianzhiyi.corecommon.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.part.jianzhiyi.corecommon.utils.UiUtils;

@SuppressLint("AppCompatCustomView")
public class RoundImageView extends ImageView {
    private Context mContext;
    float width, height;
    private int mRadiusPx;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mRadiusPx = UiUtils.dip2px(10);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //这里的目的是将画布设置成一个顶部边缘是圆角的矩形
        if (width > mRadiusPx && height > mRadiusPx) {
            Path path = new Path();

            path.moveTo(mRadiusPx, 0);
            path.lineTo(width - mRadiusPx, 0);
            path.quadTo(width, 0, width, mRadiusPx);
            path.lineTo(width, height - mRadiusPx);
            path.quadTo(width, height, width - mRadiusPx, height);
            path.lineTo(mRadiusPx, height);
            path.quadTo(0, height, 0, height - mRadiusPx);
            path.lineTo(0, mRadiusPx);
            path.quadTo(0, 0, mRadiusPx, 0);


            canvas.clipPath(path);
        }

        super.onDraw(canvas);
    }
}
