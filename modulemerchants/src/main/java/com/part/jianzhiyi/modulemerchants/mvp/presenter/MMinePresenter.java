package com.part.jianzhiyi.modulemerchants.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.http.ResultObserver;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MConfigEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MMineContract;
import com.part.jianzhiyi.modulemerchants.mvp.model.MMineModel;

import okhttp3.RequestBody;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class MMinePresenter extends BasePresenter<MMineContract.IMMineModel, MMineContract.IMMineView> {
    public MMinePresenter(MMineContract.IMMineView mView) {
        super(mView, new MMineModel());
    }

    public void getMerUserinfo() {
        mModel.getMerUserinfo()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MUserInfoEntity>() {
                    @Override
                    public void onNext(MUserInfoEntity mUserInfoEntity) {
                        if (TextUtils.equals(mUserInfoEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetMerUserinfo(mUserInfoEntity);
                            }
                        }
                    }
                }));
    }

    public void getAvatar(RequestBody requestBody) {
        mModel.getAvatar(requestBody)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetAvatar(responseData);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetAvatar(responseData);
                            }
                        }
                    }
                }));
    }

    public void getOpinion(String reason, String con, String phone) {
        mModel.getOpinion(reason, con, phone)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetOpinion(responseData);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetOpinion(responseData);
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

    public void getCheck() {
        mModel.getCheck()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MCheckVersionEntity>() {
                    @Override
                    public void onNext(MCheckVersionEntity mCheckVersionEntity) {
                        if (TextUtils.equals(mCheckVersionEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetCheck(mCheckVersionEntity);
                            }
                        }
                    }
                }));
    }

    public void getConfig() {
        mModel.getConfig()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MConfigEntity>() {
                    @Override
                    public void onNext(MConfigEntity mConfigEntity) {
                        if (TextUtils.equals(mConfigEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetConfig(mConfigEntity);
                            }
                        }
                    }
                }));
    }
}
