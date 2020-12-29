package com.part.jianzhiyi.modulemerchants.mvp.model;

import com.part.jianzhiyi.modulemerchants.constants.Constants;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MCheckContract;

import io.reactivex.Observable;


public class MCheckModel implements MCheckContract.IMCheckModel {

    @Override
    public Observable<MCheckVersionEntity> getCheck() {
        return HttpAPI.getInstence().getServiceApi().getCheck(Constants.OS, Constants.APPID);
    }
}
