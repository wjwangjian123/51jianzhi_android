package com.part.jianzhiyi.modulemerchants.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.http.ResultObserver;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MSetContract;
import com.part.jianzhiyi.modulemerchants.mvp.model.MSetModel;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class MSetPresenter extends BasePresenter<MSetContract.ISetModel, MSetContract.ISetView> {
    public MSetPresenter(MSetContract.ISetView mView) {
        super(mView, new MSetModel());
    }

    public void getPassword(String type, String pass, String new_pass, String old_pass, String code) {
        mModel.getPassword(type, pass, new_pass, old_pass, code)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetPassword(responseData);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetPassword(responseData);
                            }
                        }
                    }
                }));
    }

    public void getCode() {
        mModel.getCode()
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetCode(responseData);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetCode(responseData);
                            }
                        }
                    }
                }));
    }
}
