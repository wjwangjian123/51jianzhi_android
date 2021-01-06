package com.part.jianzhiyi.modulemerchants.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MCompanyInfoEntity;

import io.reactivex.Observable;

public interface MCompanyContract {

    interface IMCompanyModel extends IModel {
        Observable<MCompanyInfoEntity> getCompanyInfo(String uid, String job_id);

        Observable<ResponseData> getIntroduced(String company_desc);
    }

    interface IMCompanyView extends IView {
        void updategetCompanyInfo(MCompanyInfoEntity mCompanyInfoEntity);

        void updategetIntroduced(ResponseData responseData);
    }
}
