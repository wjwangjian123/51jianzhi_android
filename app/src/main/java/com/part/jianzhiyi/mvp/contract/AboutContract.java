package com.part.jianzhiyi.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.entity.ConfigEntity;

import io.reactivex.Observable;

public interface AboutContract {
    interface IAboutModel extends IModel {
        Observable<ConfigEntity> getConfig();
    }

    interface IAboutView extends IView {
        void updategetConfig(ConfigEntity configEntity);
    }
}
