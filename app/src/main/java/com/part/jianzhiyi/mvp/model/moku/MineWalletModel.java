package com.part.jianzhiyi.mvp.model.moku;

import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.moku.MokuBillListEntity;
import com.part.jianzhiyi.model.entity.moku.TxLogEntity;
import com.part.jianzhiyi.model.entity.moku.WalletEntity;
import com.part.jianzhiyi.mvp.contract.moku.MineWalletContract;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;

import io.reactivex.Observable;

/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
public class MineWalletModel implements MineWalletContract.IMineWalletModel {

    @Override
    public Observable<MokuBillListEntity> getLiushuiList(String status) {
        return HttpAPI.getInstence().getServiceApi().getLiushuiList(PreferenceUUID.getInstence().getUserId(), status);
    }

    @Override
    public Observable<WalletEntity> getMyMoney() {
        return HttpAPI.getInstence().getServiceApi().getMyMoney(PreferenceUUID.getInstence().getUserId());
    }

    @Override
    public Observable<TxLogEntity> getTxLog(int page) {
        return HttpAPI.getInstence().getServiceApi().getTxLog(PreferenceUUID.getInstence().getUserId(), page);
    }

    @Override
    public Observable<ResponseData> getaddMd(String type) {
        return HttpAPI.getInstence().getServiceApi().getaddMd(type, Constants.OS);
    }
}
