package com.part.jianzhiyi.corecommon.utils;

import com.part.jianzhiyi.corecommon.constants.ConstantsDimens;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/9/25
 * @modified by:shixinxin
 **/
public class UiDimens {
    public static void initPadding() {
        int height = Tools.getApplicationByReflection().getResources().getDisplayMetrics().heightPixels;
        int width = Tools.getApplicationByReflection().getResources().getDisplayMetrics().widthPixels;
        ConstantsDimens.isUpdate = true;
        //屏幕宽
        ConstantsDimens.SCREEN_WIDTH = width;
        //屏幕高
        ConstantsDimens.SCREEN_HEIGHT = height;
        ConstantsDimens.EDITE_HEIGHT = width / 6 - width / 72;
        ConstantsDimens.NORMAL_PADDING = width / 36;

        //button的高度
        ConstantsDimens.BUTTON_HEIGHT = width / 9;

        ConstantsDimens.BUTTON_WIDTH = width / 9 * 7;

        ConstantsDimens.BUTTON_PADDING = width / 9;
        ConstantsDimens.BUTTON_MARGIN = width / 12;
        ;


    }
}
