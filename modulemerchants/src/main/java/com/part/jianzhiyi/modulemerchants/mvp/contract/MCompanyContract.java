package com.part.jianzhiyi.modulemerchants.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.modulemerchants.model.entity.MCompanyInfoEntity;

import io.reactivex.Observable;

public interface MCompanyContract {

    interface IMCompanyModel extends IModel {
        Observable<MCompanyInfoEntity> getCompanyInfo(String uid, String job_id);
    }

    interface IMCompanyView extends IView {
        void updategetCompanyInfo(MCompanyInfoEntity mCompanyInfoEntity);
    }
}
