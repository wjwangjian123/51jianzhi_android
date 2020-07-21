package com.part.jianzhiyi.mvp.presenter.mine;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.corecommon.utils.RegularUtils;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.request.UpdateProfileRequest;
import com.part.jianzhiyi.mvp.contract.mine.MineUpdateProfileContract;
import com.part.jianzhiyi.mvp.model.mine.MineUpdateProfileModel;
import com.part.jianzhiyi.preference.PreferenceUUID;

/**
 * @author:
 * @content：我的信息
 * @vision:V1.0.1
 **/
public class MineUpdateProfilePresenter extends BasePresenter<MineUpdateProfileContract.IMineUpdateProfileModel, MineUpdateProfileContract.IMineUpdateProfileView> {
    public MineUpdateProfilePresenter(MineUpdateProfileContract.IMineUpdateProfileView mView) {
        super(mView, new MineUpdateProfileModel());
    }

    public void loadUserInfo() {
        LoginResponseEntity loginResponseEntity = mModel.loadUserInfo();
        if (loginResponseEntity != null && isAttach()) {
            weakReferenceView.get().updateUserInfo(loginResponseEntity);
        } else {
            PreferenceUUID.getInstence().loginOut();
        }

    }

    public void updateProfile(String userName, String signature, String phone, String email) {
        if (TextUtils.isEmpty(userName)) {
            if (isAttach()) {
                weakReferenceView.get().showToast("请填写用户昵称");
                return;
            }
        }
        if (TextUtils.isEmpty(signature)) {
            if (isAttach()) {
                weakReferenceView.get().showToast("请填写个人签名");
                return;
            }
        }
        if (TextUtils.isEmpty(phone) || !RegularUtils.isMobileNumber(phone)) {
            showToast("请填写正确的手机号");
            return;
        }

        if (TextUtils.isEmpty(email) || !RegularUtils.isEmail(email)) {
            showToast("请填写正确的邮箱");
            return;
        }
        UpdateProfileRequest request = new UpdateProfileRequest(PreferenceUUID.getInstence().getUserId(), userName, signature, phone, email);
        mModel.updateProfile(request)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<String>>() {
                    @Override
                    public void onNext(ResponseData<String> stringResponseData) {
                        if (stringResponseData.getCode().equals(HttpAPI.REQUEST_SUCCESS)) {
                            LoginResponseEntity loginResponseEntity = mModel.loadUserInfo();
                            loginResponseEntity.setUsername(userName);
                            loginResponseEntity.setEmail(email);
                            loginResponseEntity.setSignature(signature);
                            loginResponseEntity.setPhone(phone);
                            mModel.insertOrUpdateDb(loginResponseEntity);
                            weakReferenceView.get().updateSuccess();
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().showToast(stringResponseData.getMsg());
                            }
                        }
                    }
                }));
    }
}
