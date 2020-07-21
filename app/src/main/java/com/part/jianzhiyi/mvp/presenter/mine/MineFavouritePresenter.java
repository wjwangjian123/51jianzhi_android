package com.part.jianzhiyi.mvp.presenter.mine;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.mvp.contract.mine.MineFavouriteContract;
import com.part.jianzhiyi.mvp.model.mine.MineFavouriteModel;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;


/**
 * @author:
 * @content：我的信息
 * @vision:V1.0.1
 **/
public class MineFavouritePresenter extends BasePresenter<MineFavouriteContract.IMineFavouriteModel, MineFavouriteContract.IMineFavouriteView> {
    public MineFavouritePresenter(MineFavouriteContract.IMineFavouriteView mView) {
        super(mView, new MineFavouriteModel());
    }


    public void favourite() {
        String userId = PreferenceUUID.getInstence().getUserId();
        if (TextUtils.isEmpty(userId)) {
            if (isAttach()) {
                weakReferenceView.get().reStartLogin();
                return;
            }
        }
        mModel.favourite(userId)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<List<JobListResponseEntity>>>() {
                    @Override
                    public void onNext(ResponseData<List<JobListResponseEntity>> stringResponseData) {
                        if (stringResponseData.getCode().equals(HttpAPI.REQUEST_SUCCESS)) {
                            weakReferenceView.get().updateList(stringResponseData.getData());
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().showToast(stringResponseData.getMsg());
                            }
                        }
                    }
                }));
    }

    public void cancelFavourite(String jobId,String sortId) {
        String userId = PreferenceUUID.getInstence().getUserId();
        if (TextUtils.isEmpty(userId)) {
            if (isAttach()) {
                weakReferenceView.get().reStartLogin();
                return;
            }
        }
        mModel.cancelFavourite(userId, jobId, sortId)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<AddFavouriteResponseEntity>>() {
                    @Override
                    public void onNext(ResponseData<AddFavouriteResponseEntity> stringResponseData) {
                        if (stringResponseData.getCode().equals(HttpAPI.REQUEST_SUCCESS)) {
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
