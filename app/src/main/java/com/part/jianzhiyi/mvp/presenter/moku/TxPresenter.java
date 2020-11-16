package com.part.jianzhiyi.mvp.presenter.moku;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.entity.moku.KuaibaoEntity;
import com.part.jianzhiyi.model.entity.moku.TxBindingEntity;
import com.part.jianzhiyi.model.entity.moku.TxInfoEntity;
import com.part.jianzhiyi.model.entity.moku.TxTypeEntity;
import com.part.jianzhiyi.model.entity.pay.AuthInfoEntity;
import com.part.jianzhiyi.mvp.contract.moku.TxContract;
import com.part.jianzhiyi.mvp.model.moku.TxModel;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class TxPresenter extends BasePresenter<TxContract.ITxModel, TxContract.ITxView> {

    public TxPresenter(TxContract.ITxView mView) {
        super(mView, new TxModel());
    }

    public void getTxtype() {
        mModel.getTxtype()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<TxTypeEntity>() {
                    @Override
                    public void onNext(TxTypeEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetTxtype(responseData);
                            }
                        }
                    }
                }));
    }

    public void getTxbinding(String code) {
        mModel.getTxbinding(code)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<TxBindingEntity>() {
                    @Override
                    public void onNext(TxBindingEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetTxbinding(responseData);
                            }
                        } else {
                            weakReferenceView.get().updategetTxbinding(responseData);
                        }
                    }
                }));
    }

    public void getTxInfo() {
        mModel.getTxInfo()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<TxInfoEntity>() {
                    @Override
                    public void onNext(TxInfoEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetTxInfo(responseData);
                            }
                        }
                    }
                }));
    }

    public void getTxcode(String user_id, String times, String token) {
        mModel.getTxcode(user_id, times, token)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<TxBindingEntity>() {
                    @Override
                    public void onNext(TxBindingEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetTxcode(responseData);
                            }
                        } else {
                            weakReferenceView.get().updategetTxcode(responseData);
                        }
                    }
                }));
    }

    public void getTxapp(String code, String money) {
        mModel.getTxapp(code, money)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<TxBindingEntity>() {
                    @Override
                    public void onNext(TxBindingEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetTxapp(responseData);
                            }
                        } else {
                            weakReferenceView.get().updategetTxapp(responseData);
                        }
                    }
                }));
    }

    public void getAuthInfo() {
        mModel.getAuthInfo()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<AuthInfoEntity>() {
                    @Override
                    public void onNext(AuthInfoEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetAuthInfo(responseData);
                            }
                        }
                    }
                }));
    }

    public void getTxkb() {
        mModel.getTxkb()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<KuaibaoEntity>() {
                    @Override
                    public void onNext(KuaibaoEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetTxkb(responseData);
                            }
                        }
                    }
                }));
    }

}
