package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.mvp.contract.CityContract;

import io.reactivex.Observable;


public class CityModel implements CityContract.ICityModel {
    @Override
    public Observable<String> getCity() {
        return HttpAPI.getInstence(false).getScaleServiceApi().getCity();
    }
}
