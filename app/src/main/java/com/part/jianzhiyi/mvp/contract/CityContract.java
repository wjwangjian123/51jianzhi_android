package com.part.jianzhiyi.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;

import io.reactivex.Observable;

public interface CityContract {
    interface ICityModel extends IModel {
        Observable<String> getCity();
    }

    interface ICityView extends IView {
        void updateCity(String text);
    }
}
