package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.mvp.contract.AboutContract;

import io.reactivex.Observable;


public class AboutModel implements AboutContract.IAboutModel {
    @Override
    public Observable<ConfigEntity> getConfig() {
        return HttpAPI.getInstence().getServiceApi().getConfig(Constants.APPID, "2");
    }
}
