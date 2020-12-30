package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.mvp.contract.AboutContract;
import com.part.jianzhiyi.mvp.model.AboutModel;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class AboutPresenter extends BasePresenter<AboutContract.IAboutModel, AboutContract.IAboutView> {
    public AboutPresenter(AboutContract.IAboutView mView) {
        super(mView, new AboutModel());
    }

    public void getConfig() {
        mModel.getConfig()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ConfigEntity>() {
                    @Override
                    public void onNext(ConfigEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetConfig(responseData);
                            }
                        }
                    }
                }));
    }
}
