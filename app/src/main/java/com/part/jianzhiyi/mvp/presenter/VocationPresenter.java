package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.model.entity.JobDetailEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.JoinJobEntity;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.VocationEntity;
import com.part.jianzhiyi.mvp.contract.VocationContract;
import com.part.jianzhiyi.mvp.model.VocationModel;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;


/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/13
 * @modified by:shixinxin
 **/
public class VocationPresenter extends BasePresenter<VocationContract.IVocationModel, VocationContract.IVocationView> {

    public VocationPresenter(VocationContract.IVocationView mView) {
        super(mView, new VocationModel());
    }

    public void jobDetail(String jobId, String position, String sortId) {
        mModel.jobDetail(jobId, position, sortId)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<VocationEntity>>() {
                    @Override
                    public void onNext(ResponseData<VocationEntity> responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                VocationEntity entity = responseData.getData();
                                VocationEntity.InfoBean info = entity.getInfo();
                                List<VocationEntity.LoveBean> love = entity.getLove();
//                                weakReferenceView.get().updateEntity(info);
//                                weakReferenceView.get().updateRecommend(love);

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("retrofit", "详情error:" + e.getMessage());
                    }
                }));
    }

    public void recommendList(String jobId) {
        mModel.recommendList(jobId)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<List<JobListResponseEntity>>>() {
                    @Override
                    public void onNext(ResponseData<List<JobListResponseEntity>> jobListResponseEntityResponseData) {
                        if (TextUtils.equals(jobListResponseEntityResponseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                            }
                        }
                    }
                }));
    }


    public void addFavourite(String jobId, String sortId) {
        String userId = PreferenceUUID.getInstence().getUserId();
        if (TextUtils.isEmpty(userId)) {
            if (isAttach()) {
                weakReferenceView.get().reStartLogin();
                return;
            }
        }
        mModel.addFavourite(userId, jobId, sortId)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<AddFavouriteResponseEntity>>() {
                    @Override
                    public void onNext(ResponseData<AddFavouriteResponseEntity> stringResponseData) {
                        if (stringResponseData.getCode().equals(HttpAPI.REQUEST_SUCCESS)) {
                            weakReferenceView.get().favouriteSuccess();
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().showToast(stringResponseData.getMsg());
                            }
                        }
                    }
                }));
    }

    public void cancelFavourite(String jobId, String sortId) {
        String userId = PreferenceUUID.getInstence().getUserId();
        if (TextUtils.isEmpty(userId)) {
            if (isAttach()) {
                weakReferenceView.get().reStartLogin();
                return;
            }
        }
        mModel.cancelFavourite(userId, jobId, sortId)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<AddFavouriteResponseEntity>>() {
                    @Override
                    public void onNext(ResponseData<AddFavouriteResponseEntity> stringResponseData) {
                        if (stringResponseData.getCode().equals(HttpAPI.REQUEST_SUCCESS)) {
                            weakReferenceView.get().cancelFavoriteSuccess();
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().showToast(stringResponseData.getMsg());
                            }
                        }
                    }
                }));
    }

    public void join(String jobId, String sortId, String contact) {
        String userId = PreferenceUUID.getInstence().getUserId();
        if (TextUtils.isEmpty(userId)) {
            if (isAttach()) {
                weakReferenceView.get().reStartLogin();
                return;
            }
        }
        mModel.joinJob(userId, jobId, sortId, contact)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<AddFavouriteResponseEntity>>() {
                    @Override
                    public void onNext(ResponseData<AddFavouriteResponseEntity> stringResponseData) {
                        if (stringResponseData.getCode().equals(HttpAPI.REQUEST_SUCCESS)) {
//                            weakReferenceView.get().joinSuccess();
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().showToast(stringResponseData.getMsg());
                            }
                        }
                    }
                }));
    }

    public void joinJobV2(String jobId, String sortId, String contact) {
        String userId = PreferenceUUID.getInstence().getUserId();
        if (TextUtils.isEmpty(userId)) {
            if (isAttach()) {
                weakReferenceView.get().reStartLogin();
                return;
            }
        }
        mModel.joinJobV2(userId, jobId, sortId, contact)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<JoinJobEntity>() {
                    @Override
                    public void onNext(JoinJobEntity stringResponseData) {
                        if (stringResponseData.getCode().equals(HttpAPI.REQUEST_SUCCESS)) {
                            weakReferenceView.get().joinSuccess(stringResponseData);
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().showToast(stringResponseData.getMsg());
                            }
                        }
                    }
                }));
    }

    public void jobDetailv(String jobId, String position, String sortId) {
        mModel.jobDetailv(jobId, position, sortId)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<JobDetailEntity>() {
                    @Override
                    public void onNext(JobDetailEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                JobDetailEntity.DataBean data = responseData.getData();
                                JobDetailEntity.DataBean.InfoBean info = data.getInfo();
                                List<JobDetailEntity.DataBean.LoveBean> love = data.getLove();
                                List<JobDetailEntity.DataBean.JobListBean> job_list = data.getJob_list();
                                weakReferenceView.get().updateEntity(info);
                                weakReferenceView.get().updateRecommend(love);
                                weakReferenceView.get().updateJobList(job_list);
                            }
                        } else {
                            weakReferenceView.get().updateErrorTip(responseData.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("retrofit", "详情error:" + e.getMessage());
                    }
                }));
    }

    public void getSussOrErrLog(String type, String status) {
        mModel.getSussOrErrLog(type, status)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                            }
                        }
                    }
                }));
    }

    public void joincopyContact(String jobId, String sortId, String contact, int type) {
        String userId = PreferenceUUID.getInstence().getUserId();
        if (TextUtils.isEmpty(userId)) {
            if (isAttach()) {
                weakReferenceView.get().reStartLogin();
                return;
            }
        }
        mModel.joincopyContact(Constants.APPID, userId, jobId, sortId, contact, type)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<AddFavouriteResponseEntity>>() {
                    @Override
                    public void onNext(ResponseData<AddFavouriteResponseEntity> stringResponseData) {
                        Log.d("testaaa", "onNext: " + stringResponseData);
                        if (stringResponseData.getCode().equals(HttpAPI.REQUEST_SUCCESS)) {
//                            weakReferenceView.get().joinSuccess();
                        } else {
                        }
                    }
                }));
    }

    public LoginResponseEntity loadUserInfo() {
        return mModel.loadUserInfo();
    }

    public boolean isWirteIntro() {
        return !TextUtils.isEmpty(mModel.loadUserInfo().getIntroduce());
    }

    public boolean isWirteAge() {
        return !TextUtils.isEmpty(mModel.loadUserInfo().getAge());
    }

    public String isIntroAge() {
        return mModel.loadUserInfo().getAge();
    }
}
