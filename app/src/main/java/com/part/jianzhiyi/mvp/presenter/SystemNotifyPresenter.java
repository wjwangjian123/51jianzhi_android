package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.MsgResponseEntity;
import com.part.jianzhiyi.mvp.contract.SystemNotifyContract;
import com.part.jianzhiyi.mvp.model.SystemNotifyModel;
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
public class SystemNotifyPresenter extends BasePresenter<SystemNotifyContract.ISystemNotifyModel, SystemNotifyContract.ISystemNotifyView> {

    public SystemNotifyPresenter(SystemNotifyContract.ISystemNotifyView mView) {
        super(mView, new SystemNotifyModel());
    }

    public void msgList() {
        mModel.msgList(PreferenceUUID.getInstence().getUserId())
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<List<MsgResponseEntity>>>() {
                    @Override
                    public void onNext(ResponseData<List<MsgResponseEntity>> jobListResponseEntityResponseData) {
                        if (TextUtils.equals(jobListResponseEntityResponseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updateList(jobListResponseEntityResponseData.getData());
                            }
                        }
                    }

                }));
    }

}
