package com.part.jianzhiyi.modulemerchants.mvp.presenter;

import android.text.TextUtils;

import com.part.jianzhiyi.modulemerchants.base.BasePresenter;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.http.ResultObserver;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MMinMoneyEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MMyWalletEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MZfbPayEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MerWalletContract;
import com.part.jianzhiyi.modulemerchants.mvp.model.MerWalletModel;

import okhttp3.RequestBody;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class MerWalletPresenter extends BasePresenter<MerWalletContract.IMerWalletModel, MerWalletContract.IMerWalletView> {
    public MerWalletPresenter(MerWalletContract.IMerWalletView mView) {
        super(mView, new MerWalletModel());
    }

    public void getMinMoney() {
        mModel.getMinMoney()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MMinMoneyEntity>() {
                    @Override
                    public void onNext(MMinMoneyEntity mMinMoneyEntity) {
                        if (TextUtils.equals(mMinMoneyEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetMinMoney(mMinMoneyEntity);
                            }
                        }else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetMinMoney(mMinMoneyEntity);
                            }
                        }
                    }
                }));
    }

    public void getMyMoney(String start_time, String end_time, String type, int page) {
        mModel.getMyMoney(start_time, end_time, type, page)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MMyWalletEntity>() {
                    @Override
                    public void onNext(MMyWalletEntity mMyWalletEntity) {
                        if (TextUtils.equals(mMyWalletEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetMyMoney(mMyWalletEntity);
                            }
                        }
                    }
                }));
    }

    public void getOrder(String money) {
        mModel.getOrder(money)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<MZfbPayEntity>() {
                    @Override
                    public void onNext(MZfbPayEntity mZfbPayEntity) {
                        if (TextUtils.equals(mZfbPayEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetOrder(mZfbPayEntity);
                            }
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetOrder(mZfbPayEntity);
                            }
                        }
                    }
                }));
    }

    public void getmdAdd(String type) {
        mModel.getmdAdd(type)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetmdAdd(responseData);
                            }
                        }
                    }
                }));
    }
}
