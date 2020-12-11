package com.part.jianzhiyi.mvp.model.integral;

import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.integral.ExcitationInfoEntity;
import com.part.jianzhiyi.model.entity.integral.MyIntegralEntity;
import com.part.jianzhiyi.mvp.contract.integral.IntegralContract;

import io.reactivex.Observable;


public class IntegralModel implements IntegralContract.IntegralModel {

    @Override
    public Observable<MyIntegralEntity> getMyIntegInfo(String user_id, int page) {
        return HttpAPI.getInstence().getServiceApi().getMyIntegInfo(user_id, page);
    }

    @Override
    public Observable<ExcitationInfoEntity> getExcitationInfo(String user_id) {
        return HttpAPI.getInstence().getServiceApi().getExcitationInfo(user_id);
    }

    @Override
    public Observable<ResponseData> getaddMd(String type) {
        return HttpAPI.getInstence().getServiceApi().getaddMd(type, Constants.OS);
    }
}
