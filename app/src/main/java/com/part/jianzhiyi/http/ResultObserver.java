package com.part.jianzhiyi.http;


import android.widget.Toast;

import com.part.jianzhiyi.corecommon.utils.NetworkUtils;
import com.part.jianzhiyi.corecommon.utils.Tools;

import io.reactivex.observers.DisposableObserver;

/**
 * @author
 * @Description: 自定义 用于管理 rxJava 订阅和取消订阅
 * @Version: 1.0
 */
public abstract class ResultObserver<T> extends DisposableObserver<T> {

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {
        if (!NetworkUtils.checkNetworkConnect(Tools.getApplicationByReflection().getApplicationContext())) {//没有网络的 时候
            Toast.makeText(Tools.getApplicationByReflection().getApplicationContext(), "当前网络不可用，请检查网络状态", Toast.LENGTH_SHORT).show();
        }
    }
}
