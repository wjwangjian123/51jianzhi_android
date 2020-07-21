package com.part.jianzhiyi.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;

import io.reactivex.Observable;

public interface BusinessContract {
    interface IBusinessModel extends IModel {
        Observable<ResponseData<AddFavouriteResponseEntity>> getTitle(String appId);
    }

    interface IBusinessView extends IView {
        void updateContract(String text);
    }
}
