package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.ChoiceEntity;
import com.part.jianzhiyi.mvp.contract.ChoiceContract2;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @Date :2020-03-27
 **/
public class ChoiceModel implements ChoiceContract2.IChoiceModel {
    @Override
    public Observable<ResponseData<ChoiceEntity>> getChoice() {
        return HttpAPI.getInstence().getServiceApi().getChoice(Constants.POSITION_CHOICE,Constants.APPID,"1", HttpAPI.ip,"1",Constants.OS);
    }
}
