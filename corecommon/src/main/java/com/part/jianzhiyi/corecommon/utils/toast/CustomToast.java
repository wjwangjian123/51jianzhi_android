package com.part.jianzhiyi.corecommon.utils.toast;

import android.text.TextUtils;
import android.widget.Toast;

import com.part.jianzhiyi.corecommon.utils.Tools;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/9/23
 * @modified by:shixinxin
 **/
public class CustomToast {

    public static void normalToast(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast toast = Toast.makeText(Tools.getApplicationByReflection(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}
