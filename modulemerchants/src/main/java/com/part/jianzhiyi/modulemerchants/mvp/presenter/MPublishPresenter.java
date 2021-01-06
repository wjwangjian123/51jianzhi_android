package com.part.jianzhiyi.modulemerchants.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.http.ResultObserver;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableContactEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableSalaryEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MPublishContract;
import com.part.jianzhiyi.modulemerchants.mvp.model.MPublishModel;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class MPublishPresenter extends BasePresenter<MPublishContract.IMPublishModel, MPublishContract.IMPublishView> {
    public MPublishPresenter(MPublishContract.IMPublishView mView) {
        super(mView, new MPublishModel());
    }

    public void getMLabel(String type) {
        mModel.getMLabel(type)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MLableEntity>() {
                    @Override
                    public void onNext(MLableEntity mLableEntity) {
                        if (TextUtils.equals(mLableEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetMLabel(mLableEntity);
                            }
                        }
                    }
                }));
    }

    public void getMLabelMethod(String type) {
        mModel.getMLabelMethod(type)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MLableSalaryEntity>() {
                    @Override
                    public void onNext(MLableSalaryEntity mLableSalaryEntity) {
                        if (TextUtils.equals(mLableSalaryEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetMLabelMethod(mLableSalaryEntity);
                            }
                        }
                    }
                }));
    }

    public void getMLabelSalary(String type) {
        mModel.getMLabelSalary(type)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MLableSalaryEntity>() {
                    @Override
                    public void onNext(MLableSalaryEntity mLableSalaryEntity) {
                        if (TextUtils.equals(mLableSalaryEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetMLabelSalary(mLableSalaryEntity);
                            }
                        }
                    }
                }));
    }

    public void getMLabelContact(String type) {
        mModel.getMLabelContact(type)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MLableContactEntity>() {
                    @Override
                    public void onNext(MLableContactEntity mLableContactEntity) {
                        if (TextUtils.equals(mLableContactEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetMLabelContact(mLableContactEntity);
                            }
                        }
                    }
                }));
    }

    public void getIsSing() {
        mModel.getIsSing()
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetIsSing(responseData);
                            }
                        }
                    }
                }));
    }

    public void getCheckJob(String label_id, String job_id, String ther) {
        mModel.getCheckJob(label_id, job_id, ther)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetCheckJob(responseData);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetCheckJob(responseData);
                            }
                        }
                    }
                }));
    }

    public void getAddJob(String title, String method, String time, String sex, String price, String content, String contact, int contact_type, String number, String place, String duration, String type, String id, String label_id, String price2, String age1, String age2) {
        mModel.getAddJob(title, method, time, sex, price, content, contact, contact_type, number, place, duration, type, id, label_id, price2, age1, age2)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetAddJob(responseData);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetAddJob(responseData);
                            }
                        }
                    }
                }));
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

    public void getTextFilter(String title, String content, String duration, String place, String contact) {
        mModel.getTextFilter(title, content, duration, place, contact)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetTextFilter(responseData);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetTextFilter(responseData);
                            }
                        }
                    }
                }));
    }

}
