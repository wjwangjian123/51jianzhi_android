package com.part.jianzhiyi.modulemerchants.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;

import io.reactivex.Observable;

public interface MSetContract {

    interface ISetModel extends IModel {
        Observable<ResponseData> getPassword(String type, String pass, String new_pass, String old_pass, String code, String username);

        Observable<ResponseData> getCode();
    }

    interface ISetView extends IView {
        void updategetPassword(ResponseData responseData);

        void updategetCode(ResponseData responseData);
    }
}
