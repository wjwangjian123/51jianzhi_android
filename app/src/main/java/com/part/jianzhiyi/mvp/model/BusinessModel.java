package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.mvp.contract.BusinessContract;

import io.reactivex.Observable;


public class BusinessModel implements BusinessContract.IBusinessModel {
    @Override
    public Observable<ResponseData<AddFavouriteResponseEntity>> getTitle(String appId) {
        return HttpAPI.getInstence().getServiceApi().getTitle(appId, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData> getaddMd(String type) {
        return HttpAPI.getInstence().getServiceApi().getaddMd(type, Constants.OS);
    }

}
