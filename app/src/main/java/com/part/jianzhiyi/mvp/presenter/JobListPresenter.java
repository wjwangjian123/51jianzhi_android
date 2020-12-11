package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.model.entity.ViewedEntity;
import com.part.jianzhiyi.mvp.contract.ChoiceContract;
import com.part.jianzhiyi.mvp.model.JobListModel;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;

import java.util.List;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class JobListPresenter extends BasePresenter<ChoiceContract.IChoiceModel, ChoiceContract.IChoiceView> {
    public JobListPresenter(ChoiceContract.IChoiceView mView) {
        super(mView, new JobListModel());
    }

    public void jobList(String type, String position, int page,String label) {
        mModel.jobList(PreferenceUUID.getInstence().getUserId(), type, position, page,label)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<JobListResponseEntity2>>() {
                    @Override
                    public void onNext(ResponseData<JobListResponseEntity2> responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                JobListResponseEntity2 data = responseData.getData();
                                weakReferenceView.get().updateNewList(position, data.getData());
                                weakReferenceView.get().updateAdvertising(position, data.getAdvertising());
                            }
                        }
                    }
                }));
    }

    public void inviteJob() {
        mModel.inviteJob(PreferenceUUID.getInstence().getUserId())
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<List<JobListResponseEntity>>>() {
                    @Override
                    public void onNext(ResponseData<List<JobListResponseEntity>> jobListResponseEntityResponseData) {
                        if (TextUtils.equals(jobListResponseEntityResponseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updateList(jobListResponseEntityResponseData.getData());
                            }
                        }
                    }
                }));
    }

    public void joinedJob() {
        mModel.joinedJob(PreferenceUUID.getInstence().getUserId())
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<List<JobListResponseEntity>>>() {
                    @Override
                    public void onNext(ResponseData<List<JobListResponseEntity>> jobListResponseEntityResponseData) {
                        if (TextUtils.equals(jobListResponseEntityResponseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updateList(jobListResponseEntityResponseData.getData());
                            }
                        }
                    }
                }));
    }

    public void approvedJob() {
        mModel.approvedJob(PreferenceUUID.getInstence().getUserId())
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<List<JobListResponseEntity>>>() {
                    @Override
                    public void onNext(ResponseData<List<JobListResponseEntity>> jobListResponseEntityResponseData) {
                        if (TextUtils.equals(jobListResponseEntityResponseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updateList(jobListResponseEntityResponseData.getData());
                            }
                        }
                    }
                }));
    }


    public void arrivedJob() {
        mModel.arrivedJob(PreferenceUUID.getInstence().getUserId())
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<List<JobListResponseEntity>>>() {
                    @Override
                    public void onNext(ResponseData<List<JobListResponseEntity>> jobListResponseEntityResponseData) {
                        if (TextUtils.equals(jobListResponseEntityResponseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updateList(jobListResponseEntityResponseData.getData());
                            }
                        }
                    }
                }));
    }

    public void donedJob() {
        mModel.donedJob(PreferenceUUID.getInstence().getUserId())
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<List<JobListResponseEntity>>>() {
                    @Override
                    public void onNext(ResponseData<List<JobListResponseEntity>> jobListResponseEntityResponseData) {
                        if (TextUtils.equals(jobListResponseEntityResponseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updateList(jobListResponseEntityResponseData.getData());
                            }
                        }
                    }
                }));
    }

    public void viewedJob() {
        mModel.viewedJob(PreferenceUUID.getInstence().getUserId())
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ViewedEntity>() {
                    @Override
                    public void onNext(ViewedEntity viewedEntity) {
                        if (TextUtils.equals(viewedEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updateviewedJob(viewedEntity);
                            }
                        }
                    }
                }));
    }

    public void getaddMd(String type) {
        mModel.getaddMd(type)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetaddMd(responseData);
                            }
                        }
                    }
                }));
    }
}
