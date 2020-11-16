package com.part.jianzhiyi.mvp.presenter.mine;

import android.text.TextUtils;
import android.util.Log;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddSignEntity;
import com.part.jianzhiyi.model.entity.DaySignEntity;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.mvp.contract.user.MineContract;
import com.part.jianzhiyi.mvp.model.user.MineModel;
import com.part.jianzhiyi.preference.PreferenceUUID;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class MinePresenter extends BasePresenter<MineContract.IMineModel, MineContract.IMineView> {
    public MinePresenter(MineContract.IMineView mView) {
        super(mView, new MineModel());
    }

    public boolean isUserLogin() {
        return PreferenceUUID.getInstence().isUserLogin();
    }

    public void loadUserInfo() {
        LoginResponseEntity loginResponseEntity = mModel.loadUserInfo();
        if (loginResponseEntity != null && isAttach()) {
            weakReferenceView.get().updateUserInfo(loginResponseEntity);
        } else {
            PreferenceUUID.getInstence().loginOut();
        }

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

    public void getDaySign(String user_id) {
        mModel.getDaySign(user_id)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<DaySignEntity>() {
                    @Override
                    public void onNext(DaySignEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetDaySign(responseData);
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

    public void addDaySign(String user_id,String day) {
        mModel.addDaySign(user_id, day)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<AddSignEntity>() {
                    @Override
                    public void onNext(AddSignEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updateaddDaySign(responseData);
                            }
                        }else {
                            weakReferenceView.get().updateaddDaySign(responseData);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.i("tag", "解析异常");
                    }
                }));
    }
}
