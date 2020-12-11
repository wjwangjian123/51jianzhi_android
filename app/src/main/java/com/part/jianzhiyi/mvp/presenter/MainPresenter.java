package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.ActJobListEntity;
import com.part.jianzhiyi.model.entity.ActivityEntity;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.model.entity.IpResponseEntity;
import com.part.jianzhiyi.mvp.contract.MainContract;
import com.part.jianzhiyi.mvp.model.MainModel;

import androidx.annotation.Nullable;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/9/22
 * @modified by:shixinxin
 **/
public class MainPresenter extends BasePresenter<MainContract.IMainModel, MainContract.IMainView> {

    public MainPresenter(@Nullable MainContract.IMainView mView) {
        super(mView, new MainModel());
    }

    public void getIp() {
        mModel.getIp()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<IpResponseEntity>() {
                    @Override
                    public void onNext(IpResponseEntity jobListResponseEntityResponseData) {
                        if (isAttach()) {
                            HttpAPI.getInstence().refreshIp(jobListResponseEntityResponseData.getQuery());
                        }
                    }

                }));
    }

    public void getAction() {
        mModel.getAction()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ActivityEntity>() {
                    @Override
                    public void onNext(ActivityEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetAction(responseData);
                            }
                        }
                    }
                }));
    }

    public void getActJobList(String id, String type) {
        mModel.getActJobList(id, type)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ActJobListEntity>() {
                    @Override
                    public void onNext(ActJobListEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetActJobList(responseData);
                            }
                        }
                    }
                }));
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
