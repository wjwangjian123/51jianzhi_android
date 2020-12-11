package com.part.jianzhiyi.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;

import io.reactivex.Observable;

public interface CityContract {
    interface ICityModel extends IModel {
        Observable<String> getCity();

        Observable<ResponseData> getaddMd(String type);
    }

    interface ICityView extends IView {
        void updateCity(String text);

        void updategetaddMd(ResponseData responseData);
    }
}
