package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.ChoiceEntity;
import com.part.jianzhiyi.mvp.contract.ChoiceContract2;
import com.part.jianzhiyi.mvp.model.ChoiceModel;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class ChoicePresenter extends BasePresenter<ChoiceContract2.IChoiceModel, ChoiceContract2.IChoiceView> {
    public ChoicePresenter(ChoiceContract2.IChoiceView mView) {
        super(mView, new ChoiceModel());
    }


    public void getChoice() {
        mModel.getChoice()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<ChoiceEntity>>() {
                    @Override
                    public void onNext(ResponseData<ChoiceEntity> responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                ChoiceEntity choiceModel = responseData.getData();
                                weakReferenceView.get().updateChoiceList(choiceModel.getChoice());
                                weakReferenceView.get().updateAdvertising(choiceModel.getAdvertising());
                                weakReferenceView.get().updateRankList(choiceModel.getWeekly());
                                weakReferenceView.get().updateRecommendList(choiceModel.getXiaobian());
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
