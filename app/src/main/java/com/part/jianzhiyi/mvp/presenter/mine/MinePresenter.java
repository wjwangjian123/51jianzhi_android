package com.part.jianzhiyi.mvp.presenter.mine;

import android.text.TextUtils;
import android.util.Log;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
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
                .subscribe(getResult(new ResultObserver<ResponseData<LoginResponseEntity>>() {
                    @Override
                    public void onNext(ResponseData<LoginResponseEntity> responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                PreferenceUUID.getInstence().changeShowResume(responseData.getData().getShowResume());
                                LoginResponseEntity loginResponseEntity = responseData.getData();
                                mModel.insertOrUpdateDb(loginResponseEntity);
                                weakReferenceView.get().updateUserInfoPer(loginResponseEntity);
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
}
