package com.part.jianzhiyi.modulemerchants.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;

import io.reactivex.Observable;

public interface MCheckContract {

    interface IMCheckModel extends IModel {
        Observable<MCheckVersionEntity> getCheck();
    }

    interface IMCheckView extends IView {
        void updategetCheck(MCheckVersionEntity mCheckVersionEntity);
    }
}
