package com.part.jianzhiyi.modulemerchants.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.http.ResultObserver;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MSwitchMerchantsEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.ChooseContract;
import com.part.jianzhiyi.modulemerchants.mvp.model.ChooseModel;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class ChoosePresenter extends BasePresenter<ChooseContract.IChooseModel, ChooseContract.IChooseView> {
    public ChoosePresenter(ChooseContract.IChooseView mView) {
        super(mView, new ChooseModel());
    }

    public void getUserChabge(String phone) {
        mModel.getUserChabge(phone)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<MSwitchMerchantsEntity>() {
                    @Override
                    public void onNext(MSwitchMerchantsEntity mSwitchMerchantsEntity) {
                        if (TextUtils.equals(mSwitchMerchantsEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            PreferenceUUID.getInstence().putToken(mSwitchMerchantsEntity.getData().getToken());
                            if (isAttach()) {
                                weakReferenceView.get().updategetUserChabge(mSwitchMerchantsEntity);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetUserChabge(mSwitchMerchantsEntity);
                            }
                        }
                    }
                }));
    }

    public void getmdAdd(String type) {
        mModel.getmdAdd(type)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetmdAdd(responseData);
                            }
                        }
                    }
                }));
    }
}
