package com.part.jianzhiyi.modulemerchants.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MMinMoneyEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MMyWalletEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MZfbPayEntity;

import io.reactivex.Observable;

public interface MerWalletContract {

    interface IMerWalletModel extends IModel {
        Observable<MMinMoneyEntity> getMinMoney();

        Observable<MMyWalletEntity> getMyMoney(String start_time, String end_time, String type, int page);

        Observable<MZfbPayEntity> getOrder(String money);

        Observable<ResponseData> getmdAdd(String type);
    }

    interface IMerWalletView extends IView {
        void updategetMinMoney(MMinMoneyEntity mMinMoneyEntity);

        void updategetMyMoney(MMyWalletEntity mMyWalletEntity);

        void updategetOrder(MZfbPayEntity mZfbPayEntity);

        void updategetmdAdd(ResponseData responseData);

    }
}
