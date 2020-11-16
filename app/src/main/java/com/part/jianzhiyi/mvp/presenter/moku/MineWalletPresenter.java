package com.part.jianzhiyi.mvp.presenter.moku;

import android.text.TextUtils;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.entity.moku.MokuBillListEntity;
import com.part.jianzhiyi.model.entity.moku.TxLogEntity;
import com.part.jianzhiyi.model.entity.moku.WalletEntity;
import com.part.jianzhiyi.mvp.contract.moku.MineWalletContract;
import com.part.jianzhiyi.mvp.model.moku.MineWalletModel;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class MineWalletPresenter extends BasePresenter<MineWalletContract.IMineWalletModel, MineWalletContract.IMineWalletView> {
    public MineWalletPresenter(MineWalletContract.IMineWalletView mView) {
        super(mView, new MineWalletModel());
    }

    public void getLiushuiList(String status) {
        mModel.getLiushuiList(status)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MokuBillListEntity>() {
                    @Override
                    public void onNext(MokuBillListEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetLiushuiList(responseData);
                            }
                        }
                    }
                }));
    }

    public void getMyMoney() {
        mModel.getMyMoney()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<WalletEntity>() {
                    @Override
                    public void onNext(WalletEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetMyMoney(responseData);
                            }
                        }
                    }
                }));
    }

    public void getTxLog(int page) {
        mModel.getTxLog(page)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<TxLogEntity>() {
                    @Override
                    public void onNext(TxLogEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetTxLog(responseData);
                            }
                        }
                    }
                }));
    }
}
