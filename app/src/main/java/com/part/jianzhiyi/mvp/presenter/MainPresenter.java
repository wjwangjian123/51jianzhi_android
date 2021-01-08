package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.ActJobListEntity;
import com.part.jianzhiyi.model.entity.ActivityEntity;
import com.part.jianzhiyi.model.entity.CityIdEntity;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.model.entity.DelUserEntity;
import com.part.jianzhiyi.model.entity.IpResponseEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
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

    public void getAddInteg(String user_id, int type, String job_id) {
        mModel.getAddInteg(user_id, type, job_id)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<SignInfoEntity>() {
                    @Override
                    public void onNext(SignInfoEntity entity) {
                        if (TextUtils.equals(entity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetAddInteg(entity);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetAddInteg(entity);
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

    public void getIsDel() {
        mModel.getIsDel()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<DelUserEntity>() {
                    @Override
                    public void onNext(DelUserEntity delUserEntity) {
                        if (TextUtils.equals(delUserEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetIsDel(delUserEntity);
                            }
                        }
                    }
                }));
    }

    public void userInfo(String userid) {
        mModel.userInfo(userid)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<UserInfoEntity>() {
                    @Override
                    public void onNext(UserInfoEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                PreferenceUUID.getInstence().changeShowResume(responseData.getData().isShowResume());
                                weakReferenceView.get().updateUserInfoPer(responseData);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.i("tag", "解析异常");
                    }
                }));
    }

    public void getCityId(String city_name, String mcityName, int mcityId) {
        mModel.getCityId(city_name)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<CityIdEntity>() {
                    @Override
                    public void onNext(CityIdEntity cityIdEntity) {
                        if (TextUtils.equals(cityIdEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetCityId(cityIdEntity, mcityName, mcityId);
                            }
                        }
                    }
                }));
    }

    public void getUserCity(int city_id, String user_id) {
        mModel.getUserCity(city_id, user_id)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetUserCity(responseData);
                            }
                        }
                    }
                }));
    }
}
