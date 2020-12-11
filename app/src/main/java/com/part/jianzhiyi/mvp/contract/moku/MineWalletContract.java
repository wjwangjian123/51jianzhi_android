package com.part.jianzhiyi.mvp.contract.moku;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.moku.MokuBillListEntity;
import com.part.jianzhiyi.model.entity.moku.TxLogEntity;
import com.part.jianzhiyi.model.entity.moku.WalletEntity;

import io.reactivex.Observable;

/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
public interface MineWalletContract {
    interface IMineWalletModel extends IModel {
        Observable<MokuBillListEntity> getLiushuiList(String status);

        Observable<WalletEntity> getMyMoney();

        Observable<TxLogEntity> getTxLog(int page);

        Observable<ResponseData> getaddMd(String type);
    }

    interface IMineWalletView extends IView {
        void updategetLiushuiList(MokuBillListEntity responseData);

        void updategetMyMoney(WalletEntity responseData);

        void updategetTxLog(TxLogEntity txLogEntity);

        void updategetaddMd(ResponseData responseData);
    }
}
