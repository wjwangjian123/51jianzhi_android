package com.part.jianzhiyi.mvp.presenter.user;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.corecommon.utils.RegularUtils;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.UMEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.mvp.contract.user.LoginContract;
import com.part.jianzhiyi.mvp.model.user.LoginModel;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;

import androidx.annotation.Nullable;

/**
 * @author:
 * @content：登录
 * @vision:V1.0.1
 **/
public class LoginPresenter extends BasePresenter<LoginContract.ILoginModel, LoginContract.ILoginView> {
    public LoginPresenter(@Nullable LoginContract.ILoginView mView) {
        super(mView, new LoginModel());
    }


    public boolean sendSmsCode(String phone) {
        if (TextUtils.isEmpty(phone) || !RegularUtils.isMobileNumber(phone)) {
            showToast("请填写正确的手机号");
            return false;
        }
        mModel.sendSms(phone)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<String>>() {
                    @Override
                    public void onNext(ResponseData<String> responseData) {
                        if (responseData != null) {
                            String code = responseData.getCode();
                            if (code.equals(HttpAPI.REQUEST_SUCCESS)) {
                                if (isAttach()) {
                                    weakReferenceView.get().updatesendSms(responseData);
                                }
                            } else {
                                weakReferenceView.get().updatesendSms(responseData);
                            }
                        }
                    }
                }));
        return true;
    }

    public void login(String phone, String code) {
        mModel.login(phone, code)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<String>() {
                    @Override
                    public void onNext(String json) {
                        ResponseData<String> responseData = JSON.parseObject(json, ResponseData.class);
                        if (responseData != null) {
                            String code = responseData.getCode();
                            if (code.equals(HttpAPI.REQUEST_SUCCESS)) {
                                if (isAttach()) {
                                    String data = JSON.parseObject(json).getString("data");
                                    LoginResponseEntity entity = JSON.parseObject(data, LoginResponseEntity.class);
//                                    GreenDaoManager.getInstance().getDaoSession().getLoginResponseEntityDao().insertOrReplace(responseData.getData());
                                    mModel.insertOrUpdateDb(entity);
                                    PreferenceUUID.getInstence().putUserId(entity.id);
                                    PreferenceUUID.getInstence().putUserPhone(entity.getPhone());
                                    PreferenceUUID.getInstence().loginIn();
                                    PreferenceUUID.getInstence().changeShowResume(entity.getShowResume());
                                    weakReferenceView.get().startIntent();
                                    weakReferenceView.get().updatelogin(entity);
                                }
                            } else {
                                weakReferenceView.get().showToast(responseData.getMsg());
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

    public void verifys(String token) {
        mModel.verifys(token)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<UMEntity>() {
                    @Override
                    public void onNext(UMEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updateverifys(responseData);
                            }
                        }
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
                        Log.e("tag", "解析异常");
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
