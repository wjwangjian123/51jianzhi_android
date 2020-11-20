package com.part.jianzhiyi.mvp.presenter.integral;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.integral.ExcitationInfoEntity;
import com.part.jianzhiyi.model.entity.integral.MyIntegralEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.mvp.contract.integral.IntegralContract;
import com.part.jianzhiyi.mvp.model.integral.IntegralModel;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class IntegralPresenter extends BasePresenter<IntegralContract.IntegralModel, IntegralContract.IntegralView> {

    public IntegralPresenter(IntegralContract.IntegralView mView) {
        super(mView, new IntegralModel());
    }

    public void getMyIntegInfo(String user_id, int page) {
        mModel.getMyIntegInfo(user_id, page)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MyIntegralEntity>() {
                    @Override
                    public void onNext(MyIntegralEntity entity) {
                        if (TextUtils.equals(entity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetMyIntegInfo(entity);
                            }
                        }
                    }
                }));
    }

    public void getExcitationInfo(String user_id) {
        mModel.getExcitationInfo(user_id)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ExcitationInfoEntity>() {
                    @Override
                    public void onNext(ExcitationInfoEntity entity) {
                        if (TextUtils.equals(entity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetExcitationInfo(entity);
                            }
                        }
                    }
                }));
    }
}
