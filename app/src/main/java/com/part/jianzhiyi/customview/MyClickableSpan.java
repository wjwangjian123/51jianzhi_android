package com.part.jianzhiyi.customview;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * Created by jyx on 2020/11/5
 *
 * @author jyx
 * @describe
 */
public class MyClickableSpan extends ClickableSpan {
    public MyClickableSpan() {
    }

    @Override
    public void onClick(@NonNull View widget) {

    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }
}
