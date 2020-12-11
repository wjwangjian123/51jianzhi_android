package com.part.jianzhiyi.modulemerchants.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.http.ResultObserver;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MBannerEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobListEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MHomeContract;
import com.part.jianzhiyi.modulemerchants.mvp.model.MHomeModel;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class MHomePresenter extends BasePresenter<MHomeContract.IMHomeModel, MHomeContract.IMHomeView> {
    public MHomePresenter(MHomeContract.IMHomeView mView) {
        super(mView, new MHomeModel());
    }

    public void getIndexBanner() {
        mModel.getIndexBanner()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MBannerEntity>() {
                    @Override
                    public void onNext(MBannerEntity mBannerEntity) {
                        if (TextUtils.equals(mBannerEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetIndexBanner(mBannerEntity);
                            }
                        }
                    }
                }));
    }

    public void getJobRefresh(String job_id) {
        mModel.getJobRefresh(job_id)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetJobRefresh(responseData);
                            }
                        }else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetJobRefresh(responseData);
                            }
                        }
                    }
                }));
    }

    public void getJobSx(int status, String job_id) {
        mModel.getJobSx(status, job_id)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetJobSx(responseData);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetJobSx(responseData);
                            }
                        }
                    }
                }));
    }

    public void getMJobInfo(String id) {
        mModel.getMJobInfo(id)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MJobInfoEntity>() {
                    @Override
                    public void onNext(MJobInfoEntity mJobInfoEntity) {
                        if (TextUtils.equals(mJobInfoEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetMJobInfo(mJobInfoEntity);
                            }
                        }
                    }
                }));
    }

    public void getMJobList(String type, String start_time, String end_time) {
        mModel.getMJobList(type, start_time, end_time)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MJobListEntity>() {
                    @Override
                    public void onNext(MJobListEntity mJobListEntity) {
                        if (TextUtils.equals(mJobListEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetMJobList(mJobListEntity);
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
