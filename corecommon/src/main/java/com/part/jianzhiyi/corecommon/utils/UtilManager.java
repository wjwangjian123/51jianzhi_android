package com.part.jianzhiyi.corecommon.utils;

import android.content.Context;

/**
 * Created by jyx on 2019/3/29
 *
 * @author jyx
 * @describe
 */
public class UtilManager {
    private static Context mcontext;

    public static void init(Context context) {
        mcontext = context;
//        FrescoUtil.initialize(mcontext);
    }

    public static Context getContext() {
        return mcontext;
    }
}
