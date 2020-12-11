package com.part.jianzhiyi.modulemerchants.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MSwitchMerchantsEntity;

import io.reactivex.Observable;

public interface ChooseContract {

    interface IChooseModel extends IModel {
        Observable<MSwitchMerchantsEntity> getUserChabge(String phone);

        Observable<ResponseData> getmdAdd(String type);
    }

    interface IChooseView extends IView {
        void updategetUserChabge(MSwitchMerchantsEntity mSwitchMerchantsEntity);

        void updategetmdAdd(ResponseData responseData);
    }
}
