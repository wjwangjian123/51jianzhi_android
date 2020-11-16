package com.part.jianzhiyi.mvp.presenter.mine;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.corecommon.utils.RegularUtils;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.mvp.contract.mine.MineFeekbackContract;
import com.part.jianzhiyi.mvp.model.mine.MineFeekbackModel;
import com.part.jianzhiyi.preference.PreferenceUUID;

/**
 * @author:
 * @content：我的信息
 * @vision:V1.0.1
 **/
public class MineFeekbackPresenter extends BasePresenter<MineFeekbackContract.IMineFeekbackModel, MineFeekbackContract.IMineFeekbackView> {
    public MineFeekbackPresenter(MineFeekbackContract.IMineFeekbackView mView) {
        super(mView, new MineFeekbackModel());
    }


    public void jobfeedback(String content, String contact) {
        String userId = PreferenceUUID.getInstence().getUserId();
        if (TextUtils.isEmpty(userId)) {
            if (isAttach()) {
                weakReferenceView.get().reStartLogin();
                return;
            }
        }
        if (TextUtils.isEmpty(content)) {
            if (isAttach()) {
                weakReferenceView.get().showToast("请填写反馈详情");
                return;
            }
        }
        if (TextUtils.isEmpty(contact) || !RegularUtils.isEmail(contact)) {
            showToast("请填写正确的联系方式");
            return;
        }

        mModel.jobfeedback(userId, content, contact)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<AddFavouriteResponseEntity>>() {
                    @Override
                    public void onNext(ResponseData<AddFavouriteResponseEntity> stringResponseData) {
                        if (stringResponseData.getCode().equals(HttpAPI.REQUEST_SUCCESS)) {
                            weakReferenceView.get().showToast("反馈成功");
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
