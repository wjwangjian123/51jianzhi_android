package com.part.jianzhiyi.modulemerchants.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.http.ResultObserver;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MAuthInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MEnterpriseInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MFileEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MGetEnterpriseInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MIDFaPositiveEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MIDPositiveEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.AuthContract;
import com.part.jianzhiyi.modulemerchants.mvp.model.AuthModel;

import java.util.Map;

import okhttp3.RequestBody;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class AuthPresenter extends BasePresenter<AuthContract.IAuthModel, AuthContract.IAuthView> {
    public AuthPresenter(AuthContract.IAuthView mView) {
        super(mView, new AuthModel());
    }

    public void getBaidNumber(RequestBody requestBody) {
        mModel.getBaidNumber(requestBody)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<MIDPositiveEntity>() {
                    @Override
                    public void onNext(MIDPositiveEntity midPositiveEntity) {
                        if (TextUtils.equals(midPositiveEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetBaidNumber(midPositiveEntity);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetBaidNumber(midPositiveEntity);
                            }
                        }
                    }
                }));
    }

    public void getCheckEnterprise(Map<String, Object> obj) {
        mModel.getCheckEnterprise(obj)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetCheckEnterprise(responseData);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetCheckEnterprise(responseData);
                            }
                        }
                    }
                }));
    }

    public void getNumidSuccess(String img_z, String name, String number, String img_f, String company) {
        mModel.getNumidSuccess(img_z, name, number, img_f, company)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetNumidSuccess(responseData);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetNumidSuccess(responseData);
                            }
                        }
                    }
                }));
    }

    public void getEnterprise(RequestBody requestBody) {
        mModel.getEnterprise(requestBody)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<MEnterpriseInfoEntity>() {
                    @Override
                    public void onNext(MEnterpriseInfoEntity mEnterpriseInfoEntity) {
                        if (TextUtils.equals(mEnterpriseInfoEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetEnterprise(mEnterpriseInfoEntity);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetEnterprise(mEnterpriseInfoEntity);
                            }
                        }
                    }
                }));
    }

    public void getCorporate(RequestBody requestBody) {
        mModel.getCorporate(requestBody)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<MIDFaPositiveEntity>() {
                    @Override
                    public void onNext(MIDFaPositiveEntity midFaPositiveEntity) {
                        if (TextUtils.equals(midFaPositiveEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetCorporate(midFaPositiveEntity);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetCorporate(midFaPositiveEntity);
                            }
                        }
                    }
                }));
    }

    public void getNumid() {
        mModel.getNumid()
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<MAuthInfoEntity>() {
                    @Override
                    public void onNext(MAuthInfoEntity mAuthInfoEntity) {
                        if (TextUtils.equals(mAuthInfoEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetNumid(mAuthInfoEntity);
                            }
                        }
                    }
                }));
    }

    public void getUpload(RequestBody requestBody) {
        mModel.getUpload(requestBody)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<MFileEntity>() {
                    @Override
                    public void onNext(MFileEntity mFileEntity) {
                        if (TextUtils.equals(mFileEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetUpload(mFileEntity);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetUpload(mFileEntity);
                            }
                        }
                    }
                }));
    }

    public void getEnterpriseInfo() {
        mModel.getEnterpriseInfo()
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<MGetEnterpriseInfoEntity>() {
                    @Override
                    public void onNext(MGetEnterpriseInfoEntity mGetEnterpriseInfoEntity) {
                        if (TextUtils.equals(mGetEnterpriseInfoEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetEnterpriseInfo(mGetEnterpriseInfoEntity);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetEnterpriseInfo(mGetEnterpriseInfoEntity);
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
