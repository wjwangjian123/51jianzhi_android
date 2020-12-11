package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.mvp.contract.CityContract;

import io.reactivex.Observable;


public class CityModel implements CityContract.ICityModel {
    @Override
    public Observable<String> getCity() {
        return HttpAPI.getInstence(false).getScaleServiceApi().getCity();
    }

    @Override
    public Observable<ResponseData> getaddMd(String type) {
        return HttpAPI.getInstence().getServiceApi().getaddMd(type, Constants.OS);
    }
}
