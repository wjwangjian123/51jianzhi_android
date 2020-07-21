package com.part.jianzhiyi.netcommon.net;

import android.content.Context;

/**
 * Created by jyx on 2019/9/29
 *
 * @author jyx
 * @describe activity的管理类，获取activity
 */
public class NetManager {
    public static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }
}
