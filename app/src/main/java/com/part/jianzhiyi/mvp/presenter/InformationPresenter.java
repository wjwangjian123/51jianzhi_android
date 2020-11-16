package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.model.entity.ChatJobInfoEntity;
import com.part.jianzhiyi.model.entity.MsgResponseEntity;
import com.part.jianzhiyi.model.entity.ViewedEntity;
import com.part.jianzhiyi.mvp.contract.InformationContract;
import com.part.jianzhiyi.mvp.model.InformationModel;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class InformationPresenter extends BasePresenter<InformationContract.IInformationModel, InformationContract.IInformationView> {
    public InformationPresenter(InformationContract.IInformationView mView) {
        super(mView, new InformationModel());
    }

    public void msgList() {
        mModel.msgList(PreferenceUUID.getInstence().getUserId())
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
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

    public void getChatJobinfo(String id) {
        mModel.getChatJobinfo(id)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ChatJobInfoEntity>() {
                    @Override
                    public void onNext(ChatJobInfoEntity chatJobInfoEntity) {
                        if (TextUtils.equals(chatJobInfoEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetChatJobinfo(chatJobInfoEntity);
                            }
                        }
                    }

                }));
    }

    public void viewedJob() {
        mModel.viewedJob(PreferenceUUID.getInstence().getUserId())
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
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

    public void joincopyContact(String jobId, String sortId, String contact, int type) {
        String userId = PreferenceUUID.getInstence().getUserId();
        if (TextUtils.isEmpty(userId)) {
            if (isAttach()) {
                weakReferenceView.get().reStartLogin();
                return;
            }
        }
        mModel.joincopyContact(Constants.APPID, userId, jobId, sortId, contact, type)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<AddFavouriteResponseEntity>>() {
                    @Override
                    public void onNext(ResponseData<AddFavouriteResponseEntity> stringResponseData) {
                        if (stringResponseData.getCode().equals(HttpAPI.REQUEST_SUCCESS)) {
//                            weakReferenceView.get().joinSuccess();
                        } else {
                        }
                    }
                }));
    }
}
